import { Osbb } from './osbb.model';
import { Street } from './addressDTO.model'

export class House {
  houseId: number;
  numberHouse:number;
  zipCode:string;
  description: string;
  street: Street;
  osbb:Osbb;
}
