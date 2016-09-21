import {Provider} from "./provider.interface";
import {Attachment} from "../../app/user/attachment/attachment.interface";
/**
 * Created by Anastasiia Fedorak on 8/5/16.
 */

export interface Contract {
    contractId: number;
    dateStart: string;
    dateFinish: string;
    text: String;
    price: number;
    priceCurrency: string
    attachments: Array<Attachment>;
    osbb: string;
    provider: Provider;
    active: boolean
}