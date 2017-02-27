import {
  Component,
  ViewChild
} from '@angular/core';
import { ModalDirective } from "ng2-bootstrap/modal";

@Component({
  selector: 'ticket-del-form',
  templateUrl: './ticket-del-form.html',
  styleUrls: ['../../../../../assets/style/page.layout.scss']
})

export class TicketDelFormComponent {
  private ticket: string;
  @ViewChild('delModal')
  delModal:ModalDirective;

  public openDelModal(ticket: string):void {
    this.ticket = ticket;
    this.delModal.show();
  }
}
