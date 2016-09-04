import { User } from './User';
export interface IOsbb {
    osbbId: number;
    name: string;
    description: string;
    creator:User;
    address: string;
    district:string;
    logo:any;
    creationDate: Date;
}

export class Osbb implements IOsbb {
    osbbId: number;
    name: string;
    description: string;
    creator: User;
    address: string;
    district:string;
    logo:any;
    creationDate: Date;
}