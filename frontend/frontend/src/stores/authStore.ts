import {writable} from 'svelte/store';

interface User {
  userId: string,
  firstName: string,
  lastName: string,
  email: string,
}

interface AuthState {
  isAuthenticated: boolean;
  user: User | null;
}

export const authStore = writable<AuthState>({
  isAuthenticated: false,
  user: null,
});



