import { User } from "../../shared/models/User";
import { Attachment } from "../admin/components/attachment/attachment.interface";
export class Event {
    id:number;
    title:string;
    author: User;
    description:string;
    start:Date;
    end:Date;
    repeat:string;
    attachments:Attachment[];
    status:string;
    constructor(eventItem?:{id:number, title:string, author:User, description:string, start:string,
                            end:string, repeat:string, attachments:Attachment[], status:string}) {
    if(eventItem) {
        Object.assign(this,eventItem);
        }
    }
}