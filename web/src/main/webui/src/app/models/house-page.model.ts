import { Street } from "./address-dto.model";

export class HousePage {
    houseId: number;
    street: Street;
    zipCode: string;
    description: string;
    osbbName: string;
    apartmentCount: number;
    numberOfInhabitants: number;
}

