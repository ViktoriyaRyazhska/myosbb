import { User } from './User';
import {Attachment} from './attachment';
export interface IOsbb {
    osbbId: number;
    name: string;
    description: string;
    creator:User;
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
    creator: User;
    address: string;
    district:string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
}
