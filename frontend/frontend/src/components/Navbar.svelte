<script lang="ts">
  import {AppBar, drawerStore} from '@skeletonlabs/skeleton';

  import {page} from '$app/stores';

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
	<div class="input-group md:grid-cols-[1rem_1fr] sm:grid-cols-[1rem_1fr]">
		<div class="input-group-shim">
			<i class="fa-solid fa-search"/>
		</div>
		<div class="p-0">
			<input type="search" class="input" placeholder="Pesquisar por Nome de Cliente, Nif ou Telemovel">
		</div>
	</div>
	<svelte:fragment slot="trail">
		<div class="invisible md:visible">
			{$page.data.user.firstName} {$page.data.user.lastName}
		</div>
	</svelte:fragment>
</AppBar>