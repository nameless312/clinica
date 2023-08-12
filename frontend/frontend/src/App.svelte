<script lang="ts">
  import {Router, Route, navigate} from 'svelte-routing';
  import Login from './routes/Login.svelte';
  import Dashboard from './routes/Dashboard.svelte';
  import {beforeUpdate, onMount} from 'svelte';
  import {authStore} from './stores/authStore';
  import {fetchWithAuth} from './utils/utils';

  interface Payload {
    userId: string;
    firstName: string;
    lastName: string;
    email: string;
  }

  onMount(async () => {
    const authenticated: Payload | null = await getUserPayload(); // Extract user payload from token
    if (authenticated) {
      authStore.update(store => {
        store.isAuthenticated = true;
        store.user = authenticated;
        return store;
      });
    } else {
      navigate('/login'); // Redirect to the login page if not authenticated
    }
  });

  async function getUserPayload(): Promise<Payload | null> {
    try {
      const id = getUserIdFromToken();
      if (id) {
        const response = await fetchWithAuth(`${import.meta.env.VITE_BASE_API_URL}/user/${id}`, {
          method: 'GET',
        });
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

  function getUserIdFromToken() {
    const cookie = document.cookie;
    const match = /Authorization=([^;]+)/.exec(cookie);
    if (match) {
      const token = match[1];
      const tokenParts = token.split('.');
      if (tokenParts.length === 3) {
        const payloadBase64 = tokenParts[1];
        const payload = JSON.parse(atob(payloadBase64));
        if (isTokenExpired(payload.exp)) {
          return null; // Return null if token is expired
        }
        return payload.userid; // Assuming 'userid' is the claim name for user ID
      }
    }

    return null; // Return null if user ID couldn't be extracted
  }
</script>

<main>
	<Router>
		<!-- Public routes -->
		<Route path="/login" component={Login}/>

		<!-- Protected routes -->
		<Route path="/" component={Dashboard}/>
		<!-- Catch-all route for unknown routes -->
		<Route path="*" let:params><p>404 - Not Found</p></Route>
	</Router>
</main>