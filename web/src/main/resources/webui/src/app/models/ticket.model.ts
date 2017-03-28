import { DatePipe } from '@angular/common' ;
import { Message } from './single_ticket/message' ;
import { User } from './user.model';
import { Attachment } from "../../admin/components/attachment/attachment.interface";
import { Apartment } from './apartment.model';
import { House } from './house.model';

export interface ITicket {
    ticketId: number;
    name: string;
    description: string;
    state: TicketState;
    statetime:Date;
    discussed:Date;
    deadline:Date;
    time:Date;
    user: User;
    assigned:User;
  //  attachments:Attachment[];

    
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
 //       attachments:Attachment[];

    constructor(name:string, description:string, state:TicketState, discussed:Date) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.discussed = discussed;
    }
}

export enum TicketState{
    NEW,
    IN_PROGRESS,
    DONE
}