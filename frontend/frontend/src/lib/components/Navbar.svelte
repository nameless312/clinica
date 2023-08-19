<script lang="ts">
  import type {AutocompleteOption, PopupSettings} from '@skeletonlabs/skeleton';
  import {AppBar, Autocomplete, drawerStore, popup} from '@skeletonlabs/skeleton';

  import {page} from '$app/stores';
  import {goto} from '$app/navigation';
  import type {Client} from '$lib/types';

  let popupSettings: PopupSettings = {
    event: 'click',
    target: 'popupAutocomplete',
    placement: 'bottom-start'
  };
  let searchClientsOptions: AutocompleteOption[] = [];


  searchClientsOptions = $page.data.clients.map((client: Client) => ({
    label: client.fullName,
    value: client,
    keywords: `${client.fullName.toLowerCase()}, ${client.ssn.toLowerCase()}, ${client.mobile.toLowerCase()}, ${client.lanline.toLowerCase()}`
  }));

  let inputSearch = '';

  function onPopupInputSelect(event: any): void {
    goto(`/client/${event.detail.value.clientID}`);
  }

  function drawerOpen(): void {
    drawerStore.open({width: 'w-[240px]'});
  }
</script>

<AppBar gridColumns="grid-cols-3" padding="p-4" slotTrail="place-content-end"
        background="bg-primary-100">
	<svelte:fragment slot="lead">
		<div class="flex items-center">
			<button class="md:hidden btn btn-sm mr-4" on:click={drawerOpen}>
				<i class="fa-solid fa-bars"></i>
			</button>
			<a href="/dashboard">
				<strong class="text-xl invisible md:visible">Clinica Joana Andrade</strong>
			</a>
		</div>
	</svelte:fragment>
	<div class="relative">
		<div class="p-0">
			<input type="search"
			       class="input autocomplete pl-8"
			       name="autocomplete-search"
			       placeholder="Pesquisar por Nome de Cliente, Nif ou Telemovel"
			       bind:value={inputSearch}
			       use:popup={popupSettings}
			/>
			<i class="fa-solid fa-search absolute left-2 top-1/2 transform -translate-y-1/2 text-primary-300 pl-1 pt-1"/>
			<div data-popup="popupAutocomplete"
			     class="absolute mt-1 bg-white border border-gray-300 shadow-lg overflow-y-auto rounded-lg z-10"
			>
				<Autocomplete
					bind:input={inputSearch}
					options={searchClientsOptions}
					on:selection={onPopupInputSelect}
				/>
			</div>
		</div>
	</div>

	<svelte:fragment slot="trail">
		<div class="invisible md:visible">
			{$page.data.user.firstName} {$page.data.user.lastName}
		</div>
	</svelte:fragment>
</AppBar>