// types.ts

export type District = {
  districtId: number,
  districtName: string
}

export type Concelho = {
  concelhoId: number,
  districtId: number,
  concelhoName: string
}

export type Address = {
  addressID: number;
  districtId: number;
  districtName: string;
  concelhoId: number;
  concelhoName: string;
  streetName: string;
  city: string;
  country: string;
  zipCode: string;
  locality: string;
}

export type Partnership = {
  partnershipID: number;
  partner: string;
  locality: string;
  mobile: string;
  commission: number;
  job: string;
}

export type Marketing = {
  marketingID: number;
  marketingChannel: string;
}

export enum Gender {
  MALE,
  FEMALE,
  OTHER
}

export type Client = {
  clientID: number;
  address: Address;
  partnership?: Partnership;
  marketing?: Marketing;
  dateRegistered: Date;
  fullName: string;
  nameAbbr: string;
  email?: string;
  birthDate?: Date;
  mobile?: string;
  lanline?: string;
  notes?: string;
  gender?: Gender;
  ssn?: string;
}
