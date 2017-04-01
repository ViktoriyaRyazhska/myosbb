import {
  Component,
  Output,
  Input,
  EventEmitter,
  OnInit,
  ViewChild
} from '@angular/core';

import { DatePipe } from '@angular/common';
import { ModalDirective } from "ng2-bootstrap/modal";
import { Ticket, TicketState, ITicket } from "../../../../models/ticket.model";
import { User } from "../../../../models/user.model";
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TicketService } from "../../ticket.service";
import { LoginService } from '../../../../shared/login/login.service';
import { UsersService } from '../../../users/users.service';

@Component({
  selector: 'ticket-edit-form',
  providers: [TicketService],
  templateUrl: './ticket-edit-form.html',
  styleUrls: ['../../../../../assets/css/page.layout.scss']
})
export class TicketEditFormComponent implements OnInit{
 @Output() update: EventEmitter<Ticket> = new EventEmitter<Ticket>();
//  @Input() ticket: Ticket;
  @ViewChild('editModal')
  editModal: ModalDirective;
  states: string[];

  private submitAttempt: boolean = false;
  private nameTicket: string = '';
  private descriptionTicket: string = '';
  private assignTicket: string = '';
  private userAssignArr: User[] = [];
  private currentUser: User;
  private editTicketForm: FormGroup;
  private usersAssignee: Array<User>;
  private assigneeItems: string[] = [];
  private isNameCorrect: boolean = true;
  private isDescriptionCorrect: boolean = true;
  private isAssigneeCorrect: boolean = true;
  private isDateCorrect: boolean = true;
  private selectedUserId: any;
  private ticket:Ticket=new Ticket("","",TicketState.NEW,null);
  private testDate:Date=new Date('2014-01-02T11:42:13.510');

  constructor(private ticketSrvice: TicketService,
    private builder: FormBuilder) {
  }
  ngOnInit() {

    this.buildForm();
    console.log('ticket edit onInit');
   
    for (let user of this.userAssignArr) {
      console.log(user);
    }
  };

  public openEditModal() {
    this.editModal.show();
  };

  buildForm(): void {
    this.editTicketForm = this.builder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      assignee: [null, [Validators.required]],
      endTimeIntput: [this.testDate, [Validators.required]]
    });
  };


   listAllUsers(currentUser:any) {
     console.log(" listAllUsers");
     console.log(currentUser);
    this.ticketSrvice.listAllUsersInOsbb(currentUser.osbbId).subscribe((data) => {
      this.usersAssignee = data;
      this.assigneeItems = this.fillAssigneeItems();
    },
      (error) => {
        this.henleError(error);
      });
  };

  fillAssigneeItems(): string[] {
    let opts = new Array(this.usersAssignee.length)
    for (let user of this.usersAssignee) {
      opts.push({
        id: user.userId,
        text: user.firstName.toString() + ' ' + user.lastName.toString()
      })
    }
    return opts.slice(0);
  }

  public isEmptyName(event: any): boolean {
    this.isNameCorrect = this.editTicketForm.value.name.length <= 10 ? true : false;
    return this.editTicketForm.value.name.length <= 10 ? true : false;
  }

  public isEmptyDescription(event: any): boolean {
    this.isDescriptionCorrect = this.editTicketForm.value.description.length <= 10 ? true : false;
    return this.editTicketForm.value.description.length <= 20 ? true : false;
  }

  selectUser(value: any): string {
    this.assignTicket = value.text;
    this.selectedUserId = value.id;
    console.log('selectedUserId: ' + this.selectedUserId);
    console.log(this.assignTicket);
    this.isAssigneeCorrect = false;
    return this.assignTicket;
  }
  removedUser(value: any) {
    this.assignTicket = '';
    this.isAssigneeCorrect = true;
    this.editTicketForm.value.endTimeIntput
  }

  public isEmptyAssignee(value: any): boolean {
    this.isAssigneeCorrect = this.editTicketForm.value.assignee.length <= 0 ? true : false;
    this.toggleSubmitAttempt();
    return this.isAssigneeCorrect;
  }

  isDeadLineCorrect(event: any): boolean {
    this.ticket.deadline = new Date(this.editTicketForm.value.endTimeIntput);
    let startTime = new Date();
    let res = this.ticket.deadline.valueOf() - startTime.valueOf();
    this.isDateCorrect = res < 0;
    this.toggleSubmitAttempt();
    return res > 0;
  }
  isDeadLineSet(): boolean {
    this.ticket.deadline = new Date(this.editTicketForm.value.endTimeIntput);
    let startTime = new Date();
    let res = this.ticket.deadline.valueOf() - startTime.valueOf();
    this.isDateCorrect = res < 0;
    return res > 0;
  }

  public toggleSubmitAttempt() {
    this.submitAttempt = true;
  };

  public closeEditModal() {
    this.submitAttempt = false;
    this.clearEditModal();
    this.editModal.hide();
  };

  public clearEditModal() {
    this.isNameCorrect = true;
    this.isDescriptionCorrect = true;
    this.isAssigneeCorrect = true;
    this.isDateCorrect = true;
    this.submitAttempt = false;
    this.buildForm();
  }

  public initUpdatedTicket(ticket:Ticket) {
    this.ticket=ticket;
    this.currentUser=ticket.assigned;
      this.editTicketForm = this.builder.group({
      name: [this.ticket.name, [Validators.required]],
      description: [this.ticket.description, [Validators.required]]
     });
    this.openEditModal();
  };


  private henleError(error: any): Promise<any> {
    console.log('Add ticket Hendle Error', error)
    return Promise.reject(error.message || error)
  };
    getAssignedId(assignees:Array<User>):User {
        for (let i = 0; i < assignees.length; i++) {
          if (assignees[i].userId==this.selectedUserId) {
                return assignees[i];
            }
        } 
    }

createTicket(editTicketForm: FormGroup):Ticket{

      this.nameTicket=editTicketForm.value.name;
      this.descriptionTicket=editTicketForm.value.description;
      this.ticket.name=editTicketForm.value.name;
      this.ticket.description=editTicketForm.value.description;
      this.ticket.state=TicketState.NEW;
      this.ticket.assigned=this.getAssignedId( this.usersAssignee);
      this.ticket.user=this.currentUser;
      console.log('tickeEditComponent create: '+this.ticket.name)
      return this.ticket;
}

    // Saving the ticket
    onSubmit(editTicketForm: FormGroup) {
if(!this.isNameCorrect&&!this.isDescriptionCorrect&&!this.isAssigneeCorrect&&!this.isDateCorrect){
 this.update.emit(this.createTicket(editTicketForm));

 this.closeEditModal();
}};
}
