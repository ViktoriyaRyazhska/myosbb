import { User } from './User';
import { Attachment } from './attachment';
import { Street } from './addressDTO';

export interface IOsbb {
    osbbId: number;
    name: string;
    description: string;
    creator:User;
    street: Street;
    address: string;
    district:string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
}

export class Osbb implements IOsbb {
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
}
