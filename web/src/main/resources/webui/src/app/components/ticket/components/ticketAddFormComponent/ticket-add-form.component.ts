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
  @ViewChild('addModal')
  addModal: ModalDirective;

 private currentUser:User;
 private userAssignArr:User[] = [];
 private usersAssignee:Array<User>;
 private addTicketForm:FormGroup;
 private submitAttempt: boolean;
 private nameTicket: string = '';
 private descriptionTicket: string = '';
  private assignTicket: string = '';
  private endTimeStr: string;
  private isNameCorrect:boolean =false;
  private isDescriptionCorrect:boolean =false;
  private isAssigneeCorrect:boolean =false;
  private ticket: Ticket;
  private assigneeItems: string [] =[];
  private name:string='';
  private selectedUserId: any;

  constructor(private ticketSrvice:TicketService,
              private loginService:LoginService,
              private builder:FormBuilder) {
       this.currentUser=this.loginService.getUser();
        this.created = new EventEmitter<Ticket>();
        this.ticket = new Ticket("","",TicketState.NEW,null);
      
   }

   ngOnInit(){

    this.buildForm();
    console.log('ticket add onInit');
    this.listAllUsers();
    for(let user of this.userAssignArr ){
    console.log(user);
    }
   };

buildForm():void{
this.addTicketForm=this.builder.group({
name: ['',[Validators.required]],
description: ['',[Validators.required]],
assignee: [null,[Validators.required]],
endTimeIntput:['',[Validators.required]]
});
}

  public openAddModal() {
    this.addModal.show(); 
  }
getAllOsbbUsers(){
   console.log(this.currentUser.osbbId);
return this.ticketSrvice.getAllUsers(this.currentUser.osbbId)
.then(userAssignArr =>this.userAssignArr=userAssignArr)
}

listAllUsers(){
  this.ticketSrvice.listAllUsersInOsbb(this.currentUser.osbbId).subscribe((data)=>{
  this.usersAssignee=data;
  this.assigneeItems=this.fillAssigneeItems();
},
(error)=>{
  this.henleError(error);
});
}

fillAssigneeItems():string[]{
let opts = new Array(this.usersAssignee.length)
for(let user of this.usersAssignee ){
  opts.push({
    id: user.userId,
    text: user.firstName.toString()+' '+user.lastName.toString()
  })
}
return opts.slice(0);
}

selectUser(value: any):string{
this.assignTicket=value.text;
this.selectedUserId=value.id;
console.log('selectedUserId: '+this.selectedUserId);
console.log(this.assignTicket);
return this.assignTicket;
}
removedUser(value: any){
  console.log('removeUser($event)'+value);
 this.assignTicket=value.text;
}


  public isEmptyName(event:any): boolean {
    this.isNameCorrect = this.addTicketForm.value.name.length <= 10 ? true : false;
    return this.addTicketForm.value.name.length <= 10 ? true : false;
  }

  public isEmptyDescription(event:any): boolean {
    this.isDescriptionCorrect = this.addTicketForm.value.description.length <= 10 ? true : false;
    return this.addTicketForm.value.description.length <= 20 ? true : false;
  }

  public isEmptyAssignee(value :any): boolean {
    this.isAssigneeCorrect= this.addTicketForm.value.assignee.length <= 0 ? true : false;
    return this.isAssigneeCorrect;
  }

  public toggleSubmitAttempt() {
    this.submitAttempt = true;
  }

  public closeAddModal() {
    this.submitAttempt = false;
    this.clearAddModal();
    this.addModal.hide();
    this.ticket = new Ticket("","",TicketState.NEW,null);
  }

  public clearAddModal() {
  this.isNameCorrect=false;
   this.isDescriptionCorrect=false;
  this.isAssigneeCorrect =false;
  this.buildForm();
  }

public onCreateTicket() {
  console.log("On Create Ticket Start");
     this.ticketSrvice.addTicket(this.createTicket());
     this.closeAddModal();
 
    console.log("On Create Ticket End");
  }
      createTicket():Ticket {
       console.log("Create Ticket Start");
       let ticket = new Ticket(this.nameTicket, this.descriptionTicket, TicketState.NEW,null);
       ticket.user = this.currentUser;
      //  console.log("assign "+this.getAssignedId(this.assignTicket));
      //  ticket.assigned = this.getAssignedId(this.assignTicket);
       ticket.deadline = this.castDeadLineStringToDate();
         console.log("Create Ticket End");
        return ticket;

    }
    getAssignedId(assignees:Array<User>):User {
      console.log('selected userid: '+this.selectedUserId);
      console.log('length: '+assignees.length);
        for (let i = 0; i < assignees.length; i++) {
          console.log(assignees[i])
            if (assignees[i].userId==this.selectedUserId) {
                return assignees[i];
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
    }
private henleError(error:any):Promise<any>{
console.log('Add ticket Hendle Error',error)
return Promise.reject(error.message || error)
}
    // Saving the ticket
    onSubmit(addTicketForm: FormGroup) {
      this.nameTicket=addTicketForm.value.name;
      this.descriptionTicket=addTicketForm.value.description;
      
      console.log(this.nameTicket);
      console.log(this.descriptionTicket);
      console.log(this.currentUser);
      console.log('assignee: '+this.assignTicket);
      console.log(addTicketForm.value.endTimeIntput);
      console.log(this.getAssignedId( this.usersAssignee));
      this.ticket.name=addTicketForm.value.name;
      this.ticket.description=addTicketForm.value.description;
      this.ticket.deadline=new Date(addTicketForm.value.endTimeIntput);
      this.ticket.state=TicketState.NEW;
      this.ticket.assigned=this.getAssignedId( this.usersAssignee);
      this.ticket.user=this.currentUser;

      this.ticketSrvice.addTicket(this.ticket)

       
        
        this.closeAddModal();
    }

}
