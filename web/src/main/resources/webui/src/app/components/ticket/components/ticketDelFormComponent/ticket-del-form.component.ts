import {
 Component, Output, Input, EventEmitter, OnInit, ViewChild 
} from '@angular/core';
import { ModalDirective } from "ng2-bootstrap/modal";
import {TicketService} from "../../ticket.service";
import {Ticket, TicketState, ITicket} from "../../../../models/ticket.model";

@Component({
  selector: 'ticket-del-form',
  templateUrl: './ticket-del-form.html',
  providers: [TicketService],
  styleUrls: ['../../../../../assets/css/page.layout.scss']
})

export class TicketDelFormComponent {
  @Input() ticket:ITicket;
  @Output() delete:EventEmitter<ITicket>=new EventEmitter<ITicket>();
  @ViewChild('delModal')
  delModal:ModalDirective;

    constructor(private ticketService:TicketService) {}

  public openDelModal(ticket: ITicket):void {
    this.ticket = ticket;
    console.log(this.ticket);
    this.delModal.show();
  }
    delTicket(event:any):void {
        this.ticketService.deleteTicket(this.ticket);
    }
}
