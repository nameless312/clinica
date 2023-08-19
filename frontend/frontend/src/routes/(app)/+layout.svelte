<script lang="ts">
  import Sidebar from '@/lib/components/Sidebar.svelte';
  import Navbar from '@/lib/components/Navbar.svelte';
  import {AppShell, Drawer} from '@skeletonlabs/skeleton';

  import {storePopup} from '@skeletonlabs/skeleton';
  import {computePosition, autoUpdate, flip, shift, offset, arrow} from '@floating-ui/dom';

  storePopup.set({computePosition, flip, shift, offset, arrow, autoUpdate});
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
