import { Attachment } from './attachment';
import { Street, District } from './addressDTO';

export class OsbbDTO {
    osbbId: number;
    name: string;
    description: string;
    street: Street;
    address: string;
    district: District;
    houseNumber: string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
    countOfHouses: number;
    countOfUsers: number;
}
