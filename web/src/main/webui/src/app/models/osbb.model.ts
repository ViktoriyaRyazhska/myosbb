import { Attachment } from './attachment.model';
import { Street, District } from './address-dto.model';

export class Osbb {
    osbbId: number;
    name: string;
    description: string;
    street: Street;
    district: District;
    houseNumber: string;
    logo: Attachment;
    creationDate: Date;
    available: boolean;
}

