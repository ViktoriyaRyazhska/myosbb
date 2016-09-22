import { User } from './User';
import {Attachment} from './attachment';

export class OsbbDTO {
    osbbId: number;
    name: string;
    description: string;
    creator: User;
    address: string;
    district:string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
    countOfHouses: number;
    countOfUsers: number;
}
