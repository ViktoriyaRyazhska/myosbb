import { Osbb } from './osbb';
import { Street } from './addressDTO'

export class House implements House {
    houseId: number;
    numberHouse:number;
    zipCode:string;
    description: string;
    street: Street;
    osbb:Osbb;
}
