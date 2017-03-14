import {
  Component,
  Output,
  Input,
  EventEmitter,
  OnInit,
  ViewChild
} from '@angular/core';

import { DatePipe } from '@angular/common' ;
import { ModalDirective } from "ng2-bootstrap/modal";
import {Ticket, TicketState, ITicket} from "../../../../models/ticket.model";
import {User} from "../../../../models/user.model";
import { FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TicketService} from "../../ticket.service";
import { LoginService } from '../../../../shared/login/login.service';

@Component({
    selector: 'ticket-add-form',
    providers: [TicketService],
    templateUrl: './ticket-add-form.html',
    styleUrls: ['../../../../../assets/css/page.layout.scss']
})

export class TicketAddFormComponent {
  @Output() created:EventEmitter<Ticket>;
  @Input() ticket: Ticket;
  @ViewChild('addModal')
  addModal: ModalDirective;

 private currentUser:User;
 private userAssignArr:User[] = [];
 private creatingForm:FormGroup;
 private nameInput:FormControl;
 private descriptionInput:FormControl;
 private assignInput:FormControl;
 private submitAttempt: boolean;
 private nameTicket: string = '';
  private descriptionTicket: string = '';
  private assignTicket: string = '';
  private endTimeStr: string;
 private builder:FormBuilder;
  constructor() {
        this.created = new EventEmitter<Ticket>();
        this.ticket = new Ticket("","",TicketState.NEW,null);
        this.submitAttempt = false;
        this.nameInput = new FormControl('', Validators.required);
        this.descriptionInput = new FormControl('', Validators.required);
        this.assignInput = new FormControl('', Validators.required);
        this.builder = new FormBuilder();
        this.creatingForm =this.builder.group({
            nameInput: this.nameInput,
            descriptionInput: this.descriptionInput,
            assignInput: this.assignInput
        });
   }

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
    if (this.nameInput.valid && this.descriptionInput.valid && this.assignInput.valid&&
             !this.isEmptyDescription()&&!this.isEmptyName()&&this.isFindAssign() && this.isDeadLineCorrect()) {
            this.created.emit(this.createTicket());
            this.closeAddModal();
        }
    this.closeAddModal();
  };
      createTicket():Ticket {
        console.log("Create Ticket Start");
        let ticket = new Ticket(this.nameTicket, this.descriptionTicket, TicketState.NEW,null);
        ticket.user = this.currentUser;
       // ticket.attachments = this.attachments;
        ticket.assigned = this.getAssignedId(this.assignTicket);
        ticket.deadline = this.castDeadLineStringToDate();
         console.log("Create Ticket End");
        return ticket;

    }
    getAssignedId(assign:string):User {
        let str = assign.split(' ');
        for (let i = 0; i < this.userAssignArr.length; i++) {
            if (str[0] == this.userAssignArr[i].firstName && str[1] == this.userAssignArr[i].lastName) {
                return this.userAssignArr[i];
            }
        } 
    }
        isDeadLineCorrect(): boolean {
        let startTime = new Date();
        let res = this.castDeadLineStringToDate().valueOf() - startTime.valueOf();
        return res > 0;
    }
    castDeadLineStringToDate(): Date {
        return new Date(this.endTimeStr);
    };
}
