// @ts-ignore
import {fetchWithAuth} from '@/utils/utils';
import type {Client, Concelho, District} from '$lib/types';

export async function load({params, cookies}) {
  const token = cookies.get('Authorization');

  const clientResponse = await fetchWithAuth(`${import.meta.env.VITE_BASE_API_URL}/client/${params.id}`, {
    method: 'GET'
  }, token);
  const client: Client = await clientResponse.json();

  const districtResponse = await fetchWithAuth(`${import.meta.env.VITE_BASE_API_URL}/district`, {
    method: 'GET'
  }, token);
  const district: District = await districtResponse.json();

  const concelhoResponse = await fetchWithAuth(`${import.meta.env.VITE_BASE_API_URL}/concelho`, {
    method: 'GET'
  }, token);
  const concelho: Concelho = await concelhoResponse.json();
  
  return {
    props: {
      data: {
        client,
        district,
        concelho
      }
    }
  };
}
