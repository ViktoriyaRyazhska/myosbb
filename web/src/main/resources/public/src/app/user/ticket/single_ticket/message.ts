import {DatePipe} from '@angular/common' ;
import {User} from './../../user';

export interface IMessage {
    messageId: number;
    parentId:number;
    message: string;
    time:Date;
    idTicket:number;
    user:User;
    answers:Message[];
    discussed:Date;
    deadline:Date;
    date:Date;
}

export class Message implements IMessage {
    messageId:number;
    parentId:number;
    message:string;
    time:Date;
    idTicket:number;
    user:User;
    answers:Message[];
    discussed:Date;
    deadline:Date;
    date:Date;

    constructor(message:string) {
        this.message = message;
    }
}


