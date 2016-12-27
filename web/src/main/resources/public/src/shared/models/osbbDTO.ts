import { User } from './User';
import { Attachment } from './attachment';
import { Street, District } from './addressDTO';

export class OsbbDTO {
    osbbId: number;
    name: string;
    description: string;
    street: Street;
    district: District;
    creator: User;
    houseNumber: string;
    districtStr:string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
    countOfHouses: number;
    countOfUsers: number;
}
