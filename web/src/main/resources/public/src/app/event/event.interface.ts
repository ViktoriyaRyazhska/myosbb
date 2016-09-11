import {DateTime} from "ng2-datetime-picker/dist/datetime";
import {User} from "../../shared/models/User";
export class Event {
    id:number;
    title:string;
    author: User;
    description:string;
    start:Date;
    end:Date;
    repeat:string;
    path:string;
    constructor(eventItem?:{id:number, title:string, author:User, description:string, start:string,
                            end:string, repeat:string, path:string}) {
    if(eventItem) {
        Object.assign(this,eventItem);
        }
    }
}