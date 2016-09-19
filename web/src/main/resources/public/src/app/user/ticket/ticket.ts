import {DatePipe} from '@angular/common' ;
import {Message} from './single_ticket/message' ;
import {User} from './../user';
export interface ITicket {
    ticketId: number;
    name: string;
    description: string;
    state: TicketState;
    statetime:Date;
    time:Date;
    user:User;
    assigned:User;
}

export class Ticket implements ITicket {
    ticketId:number;
    name:string;
    description:string;
    state:TicketState;
    statetime:Date;
    time:Date;
    user:User;
    assigned:User;


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