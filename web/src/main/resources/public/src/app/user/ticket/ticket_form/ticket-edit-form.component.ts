import { Component, Output, Input, EventEmitter, OnInit, ViewChild} from '@angular/core';
import {CORE_DIRECTIVES,FORM_DIRECTIVES, FormBuilder, Control, ControlGroup, Validators} from '@angular/common';
import { ITicket,Ticket,TicketState} from '../ticket';
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS} from 'ng2-bootstrap/ng2-bootstrap';
import {ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {User} from './../../../../shared/models/User';
import {CurrentUserService} from "./../../../../shared/services/current.user.service";
import { TicketService } from './../ticket.service';
import { TicketFilter } from './../ticket.filter';
import {TranslatePipe} from "ng2-translate/ng2-translate";
import {HeaderComponent} from "../../../header/header.component";
import {FileSelectDirective, FileDropDirective} from "ng2-file-upload";
import {FileUploadComponent} from "../../../admin/components/attachment/modals/file-upload-modal";
import {Attachment} from "../../../admin/components/attachment/attachment.interface";
@Component({
    selector: 'ticket-edit-form',
    templateUrl: './src/app/user/ticket/ticket_form/ticket-edit-form.html',
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, FORM_DIRECTIVES,
        FileSelectDirective, FileDropDirective, FileUploadComponent],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TicketFilter, TranslatePipe],
    styleUrls: ['src/app/user/ticket/ticket.css']
})
export class TicketEditFormComponent implements OnInit {
    @Output() update:EventEmitter<Ticket>;
    @Input() ticket:ITicket;
    @ViewChild('editModal')
    editModal:ModalDirective;
    states:string[];

    private stateTicket = "NEW";
    private userAssignArr:User[] = [];
    private assign:User;
    private creatingForm:ControlGroup;
    private nameInput:Control;
    private descriptionInput:Control;
    private assignInput:Control;
    private submitAttempt:boolean = false;
    private nameTicket:string = '';
    private descriptionTicket:string = '';
    private assignTicket:string = '';
    private discussed:Date;
    private currentUser:User;
    private _currentUserService = null;
    private attachments:Attachment[] = [];

    constructor(private ticketService:TicketService,
                private builder:FormBuilder) {
        this._currentUserService = HeaderComponent.currentUserService;
        this.currentUser = this._currentUserService.getUser();
        this.ticket = new Ticket("", "", TicketState.NEW,new Date());
        this.ticket.attachments = [];
        this.attachments = this.ticket.attachments;
        this.update = new EventEmitter<Ticket>();
        this.states = ["NEW", "IN_PROGRESS", "DONE"];
        this.assign = new User();
        this.ticket.assigned = new User();
        this.nameInput = new Control('', Validators.required);
        this.descriptionInput = new Control('', Validators.required);
        this.assignInput = new Control('', Validators.required);
        this.discussed = null;
        this.creatingForm = builder.group({
            nameInput: this.nameInput,
            descriptionInput: this.descriptionInput,
            assignInput: this.assignInput
        });


    }

    openEditModal() {
        this.editModal.show();
        this.attachments = this.ticket.attachments;        
    }


    ngOnInit() {
         return this.ticketService.getAllUsers(this.currentUser.osbbId)
            .then(userAssignArr => this.userAssignArr = userAssignArr);      
    }

    isEmptyName():boolean {
        return this.nameTicket.length >=10 ? false : true;
    }

    isEmptyDescription():boolean {
        return this.descriptionTicket.length >= 20 ? false : true;
    }
   

    isFindAssign():boolean {
        if (this.getAssignedId(this.assignTicket) == null) {
            return false;
        }
        return true;
    }


    getAllUsers() {
        return this.ticketService.getAllUsers(this.currentUser.osbbId)
            .then(userAssignArr => this.userAssignArr = userAssignArr);
    }

    toggleSubmitAttempt() {
        this.submitAttempt = true;
    }
deleteAttachmet(attachment:Attachment){
    let index = this.ticket.attachments.indexOf(attachment);
        if (index > -1) {
            this.ticket.attachments.splice(index,1);
        }
        
 }

  public onUpload(attachments:Attachment[]) {
        if (this.editModal.isShown) {
            for(let i=0;i<attachments.length;i++){
                this.attachments.unshift(attachments[i]);
        }
            
  }}

    closeEditModal() {
        this.submitAttempt = false;
        this.clearEditModal();
        this.editModal.hide();
    }

    clearEditModal() {
        this.ticket.attachments = [];
        this.attachments = [];
        this.nameTicket = '';
        this.descriptionTicket = '';
        this.assignTicket = '';

    }

    onEditTicket() {
        if (this.nameInput.valid && this.descriptionInput.valid && this.assignInput.valid&&
            !this.isEmptyDescription()&&!this.isEmptyName()&&this.isFindAssign()) {
            let ticket = this.editTicket();
            this.update.emit(ticket);            
            this.closeEditModal();
        }
    }

    editState(state:string):TicketState{
        if (state == 'NEW') {
            return TicketState.NEW;
        }
        if (state == 'IN_PROGRESS') {
            return TicketState.IN_PROGRESS;
        }
        else if (state == 'DONE') {
            return TicketState.DONE;
        }

    }

    initUpdatedTicket(ticket:Ticket) {
        console.log("is ticketttt"+ticket.ticketId);
        this.ticket = ticket;
        this.nameTicket = ticket.name;
        this.descriptionTicket = ticket.description;
        this.assignTicket = ticket.assigned.firstName + " " + ticket.assigned.lastName;
        this.openEditModal();
        
    }

    editTicket():Ticket {
        console.log("edit ticket");
        let ticket = new Ticket(this.nameTicket, this.descriptionTicket, TicketState.NEW, this.discussed );
        ticket.ticketId = this.ticket.ticketId;
        ticket.user = this.currentUser;
        ticket.assigned = this.getAssignedId(this.assignTicket);
        ticket.state  = this.editState(this.stateTicket);
        ticket.attachments = this.attachments;
        console.log("edit state  "+ this.stateTicket);
        return ticket;
    }

    getAttachments():Attachment[]{
        for(let i=0;i<this.ticket.attachments.length;i++){
        this.attachments.push(this.ticket.attachments[i]);
        }
        console.log("getAttachments "+JSON.stringify(this.attachments));
        
        return this.attachments;

    }

    getAssignedId(assign:string):User {
        let str = assign.split(' ');
        for (let i = 0; i < this.userAssignArr.length; i++) {
            if (str[0] == this.userAssignArr[i].firstName && str[1] == this.userAssignArr[i].lastName) {
                return this.userAssignArr[i];
            }
        }
    }

}