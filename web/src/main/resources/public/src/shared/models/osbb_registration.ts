import {IOsbb} from "./osbb";
import {UserRegistration} from "./user_registration";
import {Attachment} from "./attachment";
export class OsbbRegistration {
    osbbId: number;
    name: string;
    description: string;
    creator: UserRegistration;
    address: string;
    district: string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
}