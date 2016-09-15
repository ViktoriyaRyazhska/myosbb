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
    status:string;
    constructor(eventItem?:{id:number, title:string, author:User, description:string, start:string,
                            end:string, repeat:string, path:string, status:string}) {
    if(eventItem) {
        Object.assign(this,eventItem);
        }
    }
}