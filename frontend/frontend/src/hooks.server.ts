import {fetchWithAuthAndCookie} from './utils/utils';
import {authStore} from './stores/authStore';
import {redirect} from '@sveltejs/kit';

const unProtectedRoutes = ['/login'];

export const handle = async ({event, request, resolve}) => {
  const authorization = event.cookies.get('Authorization');
  if (!authorization && !unProtectedRoutes.includes(event.url.pathname)) {
    // User is not authenticated and trying to access a protected route
    throw redirect(302, '/login');
  }

  if (authorization && event.url.pathname === '/login') {
    // User is authenticated but trying to access the login page
    throw redirect(302, '/dashboard');
  }

  if (authorization) {
    const user: Payload | null = await getUserPayload(authorization);
    if (user) {
      authStore.update((store) => ({
        ...store,
        isAuthenticated: true,
        user: user,
      }));
    } else {
      if (!unProtectedRoutes.includes(event.url.pathname)) {
        throw redirect(302, '/login');
      }
    }
  }

  const query = event.url.searchParams.get('logout');
  if (Boolean(query) == true) {
    await event.cookies.delete('authorization');
    throw redirect(302, '/login');
  }
  return resolve(event);
};

interface Payload {
  userId: string;
  firstName: string;
  lastName: string;
  email: string;
}

async function getUserPayload(cookie: string): Promise<Payload | null> {
  try {
    const id = getUserIdFromToken(cookie);
    if (id) {
      const response = await fetchWithAuthAndCookie(`${import.meta.env.VITE_BASE_API_URL}/user/${id}`, {
        method: 'GET',
      }, cookie);
      if (response.ok) {
        // Extract JSON data from the response
        return await response.json();
      } else {
        return null; // Return null if response status is not OK
      }
    }

  } catch (error) {
    // Handle fetch error
    console.error(error);
  }
}

function isTokenExpired(exp: number): boolean {
  const currentTime = Math.floor(Date.now() / 1000);
  return exp < currentTime;
}

function getUserIdFromToken(cookie: string): string | null {
  const tokenParts = cookie.split('.');
  if (tokenParts.length === 3) {
    const payloadBase64 = tokenParts[1];
    const payload = JSON.parse(atob(payloadBase64));
    if (isTokenExpired(payload.exp)) {
      return null; // Return null if token is expired
    }
    return payload.userid; // Assuming 'userid' is the claim name for user ID
  }
}