import { User } from './User';
import { Attachment } from './attachment';
import { Street } from './addressDTO';

export class OsbbDTO {
    osbbId: number;
    name: string;
    description: string;
    street: Street;
    creator: User;
    address: string;
    district:string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
    countOfHouses: number;
    countOfUsers: number;
}
