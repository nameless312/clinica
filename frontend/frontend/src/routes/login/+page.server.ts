import type {Actions} from './$types';
import {redirect} from '@sveltejs/kit';

export const actions = {
  login: async ({cookies, request}) => {
    const data = await request.formData();
    const email = data.get('email');
    const password = data.get('password');

    const response = await fetch(`${import.meta.env.VITE_BASE_API_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({email, password}),
    });
    if (response.ok) {
      const cookie = response.headers.get('set-cookie').split(';');
      const cookieName = cookie[0].split('=')[0];
      const cookieValue = cookie[0].split('=')[1];
      const cookieMaxAge = +cookie[1].split('=')[1];
      const cookieExp = new Date(cookie[2].split('=')[1]);
      const cookieDomain = cookie[3].split('=')[1];
      const cookiePath = cookie[4].split('=')[1];
      const cookieSecure = response.headers.get('set-cookie').includes('Secure');

      cookies.set(cookieName, cookieValue,
        {
          secure: cookieSecure,
          maxAge: cookieMaxAge,
          expires: cookieExp,
          domain: cookieDomain,
          path: cookiePath,
          sameSite: 'none'
        });
      // console.log(response);
      throw redirect(303, '/dashboard');
    } else {
      // Handle login failure
      return {
        status: 401,
        body: {error: 'Invalid email or password'},
      };
    }
  },
} satisfies Actions;