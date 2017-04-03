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

export class TicketAddFormComponent implements OnInit{
  @Output() created:EventEmitter<Ticket>=new EventEmitter<Ticket>();;
  @Input() ticket:Ticket;
  @ViewChild('addModal')
  addModal: ModalDirective;

 private currentUser:User;
 private userAssignArr:User[] = [];
 private usersAssignee:Array<User>;
 private addTicketForm:FormGroup;
 private submitAttempt: boolean=false;
 private nameTicket: string = '';
 private descriptionTicket: string = '';
  private assignTicket: string = '';
  private endTimeStr: string;
  private isNameCorrect:boolean =true;
  private isDescriptionCorrect:boolean =true;
  private isAssigneeCorrect:boolean =true;
  private isDateCorrect: boolean =true;
  private assigneeItems: string [] =[];
  private name:string='';
  private selectedUserId: any;

  constructor(private ticketSrvice:TicketService,
              private loginService:LoginService,
              private builder:FormBuilder) {
       this.currentUser=this.loginService.getUser();
        this.ticket = new Ticket("","",TicketState.NEW,null);
      
   }

   ngOnInit(){

    this.buildForm();
    console.log('ticket add onInit');
    this.listAllUsers();
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
this.isAssigneeCorrect=false;
return this.assignTicket;
}
removedUser(value: any){
 this.assignTicket='';
 this.isAssigneeCorrect=true;
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
    this.toggleSubmitAttempt();
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
    this.isNameCorrect=true;
    this.isDescriptionCorrect=true;
    this.isAssigneeCorrect =true;
    this.isDateCorrect=true;
    this.submitAttempt = false;
    this.buildForm();
  }

    getAssignedId(assignees:Array<User>):User {
        for (let i = 0; i < assignees.length; i++) {
          if (assignees[i].userId==this.selectedUserId) {
                return assignees[i];
            }
        } 
    }
        isDeadLineCorrect(event:any): boolean {
        this.ticket.deadline=new Date(this.addTicketForm.value.endTimeIntput);
        let startTime = new Date();
        let res = this.ticket.deadline.valueOf() - startTime.valueOf();
        this.isDateCorrect=res < 0;
        this.toggleSubmitAttempt();
        return res > 0;
    }

      isDeadLineSet():boolean{
        this.ticket.deadline=new Date(this.addTicketForm.value.endTimeIntput);
        let startTime = new Date();
        let res = this.ticket.deadline.valueOf() - startTime.valueOf();
        this.isDateCorrect=res < 0;
        return res > 0;
      }

    castDeadLineStringToDate(): Date {
        return new Date(this.endTimeStr);
    }

private henleError(error:any):Promise<any>{
console.log('Add ticket Hendle Error',error)
return Promise.reject(error.message || error)
}

createTicket(addTicketForm: FormGroup):Ticket{

 this.nameTicket=addTicketForm.value.name;
      this.descriptionTicket=addTicketForm.value.description;
      this.ticket.name=addTicketForm.value.name;
      this.ticket.description=addTicketForm.value.description;
      this.ticket.state=TicketState.NEW;
      this.ticket.assigned=this.getAssignedId( this.usersAssignee);
      this.ticket.user=this.currentUser;
      console.log('ticketaddComponent create: '+this.ticket.name)
      return this.ticket;
}

    // Saving the ticket
    onSubmit(addTicketForm: FormGroup) {
if(!this.isNameCorrect&&!this.isDescriptionCorrect&&!this.isAssigneeCorrect&&!this.isDateCorrect){
 this.created.emit(this.createTicket(addTicketForm));
//this.ticketSrvice.addTicket(this.createTicket(addTicketForm))
 this.closeAddModal();
}
    }

}
