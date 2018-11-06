import { Address } from "./address";

export interface IPatient {
  id: number;
  name: string;
  phone: string;
  address: Address;
}
