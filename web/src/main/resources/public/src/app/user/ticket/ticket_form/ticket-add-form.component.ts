import { Component, Output, Input, EventEmitter, OnInit, ViewChild} from '@angular/core';
import {CORE_DIRECTIVES,FORM_DIRECTIVES, FormBuilder, Control, ControlGroup, Validators} from '@angular/common';
import { ITicket, Ticket,TicketState} from '../ticket';
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS} from 'ng2-bootstrap/ng2-bootstrap';
import {ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {User} from './../../../../shared/models/User';
import {CurrentUserService} from "./../../../../shared/services/current.user.service";
import { TicketService } from './../ticket.service';
import { TicketFilter } from './../ticket.filter';
import {TranslatePipe} from "ng2-translate/ng2-translate";
import {FileSelectDirective, FileDropDirective} from "ng2-file-upload";
import {AttachmentService} from "../../../admin/components/attachment/attachment.service";
import {Attachment} from "../../../admin/components/attachment/attachment.interface";
import {HeaderComponent} from "../../../header/header.component";
import {CapitalizeFirstLetterPipe} from "../../../../shared/pipes/capitalize-first-letter";
import {FileUploadComponent} from "../../../admin/components/attachment/modals/file-upload-modal";
import {VoteComponent} from "../../../home/voting/vote.component";
@Component({
    selector: 'ticket-add-form',
    templateUrl: './src/app/user/ticket/ticket_form/ticket-add-form.html',
    providers: [TicketService],
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, FORM_DIRECTIVES,
            FileSelectDirective, FileDropDirective, FileUploadComponent, VoteComponent],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TicketFilter, TranslatePipe,CapitalizeFirstLetterPipe],
    styleUrls: ['src/app/user/ticket/ticket.css']
})

export class TicketAddFormComponent implements OnInit {
    @Output() created:EventEmitter<Ticket>;
    @Input() ticket:ITicket;
    @ViewChild('addModal')
    addModal:ModalDirective;
    private currentUser:User;
    private userAssignArr:User[] = [];
    private creatingForm:ControlGroup;
    private nameInput:Control;
    private descriptionInput:Control;
    private assignInput:Control;
    private submitAttempt:boolean;
    private nameTicket:string = '';
    private descriptionTicket:string = '';
    private assignTicket:string = '';
    private attachments:Attachment[] = [];
    private endTimeStr: string;
    _currentUserService = null;

    constructor(private ticketService:TicketService,
                private builder:FormBuilder) {
                     this._currentUserService = HeaderComponent.currentUserService;
        this.currentUser = this._currentUserService.getUser();  
        this.created = new EventEmitter<Ticket>();
        this.ticket = new Ticket("","",TicketState.NEW);
        this.submitAttempt = false;
        this.nameInput = new Control('', Validators.required);
        this.descriptionInput = new Control('', Validators.required);
        this.assignInput = new Control('', Validators.required);

        this.creatingForm = builder.group({
            nameInput: this.nameInput,
            descriptionInput: this.descriptionInput,
            assignInput: this.assignInput
        });

    }

    ngOnInit() {
    }

    openAddModal() {
        this.addModal.show();
        this.getAllUsers();
        
    }

    deleteAttachmet(attachment:Attachment){
    let index = this.attachments.indexOf(attachment);
        if (index > -1) {
            this.attachments.splice(index,1);
        }
        
 }

    isEmptyName():boolean {
        return this.nameTicket.length >=10 ? false : true;
    }

    isEmptyDescription():boolean {
        return this.descriptionTicket.length >= 20 ? false : true;
    }

    isFindAssign():boolean {
        return this.getAssignedId(this.assignTicket) == null ? false : true;
    }

    getAllUsers() {
        return this.ticketService.getAllUsers(this.currentUser.osbbId)
            .then(userAssignArr => this.userAssignArr = userAssignArr);
    }

    toggleSubmitAttempt() {
        this.submitAttempt = true;
    }

    closeAddModal() {
        this.submitAttempt = false;
        this.clearAddModal();
        this.addModal.hide();
    }

    clearAddModal() {
        this.attachments = [];
        this.nameTicket = '';
        this.descriptionTicket = '';
        this.assignTicket = '';
        this.endTimeStr = '';

    }

    onCreateTicket() {
         if (this.nameInput.valid && this.descriptionInput.valid && this.assignInput.valid&&
             !this.isEmptyDescription()&&!this.isEmptyName()&&this.isFindAssign() && this.isDeadLineCorrect()) {
            this.created.emit(this.createTicket());
            this.closeAddModal();
        }
    }

    createTicket():Ticket {
        let ticket = new Ticket(this.nameTicket, this.descriptionTicket, TicketState.NEW);
        ticket.user = this.currentUser;
        ticket.attachments = this.attachments;
        ticket.assigned = this.getAssignedId(this.assignTicket);
        ticket.deadline = this.castDeadLineStringToDate();
        return ticket;

    }

    findUsers(name:string) {
        if (name.trim() != '') {
            this.ticketService.findAssignUser(name)
                .then(userAssignArr => this.userAssignArr = userAssignArr)
        }
    }

    getAssignedId(assign:string):User {
        let str = assign.split(' ');
        for (let i = 0; i < this.userAssignArr.length; i++) {
            if (str[0] == this.userAssignArr[i].firstName && str[1] == this.userAssignArr[i].lastName) {
                return this.userAssignArr[i];
            }
        }
    }
    public onUpload(attachments:Attachment[]) {
        if (this.addModal.isShown) {
            for(let i=0;i<attachments.length;i++){
                this.attachments.unshift(attachments[i]);
        }       
     }   
  }
           

    public removeAttachment(attachments) {
        if (this.addModal.isShown) {
            let index = this.ticket.attachments.indexOf(attachments);
            this.ticket.attachments.splice(index, 1);
        }
    }

    castDeadLineStringToDate(): Date {
        return moment(this.endTimeStr).toDate();
    }

    isDeadLineCorrect(): boolean {
        let startTime = new Date();
        let res = this.castDeadLineStringToDate().valueOf() - startTime.valueOf();
        return res > 0;
    }
}
