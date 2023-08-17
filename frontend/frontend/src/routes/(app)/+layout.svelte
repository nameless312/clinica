<script lang="ts">
  import Sidebar from '@/components/Sidebar.svelte';
  import Navbar from '@/components/Navbar.svelte';
  import {AppShell, Drawer} from '@skeletonlabs/skeleton';
</script>

<Drawer>
	<Sidebar/>
</Drawer>

<AppShell slotSidebarLeft="bg-surface-100 w-0 md:w-52">
	<svelte:fragment slot="header">
		<Navbar/>
	</svelte:fragment>
	<svelte:fragment slot="sidebarLeft">
		<Sidebar/>
	</svelte:fragment>
	<div class="container p-10 mx-auto">
		<slot/>
	</div>

</AppShell>

<!--<div>-->
<!--	<Navbar/>-->
<!--	<div>-->
<!--		<Sidebar bind:sidebarClosed class="sidebar"/>-->
<!--		<div class="{sidebarClosed ? '' : 'open'}">-->
<!--			<slot/>-->
<!--		</div>-->
<!--	</div>-->
<!--</div>-->
