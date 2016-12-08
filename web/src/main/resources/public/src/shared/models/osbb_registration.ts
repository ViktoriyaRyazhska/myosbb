import { IOsbb } from "./osbb";
import { UserRegistration } from "./user_registration";
import { Attachment } from "./attachment";
import { Street } from "./addressDTO";

export class OsbbRegistration {
    osbbId: number;
    name: string;
    description: string;
    creator: UserRegistration;
    street: Street;
    address: string;
    district: string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
}