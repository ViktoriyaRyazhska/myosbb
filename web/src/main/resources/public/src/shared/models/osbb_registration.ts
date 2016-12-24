import { IOsbb } from "./osbb";
import { Attachment } from "./attachment";
import { Street } from "./addressDTO";

export class OsbbRegistration {
    osbbId: number;
    name: string;
    description: string;
    street: Street;
    address: string;
    district: string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
}