<script>
  import { Navbar, NavBrand, NavLi, NavUl, NavHamburger, Button, Input } from 'flowbite-svelte';
  import { Icon } from 'flowbite-svelte-icons';
</script>

<Navbar let:hidden let:toggle class="h-16 border-b-2">
	<NavBrand href="/dashboard" class="items-start">
		<img src="/images/flowbite-svelte-icon-logo.svg" class="mr-3 h-6 sm:h-9" alt="Clinica Logo"/>
	</NavBrand>
	<div class="items-center">
		<div class="relative">
			<div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
				<Icon name="search-outline" class="w-4 h-4"/>
			</div>
			<Input id="search-navbar" class="pl-10 mr-60" placeholder="Pesquisar por Nome de cliente, Nif ou Telemóvel"/>
		</div>
		<Button color="none" data-collapse-toggle="mobile-menu-3" aria-controls="mobile-menu-3" aria-expanded="false"
		        class="md:hidden text-gray-500 dark:text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-700 focus:outline-none focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 rounded-lg text-sm p-2.5 ml-1">
			<Icon name="search-outline" class="w-5 h-5"/>
		</Button>
	</div>
	<div class="items-end">tsrat</div>
</Navbar>