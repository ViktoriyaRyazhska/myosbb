import { Street } from "../../shared/models/addressDTO";

export class HousePageObject {
    houseId: number;
    street: Street;
    zipCode: string;
    description: string;
    osbbName: string;
    apartmentCount: number;
    numberOfInhabitants: number;
}

