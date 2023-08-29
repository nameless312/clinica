// @ts-ignore
import {fetchWithAuth} from '@/utils/utils';
import type {Client, Concelho, District, Marketing, Partnership} from '$lib/types';

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

  const partnershipResponse = await fetchWithAuth(`${import.meta.env.VITE_BASE_API_URL}/partnership`, {
    method: 'GET'
  }, token);
  const partnership: Partnership = await partnershipResponse.json();

  const marketingResponse = await fetchWithAuth(`${import.meta.env.VITE_BASE_API_URL}/marketing`, {
    method: 'GET'
  }, token);
  const marketing: Marketing = await marketingResponse.json();

  return {
    props: {
      data: {
        client,
        district,
        concelho,
        partnership,
        marketing
      }
    }
  };
}
