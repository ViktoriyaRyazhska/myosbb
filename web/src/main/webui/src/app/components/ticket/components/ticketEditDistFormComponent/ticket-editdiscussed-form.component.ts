import {
  Component,
  Output,
  Input,
  EventEmitter,
  OnInit,
  ViewChild
} from '@angular/core';
import { ModalDirective } from "ng2-bootstrap/modal";

@Component({
  selector: 'ticket-editdiscussed-form',
  templateUrl: './ticket-editdiscussed-form.html',
  styleUrls: ['../../../../../assets/style/page.layout.scss']
})

export class TicketEditDiscussedFormComponent {
  @ViewChild('editModal')
  editModal:ModalDirective;
    states:string[];
    private submitAttempt:boolean = true;
    constructor() { }

    public openEditModal() {
      this.editModal.show();
    }
    public toggleSubmitAttempt() {
        this.submitAttempt = true;
    }
     public closeEditModal() {
        this.editModal.hide();
    }
    public initUpdatedTicket() {
      this.openEditModal();
    }
}
