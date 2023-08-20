<script lang="ts">
  import ClientCard from '@/lib/components/client/ClientCard.svelte';
  import type {Client, Concelho, District} from '$lib/types';

  export let data: { client: Client, district: District, concelho: Concelho };
</script>

<ClientCard bind:data/>