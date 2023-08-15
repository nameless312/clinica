<script lang="ts">
  import Sidebar from '@/components/Sidebar.svelte';
  import Navbar from '@/components/Navbar.svelte';
  import {onMount} from 'svelte';

  let sidebarClosed;

  onMount(() => {
    sidebarClosed = true;
  });
</script>

<style>
    .main-layout {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
        overflow-x: hidden;
    }

    .main-content {
        flex: 1;
        padding: 20px;
        transition: margin-left 0.15s ease;
        margin-left: 0;
    }

    .main-content.open {
        margin-left: 280px;
        height: 100vh;
    }

    .navbar {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        background-color: #fff;
        padding: 10px 20px;
        box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
        z-index: 1000;
    }

    .content-layout {
        display: flex;
        position: relative; /* Add this line to create a stacking context */
    }

    .sidebar {
        position: fixed;
        top: 60px; /* Adjust this value to match the height of your navbar */
        z-index: 999;
    }
</style>

<div class="main-layout w-full">
	<Navbar class="navbar"/> <!-- Add the 'navbar' class -->
	<div class="content-layout">
		<Sidebar bind:sidebarClosed class="sidebar"/> <!-- Add the 'sidebar' class -->
		<div class="main-content {sidebarClosed ? '' : 'open'}">
			<slot/>
		</div>
	</div>
</div>
