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
    selector: 'ticket-add-form',
    templateUrl: './ticket-add-form.html',
    styleUrls: ['../../../../../assets/style/page.layout.scss']
})

export class TicketAddFormComponent {
  @Input() ticket: string;
  @ViewChild('addModal')
  addModal: ModalDirective;

  private submitAttempt: boolean;
  private nameTicket: string = '';
  private descriptionTicket: string = '';
  private assignTicket: string = '';
  private endTimeStr: string;

  constructor() { }

  public openAddModal() {
    this.addModal.show();
  };

  public isEmptyName(): boolean {
    return this.nameTicket.length >= 10 ? false : true;
  };

  public isEmptyDescription(): boolean {
    return this.descriptionTicket.length >= 20 ? false : true;
  };

  public isFindAssign(): boolean {
    return true;
  };

  public toggleSubmitAttempt() {
    this.submitAttempt = true;
  };

  public closeAddModal() {
    this.submitAttempt = false;
    this.clearAddModal();
    this.addModal.hide();
  };

  public clearAddModal() {
    this.nameTicket = '';
    this.descriptionTicket = '';
    this.assignTicket = '';
    this.endTimeStr = '';
  };

  public onCreateTicket() {
    this.closeAddModal();
  };

  public removeAttachment(attachments) {
    if (this.addModal.isShown) {
      let index = this.ticket.indexOf(attachments);
    }
  };
}
