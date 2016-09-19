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

}

export class Message implements IMessage {
    messageId:number;
    parentId:number;
    message:string;
    time:Date;
    idTicket:number;
    user:User;
    answers:Message[];


    constructor(message:string) {
        this.message = message;
    }
}


