import { Attachment } from './attachment.model';
import { Street, District } from './address-dto.model';

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
