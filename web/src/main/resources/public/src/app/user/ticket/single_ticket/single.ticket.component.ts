import {RouterConfig} from "@angular/router";
import {Component, OnInit,AfterViewInit,HostListener,ElementRef,ViewChild} from '@angular/core';
import {CORE_DIRECTIVES} from '@angular/common';
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS} from 'ng2-bootstrap/ng2-bootstrap';
import {ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {Observable} from 'rxjs/Observable';
import {NgSwitch} from '@angular/common';
import 'rxjs/Rx';
import { TicketService } from './../ticket.service';
import {ROUTER_DIRECTIVES,RouterOutlet} from '@angular/router';
import {Message, IMessage,} from './message';
import {Ticket, ITicket,TicketState} from './../ticket';
import {TicketComponent} from './../ticket.component';
import { MessageService } from './single.ticket.service';
import { TicketAddFormComponent } from './../ticket_form/ticket-add-form.component';
import { TicketEditFormComponent } from './../ticket_form/ticket-edit-form.component';
import { TicketDelFormComponent } from './../ticket_form/ticket-del-form.component';
import {ActivatedRoute} from "@angular/router";
import {Router} from '@angular/router';
import {Subscription} from "rxjs";
import {CurrentUserService} from "./../../../../shared/services/current.user.service";
import {User} from './../../../../shared/models/User';
import {CapitalizeFirstLetterPipe} from "../../../../shared/pipes/capitalize-first-letter";
import {PageCreator} from "../../../../shared/services/page.creator.interface";
import {TranslatePipe} from "ng2-translate/ng2-translate";
import {HeaderComponent} from "../../../header/header.component";
import {ToasterContainerComponent, ToasterService} from "angular2-toaster/angular2-toaster";
import {
    onErrorResourceNotFoundToastMsg,
    onErrorServerNoResponseToastMsg
} from "../../../../shared/error/error.handler.component";
import {PageRequest} from '../../../../shared/models/page.request';
import {FileUploadComponent} from "../../../admin/components/attachment/modals/file-upload-modal";
import {Attachment} from "../../../admin/components/attachment/attachment.interface";


@Component({
    selector: 'ticket',
    templateUrl: './src/app/user/ticket/single_ticket/single.ticket.component.html',
    providers: [MessageService, TicketService, ToasterService],
    directives: [RouterOutlet, ROUTER_DIRECTIVES, MODAL_DIRECTIVES, ToasterContainerComponent,
        CORE_DIRECTIVES, TicketAddFormComponent, TicketEditFormComponent, TicketDelFormComponent],
    viewProviders: [BS_VIEW_PROVIDERS],
    styleUrls: ['src/app/user/ticket/ticket.css', 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe]
})

export class MessageComponent implements OnInit {
    @ViewChild('gallery') gallery:ModalDirective;

    private message:IMessage;
    private messages:Message[] = [];
    private ticket:Ticket;
    private ticketId:number;
    private sub:Subscription;
    private currentUser:User;
    private ticketState:string = 'new';
    private messText:string = "";
    private pageCreator:PageCreator<Ticket>;
    private pageNumber:number = 1;
    private pageList:Array<number> = [];
    private totalPages:number;
    private pending = false;
    private selectedRow:number = 5;
    private order:boolean = false;
    private answerText = '';
    private dates:string[] = [];
    private nameSort:string = "time";
    private open:boolean = false;
    private pageRequest:PageRequest;
    private currentAttachment:Attachment;
    private index:number;

    constructor(private routeParams:ActivatedRoute,
                private ticketService:TicketService,
                private messageService:MessageService,
                private currentUserService:CurrentUserService,
                private router:Router,
                private toasterService:ToasterService) {
        this.currentUserService = HeaderComponent.currentUserService;
        this.currentUser = this.currentUserService.getUser();
        this.currentUser.role = this.currentUserService.getRole();
        this.message = new Message("");
        this.message.answers = [];
        this.ticket = new Ticket("", "", TicketState.NEW);
        this.ticket.user = new User();
        this.ticket.assigned = new User();
        this.message = new Message("");
        this.currentAttachment = new Attachment();

    }

    ngOnInit():any {
        this.sub = this.routeParams.params.subscribe((params)=> {
            this.ticketId = +params['id'];
            this.messageService.getTicketbyId(this.ticketId)
                .subscribe((data) => {
                        this.ticket = data,
                            this.getComments();
                    },
                    (error)=> {
                        this.handleErrors(error)
                    }
                )
        });

    }

    getComments() {
        this.pending = true;
        this.pageRequest = new PageRequest(this.pageNumber, this.selectedRow, this.nameSort, this.order);
        return this.messageService.getMessagesForTicket(this.pageRequest, this.ticketId)
            .subscribe((data) => {
                    this.pending = false;
                    this.pageCreator = data;
                    for (let i = 0; i < data.rows; i++) {
                        this.messages[i].answers = [];
                    }
                    this.addMessages(data.rows);
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                    this.dates = data.dates;
                    this.pageNumber++;
                },
                (error) => {
                    this.pending = false;
                    console.error(error)
                });
    }

    preparePageList(start:number, end:number) {
        this.emptyArray();
        for (let i = start; i <= end; i++) {
            this.pageList.push(i);
        }
    }

    emptyArray() {
        while (this.pageList.length) {
            this.pageList.pop();
        }
    }

    private handleErrors(error:any) {
        if (error.status === 400 || error.status === 404) {
            console.log('server error 400');
            this.toasterService.pop(onErrorResourceNotFoundToastMsg);
            return;
        }
        if (error.status === 500) {
            console.log('server error 500');
            this.toasterService.pop(onErrorServerNoResponseToastMsg);
            return;
        }
    }

    private toTableTicket() {
        this.router.navigate(['home/ticket']);
    }


    toUser(id:number) {
        // if (id == this.currentUser.userId) {
        //     this.router.navigate(['home/user/main'])
        // }
        // else {
        //     if (this.currentUser.role == "ROLE_ADMIN") {
        //     }
        //     if (this.currentUser.role == "ROLE_USER") {
        //         this.router.navigate(['home/friend', id]);
        //     }
        //     if (this.currentUser.role == "ROLE_MANAGER") {
        //         this.router.navigate(['manager/user', id]);
        //     }
                this.currentUserService.toUser(id);
        
    }

    initEditMessage(message:Message) {
        this.message = message;
        this.messText = this.message.message;
    }

    createMessage(text:string):void {
        if (text.length > 0) {
            if (this.message.messageId == null) {
                console.log("create");
                this.message.message = text;
                this.message.user = this.currentUserService.getUser();
                this.message.idTicket = this.ticketId;
                this.messageService.addMessage(this.message, this.ticketId)
                    .then(message => this.addMessage(message))
                    .then(this.message.messageId = null);
            }
            else {
                console.log("update");
                this.editMessage(text);
            }
        }
    }

    editMessage(text:string) {
        for (let i = 0; i < this.messages.length; i++) {
            if (this.message.message == this.messages[i].message) {
                this.message.idTicket = this.ticketId;
                this.messages[i].message = text;
                this.messageService.editMessage(this.messages[i]);
                this.message = new Message("");
                this.toasterService.pop('success'
                    , "Comment updated!");
            }
        }

    }

    getAnswers(index:number):Message[] {
        return this.messages[index].answers;
    }

    private addMessage(message:IMessage):void {
        this.messages.unshift(message);
        this.message.answers = [];

    }

    private addMessages(message:IMessage[]):void {
        for (let i = 0; i < message.length; i++) {
            this.messages.push(message[i]);
        }
    }

    initEditAnswer(answer:Message, id:number) {
        this.isAnswer(id);
        this.message = answer;
        this.answerText = this.message.message;
        this.open = true;
    }

    isMessageCreator(message:Message):boolean {
        return (message.user.firstName == this.currentUser.firstName && message.user.lastName == this.currentUser.lastName
        || this.currentUser.role == 'ROLE_ADMIN' || this.currentUser.role == 'ROLE_MANAGER');
    }

    deleteMessage(message:Message) {
        this.messageService.deleteMessage(message.messageId).then(this.delMessage(message))
            .then(this.message.messageId = null);
    }

    private delMessage(message:Message) {
        let index = this.messages.indexOf(message);
        if (index > -1) {
            this.messages.splice(index, 1);
        }

    }

    deleteAnswer(i:number, message:Message) {
        this.messageService.deleteMessage(message.messageId).then(this.delAnswer(i, message))

    }

    private delAnswer(i:number, message:Message) {
        let index = this.messages[i].answers.indexOf(message);
        if (index > -1) {
            this.messages[i].answers.splice(index, 1);
        }

    }


    editState(state:string) {
        if (state == 'IN_PROGRESS') {
            this.ticket.state = TicketState.IN_PROGRESS;
            this.ticketState = 'IN_PROGRESS';
        }
        else if (state == 'DONE') {
            this.ticket.state = TicketState.DONE;
            this.ticketState = 'DONE';
        }
        this.messageService.editState(this.ticket)
            .then(this.ngOnInit());

    }


    editTicket(ticket:ITicket):void {
        this.ticketService.editTicket(ticket)
            .then(this.ngOnInit());
    }

    deleteTicket(ticket:ITicket):void {
        this.ticketService.deleteTicket(ticket)
            .then(this.toTableTicket());
    }

    getTime(time:Date):string {
        return new Date(time).toLocaleString();
    }

    getDiscussed(discussed:Date):string {
        return new Date(discussed).toLocaleString();
    }

    initAddAnswer(parentMessage:Message) {
        this.message = new Message("");
        this.message.parentId = parentMessage.messageId;
        this.message.user = this.currentUser;

    }

    createAnswer(text:string) {
        if (this.message.messageId == null) {
            this.message.message = text;
            console.log("create");
            this.message.user = this.currentUserService.getUser();
            this.messageService.addAnswer(this.message)
                .then(message => this.addAnswerToMessage(message));
        }
        else {
            console.log("update");
            this.editAnswer(text);
        }

    }

    editAnswer(text:string) {

        for (let i = 0; i < this.messages.length; i++) {
            if (this.message.parentId == this.messages[i].messageId) {
                for (let j = 0; j < this.messages.length; j++) {
                    if (this.messages[i].answers[j].message == this.message.message) {
                        this.messages[i].answers[j].message = text;
                        this.messageService.editMessage(this.messages[i].answers[j]);
                        this.message = new Message("");
                        this.toasterService.pop('success'
                            , "Answer updated!");
                    }
                }
            }
        }
    }

    toClose() {
        this.open = !this.open;
        if (this.open == false) {
            this.answerText = '';
        }
    }

    addAnswerToMessage(message:Message) {
        for (let i = 0; i < this.messages.length; i++) {
            if (this.messages[i].messageId == message.parentId) {
                this.messages[i].answers.unshift(message);
            }
        }
        this.message.parentId = null;
    }

    isAnswer(id:number):boolean {
        return this.message.parentId == id ? true : false;
    }

    isAssigned():boolean {
        return (this.ticket.assigned.firstName == this.currentUser.firstName && this.ticket.assigned.lastName == this.currentUser.lastName);
    }

    isCreator():boolean {
        return (this.ticket.user.firstName == this.currentUser.firstName && this.ticket.user.lastName == this.currentUser.lastName ||
        this.currentUser.role == 'ROLE_ADMIN' || this.currentUser.role == 'ROLE_MANAGER');
    }


// gallery
    next() {
        if (this.index == this.ticket.attachments.length) {
            this.index = 0;
        }
        this.currentAttachment = this.ticket.attachments[this.index++];
    }

    prev() {
        if (this.index == 0) {
            this.index = this.ticket.attachments.length - 1;
        }

        this.currentAttachment = this.ticket.attachments[this.index--];

    }

    showGallery(attachment:Attachment) {
        this.gallery.show();
        this.index = this.ticket.attachments.indexOf(attachment);
        this.currentAttachment = this.ticket.attachments[this.index];
    }

    closeGallery() {
        this.gallery.hide();
    }
}