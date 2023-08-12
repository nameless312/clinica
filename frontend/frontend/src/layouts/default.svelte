<!-- src/layouts/default.svelte -->
<script>
  export let segment;
</script>

<div>
	<header>
		<!-- Your header content -->
	</header>

	<main>
		<svelte:component this={segment.default}/>
	</main>

	<footer>
		<!-- Your footer content -->
	</footer>
</div>
