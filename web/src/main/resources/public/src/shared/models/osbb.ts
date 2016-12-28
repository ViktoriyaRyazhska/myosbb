import { User } from './User';
import { Attachment } from './attachment';
import { Street, District } from './addressDTO';

export interface IOsbb {
    osbbId: number;
    name: string;
    description: string;
    creator:User;
    street: Street;
    district: District;
    houseNumber: string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
}

export class Osbb implements IOsbb {
    osbbId: number;
    name: string;
    description: string;
    street: Street;
    district: District;
    creator: User;
    houseNumber: string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
}
