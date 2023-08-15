<script>
  import { Button, ButtonGroup, Input, Select } from 'flowbite-svelte';
  import { formatDateToDDMMYYYY, calculateAge, parseDDMMYYYYToDate } from '@/utils/utils.ts';

  export let data;
  let isEditing = false;
  let dadosClient = true;
  let editedData = { ...data.props.data };
  let selectedDistrictId = editedData.client.address.districtId;
  let selectedConcelhoId = editedData.client.address.concelhoId;
  let dateInputValue = editedData.client.birthDate;
  let filteredConcelhos = [];

  $: if (!isEditing || editedData.client.address.districtId !== selectedDistrictId) {
    editedData = { ...data.props.data };
    selectedDistrictId = editedData.client.address.districtId;
    selectedConcelhoId = editedData.client.address.concelhoId;
    filteredConcelhos = Object.values(data.props.data.concelho)
      .filter(concelhoItem => concelhoItem.districtId === selectedDistrictId);
  }

  const genderOptions = ['Masculino', 'Feminino'];
  const genderMapping = {
    'MALE': 'Masculino',
    'FEMALE': 'Feminino'
  };

  editedData.client.gender = genderMapping[editedData.client.gender] || editedData.client.gender;

  function toggleEditMode () {
    isEditing = !isEditing;
    if (isEditing) {
      dateInputValue = editedData.client.birthDate; // Set the date input value when entering edit mode
    }
  }

  function handleDistrictChange (event) {
    const selectedDistrictName = event.target.value;
    const selectedDistrict = data.props.data.district.find(district => district.districtName === selectedDistrictName);
    selectedDistrictId = selectedDistrict.districtId;

    // Filter concelhos based on the selected district
    editedData.client.address.districtId = selectedDistrictId;
    editedData.client.address.districtName = selectedDistrictName;
    selectedConcelhoId = ''; // Reset selected concelho when district changes

    // Find the corresponding concelho for the selected district and update editedData
    const correspondingConcelho = data.props.data.concelho.find(concelhoItem => concelhoItem.districtId === selectedDistrictId);
    if (correspondingConcelho) {
      editedData.client.address.concelhoId = correspondingConcelho.concelhoId;
      editedData.client.address.concelhoName = correspondingConcelho.concelhoName;
      editedData.client.address.districtId = correspondingConcelho.districtId;
    }

    filteredConcelhos = data.props.data.concelho.filter(concelhoItem => concelhoItem.districtId === selectedDistrictId);
  }

  function handleConcelhoChange (event) {
    const selectedConcelhoName = event.target.value;
    const selectedConcelho = data.props.data.concelho.find(concelho => concelho.concelhoName === selectedConcelhoName);
    selectedConcelhoId = selectedConcelho.concelhoId;
    editedData.client.address.concelhoId = selectedConcelhoId; // Update the concelhoId in editedData
  }

  function saveChanges () {
    editedData.client.gender = genderMapping[editedData.client.gender] || editedData.client.gender;
    editedData.client.birthDate = dateInputValue;
    data.props.data.client = Array.isArray(data.props.data.client)
      ? [...editedData.client]
      : { ...editedData.client };

    // Copy the district and concelho data as arrays if they were originally arrays
    data.props.data.district = Array.isArray(data.props.data.district)
      ? [...editedData.district]
      : { ...editedData.district };
    data.props.data.concelho = Array.isArray(data.props.data.concelho)
      ? [...editedData.concelho]
      : { ...editedData.concelho };
    toggleEditMode();
  }
</script>

<div class="container grid grid-cols-2">
	<div class="text-center max-w-xs border-solid border-[1px] border-gray-300 p-2 rounded-xl">
		{#if isEditing}
			<Input class="text-xl" bind:value={editedData.client.fullName}/>
		{:else}
			<p class="font-bold text-xl text-gray-600">{data.props.data.client.fullName}</p>
		{/if}
		{#if isEditing}
			<input
				class="mt-2 rounded-md"
				type="date"
				value={dateInputValue}
				max="<?=date('Y-m-d',strtotime('now +1 week'));?>"
				on:input={event => {
            dateInputValue = event.target.value;
          }}
			/>
		{:else}
			<p class="pt-2">{formatDateToDDMMYYYY(data.props.data.client.birthDate)} -
				({calculateAge(data.props.data.client.birthDate)}
				anos)</p>
		{/if}
		<div class="mt-2 text-gray-500"/>
		<ButtonGroup class="p-2 flex gap-1">
			<Button class="flex-1" color={dadosClient ? 'dark' : 'light'}
			        on:click={() => dadosClient = true}>Dados Cliente
			</Button>
			<Button class="flex-1" color={dadosClient ? 'light' : 'dark'}
			        on:click={() => dadosClient = false}>Morada
			</Button>
		</ButtonGroup>
		{#if dadosClient === true}
			<div class="mt-3 text-left items-stretch">
				<p class="font-light">Telemóvel</p>
				{#if isEditing}
					<Input bind:value={editedData.client.mobile}/>
				{:else}
					<p
						class="pt-1 font-medium text-gray-600">{data.props.data.client.mobile ? data.props.data.client.mobile : '-'}</p>
				{/if}
			</div>
			<div class="mt-5 text-left items-stretch">
				<p class="font-light">Telefone</p>
				{#if isEditing}
					<Input bind:value={editedData.client.lanline}/>
				{:else}
					<p
						class="pt-1 font-medium text-gray-600">{data.props.data.client.lanline ? data.props.data.client.lanline : '-'}</p>
				{/if}
			</div>
			<div class="mt-5 text-left items-stretch">
				<p class="font-light">Email</p>
				{#if isEditing}
					<Input bind:value={editedData.client.email}/>
				{:else}
					<p
						class="pt-1 font-medium text-gray-600">{data.props.data.client.email ? data.props.data.client.email : '-'}</p>
				{/if}
			</div>
			<div class="mt-5 text-left items-stretch">
				<p class="font-light">Número de Identificação Fiscal</p>
				{#if isEditing}
					<Input bind:value={editedData.client.ssn}/>
				{:else}
					<p class="pt-1 font-medium text-gray-600">{data.props.data.client.ssn ? data.props.data.client.ssn : '-'}</p>
				{/if}
			</div>
			<div class="mt-5 text-left items-stretch">
				<p class="font-light">Sexo</p>
				{#if isEditing}
					<Select bind:value={editedData.client.gender}>
						{#each genderOptions as option}
							<option value={option}>{option}</option>
						{/each}
					</Select>
				{:else}
					<p
						class="pt-1 font-medium text-gray-600">{data.props.data.client.gender === 'FEMALE' ? 'Feminino' : 'Masculino'}</p>
				{/if}
			</div>
		{:else}
			<div class="mt-3 text-left items-stretch">
				<p class="font-light">Rua</p>
				{#if isEditing}
					<Input bind:value={editedData.client.address.streetName}/>
				{:else}
					<p class="pt-1 font-medium text-gray-600">
						{data.props.data.client.address.streetName ? data.props.data.client.address.streetName : '-'}
						{data.props.data.client.address.locality ? ', ' + data.props.data.client.address.locality : ''}
					</p>
				{/if}
			</div>
			<div class="mt-5 text-left items-stretch">
				<p class="font-light">Código Postal</p>
				{#if isEditing}
					<Input bind:value={editedData.client.address.zipCode}/>
				{:else}
					<p
						class="pt-1 font-medium text-gray-600">{data.props.data.client.address.zipCode ? data.props.data.client.address.zipCode : '-'}</p>
				{/if}
			</div>
			<div class="mt-5 text-left items-stretch">
				<p class="font-light">Distrito</p>
				{#if isEditing}
					<Select bind:value={editedData.client.address.districtName} on:change={handleDistrictChange}>
						{#each data.props.data.district as districtItem}
							<option value={districtItem.districtName} data-id={districtItem.districtId}>
								{districtItem.districtName}
							</option>
						{/each}
					</Select>
				{:else}
					<p
						class="pt-1 font-medium text-gray-600">{data.props.data.client.address.districtName ? data.props.data.client.address.districtName : '-'}</p>
				{/if}
			</div>
			<div class="mt-3 text-left items-stretch">
				<p class="font-light">Concelho</p>
				{#if isEditing}
					<Select bind:value={editedData.client.address.concelhoName} on:change={handleConcelhoChange}>
						{#each filteredConcelhos as concelhoItem}
							<option value={concelhoItem.concelhoName} data-id={concelhoItem.concelhoId}>
								{concelhoItem.concelhoName}
							</option>
						{/each}
					</Select>
				{:else}
					<p
						class="pt-1 font-medium text-gray-600">{data.props.data.client.address.concelhoName ? data.props.data.client.address.concelhoName : '-'}</p>
				{/if}
			</div>
			<div class="mt-5 text-left items-stretch">
				<p class="font-light">Pais</p>
				{#if isEditing}
					<Input bind:value={editedData.client.address.country}/>
				{:else}
					<p
						class="pt-1 font-medium text-gray-600">{data.props.data.client.address.country ? data.props.data.client.address.country : '-'}</p>
				{/if}
			</div>
		{/if}
		<div class="mt-5 text-left">
			{#if isEditing}
				<Button on:click={saveChanges} color="dark">Save</Button>
			{:else}
				<Button on:click={toggleEditMode} color="dark">Edit</Button>
			{/if}
		</div>
	</div>
</div>


