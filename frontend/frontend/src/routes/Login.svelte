<script lang="ts">

  import {navigate} from 'svelte-routing';
  import {beforeUpdate} from 'svelte';
  import {authStore} from '../stores/authStore';

  let email = '';
  let password = '';

  beforeUpdate(() => {
    const isAuthenticated = $authStore.isAuthenticated;
    if (isAuthenticated) {
      navigate('/');
    }
  });
  const handleLogin = async () => {
    try {
      const response = await fetch(`${import.meta.env.VITE_BASE_API_URL}/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({email, password}),
      });

      if (response.ok) {
        navigate('/'); // Redirect to the dashboard upon successful login
      } else {
        // Handle login failure
      }
    } catch (error) {
      // Handle fetch error
    }
  };
</script>

<main>
	<h1>Login</h1>
	<form on:submit|preventDefault={handleLogin}>
		<label>
			Email:
			<input type="email" bind:value={email}/>
		</label>
		<label>
			Password:
			<input type="password" bind:value={password}/>
		</label>
		<button type="submit">Login</button>
	</form>
</main>
