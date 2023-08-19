import {fetchWithAuth} from '../../utils/utils';
import type {Client} from '$lib/types';

export const load = async ({locals, cookies}) => {
  // fill the clients store
  try {

    const cookie = cookies.get('Authorization');
    const response = await fetchWithAuth(`${import.meta.env.VITE_BASE_API_URL}/client`, {
      method: 'GET',
    }, cookie);

    if (response) {
      const clientsData: Client[] = await response.json();
      locals.clients = clientsData;

    } else {
      console.error(`API request failed with status: ${response.status}`);
    }
  } catch (error) {
    // Handle network or JSON parsing errors
    console.error('Error fetching or parsing client data:', error);
  }

  return {user: locals.user, clients: locals.clients};
};