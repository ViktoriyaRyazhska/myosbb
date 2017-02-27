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
  selector: 'ticket-edit-form',
  templateUrl: './ticket-edit-form.html',
  styleUrls: ['../../../../../assets/style/page.layout.scss']
})
export class TicketEditFormComponent {
  @ViewChild('editModal')
  editModal: ModalDirective;
  states: string[];

  private submitAttempt: boolean = false;
  private nameTicket: string = '';
  private descriptionTicket: string = '';
  private assignTicket: string = '';

  public openEditModal() {
    this.editModal.show();
  };

  public isEmptyName(): boolean {
    return this.nameTicket.length >= 10 ? false : true;
  };

  public isEmptyDescription(): boolean {
    return this.descriptionTicket.length >= 20 ? false : true;
  };

  public toggleSubmitAttempt() {
    this.submitAttempt = true;
  };

  public closeEditModal() {
    this.submitAttempt = false;
    this.clearEditModal();
    this.editModal.hide();
  };

  public clearEditModal() {
    this.nameTicket = '';
    this.descriptionTicket = '';
    this.assignTicket = '';
  };

  public initUpdatedTicket() {
      this.openEditModal();
  };
}
