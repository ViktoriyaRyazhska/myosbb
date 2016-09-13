import { Osbb } from './osbb';
export interface IHouse {
    houseId: number;
    city: string;
    street: string;
    zipCode:string;
    description: string;
    osbbId:Osbb;
}

export class House implements IHouse {
    houseId: number;
    city: string;
    street: string;
    zipCode:string;
    description: string;
    osbbId:Osbb;
}
