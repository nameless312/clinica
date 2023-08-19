<script>

  // Your custom Skeleton theme:
  import '../theme.postcss';

  // This contains the bulk of Skeletons required styles:
  import '@skeletonlabs/skeleton/styles/skeleton.css';

  // Finally, your application's global stylesheet (sometimes labeled 'app.css')
  import '../app.postcss';

  import { storePopup } from '@skeletonlabs/skeleton';
  import { computePosition, autoUpdate, flip, shift, offset, arrow } from '@floating-ui/dom';

  storePopup.set({ computePosition, flip, shift, offset, arrow, autoUpdate });
</script>

<slot/>
