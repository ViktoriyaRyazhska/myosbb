import {ProviderType} from "./provider.type.interface";
import {Attachment} from "./attachment";

export interface Provider {
    providerId:number;
    name:string,
    description:string,
    logoUrl:string,
    periodicity:string,
    type: ProviderType,
    email:string,
    phone:string,
    address:string,
    schedule: string,
    active: boolean,
    attachments: Array<Attachment>;
}