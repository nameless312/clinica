// types.d.ts

export interface Address {
  addressID: number;
  districtId: number;
  districtName: string;
  concelhoId: number;
  concelhoName: string;
  streetName: string;
  city: string;
  zipCode: string;
  locality: string;
}

export interface Partnership {
  partnershipID: number;
  partner: string;
  locality: string;
  mobile: string;
  commission: number;
  job: string;
}

export interface Marketing {
  marketingID: number;
  marketingChannel: string;
}

export enum Gender {
  MALE,
  FEMALE
}

export interface Client {
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
