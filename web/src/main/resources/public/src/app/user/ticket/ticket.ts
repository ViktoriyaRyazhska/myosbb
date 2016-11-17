import {DatePipe} from '@angular/common' ;
import {Message} from './single_ticket/message' ;
import {User} from './../user';
import {Attachment} from "../../admin/components/attachment/attachment.interface";

export interface ITicket {
    ticketId: number;
    name: string;
    description: string;
    state: TicketState;
    statetime:Date;
    discussed:Date;
    deadline:Date;
    time:Date;
    user:User;
    assigned:User;
    attachments:Attachment[];

    
}

export class Ticket implements ITicket {
    ticketId:number;
    name:string;
    description:string;
    state:TicketState;
    statetime:Date;
    discussed:Date;
    deadline:Date;
    time:Date;
    user:User;
    assigned:User;
        attachments:Attachment[];



    constructor(name:string, description:string, state:TicketState) {
        this.name = name;
        this.description = description;
        this.state = state;
    }
}

export enum TicketState{
    NEW,
    IN_PROGRESS,
    DONE
}