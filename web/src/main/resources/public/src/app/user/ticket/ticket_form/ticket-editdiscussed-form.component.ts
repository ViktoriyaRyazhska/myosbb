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
import {CapitalizeFirstLetterPipe} from "../../../../shared/pipes/capitalize-first-letter";
import {HeaderComponent} from "../../../header/header.component";
import {FileSelectDirective, FileDropDirective} from "ng2-file-upload";
import {FileUploadComponent} from "../../../admin/components/attachment/modals/file-upload-modal";
import {Attachment} from "../../../admin/components/attachment/attachment.interface";
@Component({
    selector: 'ticket-editdiscussed-form',
    templateUrl: './src/app/user/ticket/ticket_form/ticket-editdiscussed-form.html',
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, FORM_DIRECTIVES,
        FileSelectDirective, FileDropDirective, FileUploadComponent],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TicketFilter, TranslatePipe,CapitalizeFirstLetterPipe],
    styleUrls: ['src/app/user/ticket/ticket.css']
})
export class TicketEditDiscussedFormComponent implements OnInit {
    @Output() update:EventEmitter<Ticket>;
    @Input() ticket:ITicket;
    @ViewChild('editModal')
    editModal:ModalDirective;
    states:string[];

    private stateTicket = "NEW";
    private ticketId:number;
    private creatingForm:ControlGroup;
    private submitAttempt:boolean = true;
    private currentUser:User;
    private discussed:Date;
    private deadline:Date;
    private _currentUserService = null;
    DateError: boolean = false;

    constructor(private ticketService:TicketService,
                private builder:FormBuilder) {
        this._currentUserService = HeaderComponent.currentUserService;
        this.currentUser = this._currentUserService.getUser();
        this.update = new EventEmitter<Ticket>();
        this.states = ["NEW", "IN_PROGRESS", "DONE"];
        this.creatingForm = builder.group({
        });


    }

    openEditModal() {
        this.editModal.show();     
    }

    ngOnInit() {
    }

    CheckDate() {
        let date = new Date();
        let res = this.discussed.valueOf() - date.valueOf();
        let res2 = this.ticket.deadline.valueOf() - this.discussed.valueOf();
        if ((res <= 0)) {
            this.DateError = true;
        }
        else this.DateError = false;
        this.DateError=true;
    }
    toggleSubmitAttempt() {
        this.submitAttempt = true;
    }
    
     closeEditModal() {
        this.submitAttempt = false;
        this.clearEditModal();
        this.editModal.hide();
    }

    clearEditModal() {
        this.discussed = null;
        this.deadline = null;

    }

    onEditTicket() {
            let ticket = this.editTicket();
            this.update.emit(ticket);
            this.closeEditModal();
        
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
        console.log("is ticket"+ticket.ticketId);
        this.ticket = ticket;
        this.openEditModal();
        
    }
    
    editTicket():Ticket {
        console.log("edit ticket");
        let ticket =this.ticket;
        ticket.ticketId = this.ticket.ticketId;
        ticket.discussed = this.discussed;
        ticket.deadline = this.ticket.deadline;
        ticket.state = this.editState('IN_PROGRESS');
        console.log("edit state  "+ this.stateTicket);
        return ticket;
    }

}