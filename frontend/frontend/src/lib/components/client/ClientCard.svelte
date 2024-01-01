<script lang="ts">
  import {
    parseYYYYMMDDToDate,
    formatDateToDDMMYYYY,
    calculateAge,
    translateGender,
    reverseTranslateGender
  } from '../../../utils/utils';
  import {Gender} from '$lib/types';

  export let data;
  let isClientData = true;
  let isEditing = true;

  // let birthDate = formatDateToYYYYMMDD(data.props.data.client.birthDate);
  let birthDate = '2023-08-30';
  const genders = [translateGender(Gender.FEMALE), translateGender(Gender.MALE), translateGender(null)];
  let selectedGender = translateGender(data.props.data.client.gender);

  const partnerships = [null, ...data.props.data.partnership];
  const marketingChannels = [null, ...data.props.data.marketing];
  let selectedPartnership = data.props.data.client.partnership;
  let selectedMarketingChannel = data.props.data.client.marketing;

  function updateCard() {
    isEditing = false;
    data.props.data.client.birthDate = parseYYYYMMDDToDate(birthDate);
    data.props.data.client.gender = reverseTranslateGender(selectedGender);
    data.props.data.client.partnership = selectedPartnership;
    data.props.data.client.marketing = selectedMarketingChannel;
  }
</script>

<div class="grid grid-cols-3 gap-3">
	<div class="card h-full bg-surface-300">
		{#if !isEditing}
			<div class="relative">
				<button type="button" class="btn bg-primary-200 rounded-full absolute right-3 top-3"
				        on:click={() => isEditing = true}>
					<i class="fa-regular fa-pen-to-square"></i>
				</button>
			</div>
		{/if}
		{#if isEditing}
			<div class="flex flex-col text-center">
				<div class="flex flex-col items-center">
					<input type="text" class="input mt-2 w-80"
					       bind:value="{data.props.data.client.fullName}"/>
					<input type="date" class="input mt-2 w-40" max={new Date().toISOString().split("T")[0]}
					       bind:value={birthDate}/>
				</div>
			</div>
			<hr class="my-4 mx-2 !border-primary-400">
			<div class="p-2">
				<div class="flex justify-evenly">
					<button type="button" class="btn bg-primary-200 rounded-full" on:click={() => isClientData = true}>Dados
						Cliente
					</button>
					<button type="button" class="btn bg-primary-200 rounded-full" on:click={() => isClientData = false}>Dados
						Morada
					</button>
				</div>
				{#if isClientData}
					<div class="pt-2 mx-4">
						<div class="opacity-50">Email</div>
						<input class="input font-normal pt-2" bind:value={data.props.data.client.email}/>
					</div>
					<div class="pt-2 mx-4">
						<div class="opacity-50">Telemóvel</div>
						<input class="input font-normal pt-2" bind:value={data.props.data.client.mobile}/>
					</div>
					<div class="pt-2 mx-4">
						<div class="opacity-50">Telefone</div>
						<input class="input font-normal pt-2" bind:value={data.props.data.client.lanline}/>
					</div>
					<div class="pt-2 mx-4">
						<div class="opacity-50">NIF</div>
						<input class="input font-normal pt-2" bind:value={data.props.data.client.ssn}/>
					</div>
					<div class="pt-2 mx-4">
						<div class="opacity-50">Sexo</div>
						<select class="select" bind:value={selectedGender}>
							{#each genders as gender}
								<option value={gender}>{gender}</option>
							{/each}
						</select>
					</div>
					<div class="pt-2 mx-4">
						<div class="opacity-50">Parceria</div>
						<select class="select" bind:value={selectedPartnership}>
							{#each partnerships as partnership}
								<option value={partnership}>{(partnership) ? partnership.partner : '-'}</option>
							{/each}
						</select>
					</div>
					<div class="pt-2 ml-4 mr-32">
						<div class="opacity-50">Canal de Recomendação</div>
						<select class="select" bind:value={selectedMarketingChannel}>
							{#each marketingChannels as marketing}
								<option value={marketing}>{(marketing) ? marketing.marketingChannel : '-'}</option>
							{/each}
						</select>
					</div>

				{:else}
					<div class="pt-4 mx-4">
						<div class="opacity-50">Rua</div>
						<div class="font-normal pt-2">{data.props.data.client.address.streetName || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Código postal</div>
						<div class="font-normal pt-2">{data.props.data.client.address.zipCode} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Cidade</div>
						<div class="font-normal pt-2">{data.props.data.client.address.city} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Concelho</div>
						<div class="font-normal pt-2">{data.props.data.client.address.concelhoName || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Distrito</div>
						<div class="font-normal pt-2">{data.props.data.client.address.districtName || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Pais</div>
						<div class="font-normal pt-2">{data.props.data.client.address.country || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Localidade</div>
						<div class="font-normal pt-2">{data.props.data.client.address.locality || "-"} </div>
					</div>
				{/if}
				{#if isEditing}
					<div class="relative">
						<button type="button" class="btn bg-primary-200 rounded-full absolute right-3 bottom-3"
						        on:click={updateCard}>
							Salvar
						</button>
					</div>
				{/if}
			</div>
		{:else}
			<div class="flex flex-col text-center">
				<div class="card-header text-lg font-medium">{data.props.data.client.fullName}</div>
				<div class="card-header">{formatDateToDDMMYYYY(data.props.data.client.birthDate)}
					- {calculateAge(data.props.data.client.birthDate)} anos
				</div>
			</div>
			<hr class="my-4 mx-2 !border-primary-400">
			<div class="p-2">
				<div class="flex justify-evenly">
					<button type="button" class="btn bg-primary-200 rounded-full" on:click={() => isClientData = true}>Dados
						Cliente
					</button>
					<button type="button" class="btn bg-primary-200 rounded-full" on:click={() => isClientData = false}>Dados
						Morada
					</button>
				</div>
				{#if isClientData}
					<div class="pt-4 mx-4">
						<div class="opacity-50">Email</div>
						<div class="font-normal pt-2">{data.props.data.client.email || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Telemóvel</div>
						<div class="font-normal pt-2">{data.props.data.client.mobile || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Telefone</div>
						<div class="font-normal pt-2">{data.props.data.client.lanline || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">NIF</div>
						<div class="font-normal pt-2">{data.props.data.client.ssn || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Sexo</div>
						<div class="font-normal pt-2">{translateGender(data.props.data.client.gender)} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Canal de Recomendação</div>
						<div
							class="font-normal pt-2">{(data.props.data.client.marketing !== null) ? data.props.data.client.marketing.marketingChannel : "-"}
						</div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Parceria</div>
						<div
							class="font-normal pt-2">{(data.props.data.client.partnership !== null) ? data.props.data.client.partnership.partner : "-"}
						</div>
					</div>
				{:else}
					<div class="pt-4 mx-4">
						<div class="opacity-50">Rua</div>
						<div class="font-normal pt-2">{data.props.data.client.address.streetName || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Código postal</div>
						<div class="font-normal pt-2">{data.props.data.client.address.zipCode} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Cidade</div>
						<div class="font-normal pt-2">{data.props.data.client.address.city} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Concelho</div>
						<div class="font-normal pt-2">{data.props.data.client.address.concelhoName || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Distrito</div>
						<div class="font-normal pt-2">{data.props.data.client.address.districtName || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Pais</div>
						<div class="font-normal pt-2">{data.props.data.client.address.country || "-"} </div>
					</div>
					<div class="pt-4 mx-4">
						<div class="opacity-50">Localidade</div>
						<div class="font-normal pt-2">{data.props.data.client.address.locality || "-"} </div>
					</div>
				{/if}
				{#if isEditing}
					<div class="relative">
						<button type="button" class="btn bg-primary-200 rounded-full absolute right-3 bottom-3"
						        on:click={updateCard}>
							Salvar
						</button>
					</div>
				{/if}
			</div>
		{/if}
	</div>
	<div class="card h-full bg-surface-300 col-span-2">
		<header class="card-header">{data.props.data.client.fullName}</header>
		<section class="p-4">(content)</section>
	</div>
</div>