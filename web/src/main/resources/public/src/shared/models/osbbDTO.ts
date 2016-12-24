import { Attachment } from './attachment';
import { Street } from './addressDTO';

export class OsbbDTO {
    osbbId: number;
    name: string;
    description: string;
    street: Street;
    address: string;
    district:string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
    countOfHouses: number;
    countOfUsers: number;
}
