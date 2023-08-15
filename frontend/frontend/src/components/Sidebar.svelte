<script>
  import {
    Drawer,
    Button,
    Sidebar,
    SidebarGroup,
    SidebarItem,
    SidebarWrapper
  } from 'flowbite-svelte';
  import { Icon } from 'flowbite-svelte-icons';

  export let sidebarClosed;
  let spanClass = 'flex-1 ml-3 whitespace-nowrap';
  let transitionParams = {
    x: -280,
    duration: 150
  };
</script>
<div class="">
	<div class="relative left-2 top-2">
		<Button on:click={() => (sidebarClosed = false)} color='light' class="h-10 w-10 rounded-full">
			<Icon name="angle-right-solid" size="md" class="outline-0 pl-1"/>
		</Button>
	</div>
	<Drawer transitionType="fly" {transitionParams} bind:hidden={sidebarClosed} id="sidebar2" backdrop={false}
	        class="border-2 border-r-dark-900" leftOffset="top-16 left-0 h-screen">
		<div class="flex items-center">
			<h5 id="drawer-navigation-label-3" class="text-base font-semibold text-gray-500 uppercase dark:text-gray-400">
				Menu</h5>
			<div class="absolute right-0">
				<Button on:click={() => (sidebarClosed = true)} color="light" class="h-10 w-10 rounded-full mr-3">
					<Icon name="angle-left-solid" size="md" class="outline-0"/>
				</Button>
			</div>

		</div>
		<Sidebar>
			<SidebarWrapper divClass="overflow-y-auto py-4 px-3 rounded dark:bg-gray-800">
				<SidebarGroup>
					<SidebarItem label="Dashboard" href="/dashboard">>
						<svelte:fragment slot="icon">
							<Icon name="chart-pie-solid"
							      class="w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"/>
						</svelte:fragment>
					</SidebarItem>
					<SidebarItem label="Clientes">
						<svelte:fragment slot="icon">
							<Icon name="users-solid"
							      class="w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"/>
						</svelte:fragment>
					</SidebarItem>
					<SidebarItem label="Inbox" {spanClass}>
						<svelte:fragment slot="icon">
							<Icon name="mail-box-solid"
							      class="w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"/>
						</svelte:fragment>
						<svelte:fragment slot="subtext">
						<span
							class="inline-flex justify-center items-center p-3 ml-3 w-3 h-3 text-sm font-medium text-primary-600 bg-primary-200 rounded-full dark:bg-primary-900 dark:text-primary-200"> 3 </span>
						</svelte:fragment>
					</SidebarItem>

					<SidebarItem label="Products">
						<svelte:fragment slot="icon">
							<Icon name="bag-solid"
							      class="w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"/>
						</svelte:fragment>
					</SidebarItem>
					<SidebarItem label="Sign In">
						<svelte:fragment slot="icon">
							<Icon name="arrow-right-to-bracket-solid"
							      class="w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"/>
						</svelte:fragment>
					</SidebarItem>
					<SidebarItem label="Sign Up">
						<svelte:fragment slot="icon">
							<Icon name="file-edit-solid"
							      class="w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"/>
						</svelte:fragment>
					</SidebarItem>
				</SidebarGroup>
			</SidebarWrapper>
		</Sidebar>
	</Drawer>
</div>
