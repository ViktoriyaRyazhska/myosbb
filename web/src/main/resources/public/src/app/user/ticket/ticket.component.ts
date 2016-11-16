import {RouterConfig,RouterOutlet} from "@angular/router";
import {Component, OnInit} from '@angular/core';
import {CORE_DIRECTIVES} from '@angular/common';
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS} from 'ng2-bootstrap/ng2-bootstrap';
import {ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';
import {PageCreator} from "../../../shared/services/page.creator.interface";
import {Ticket, ITicket,TicketState} from './ticket';
import { TicketService } from './ticket.service';
import { TicketAddFormComponent } from './ticket_form/ticket-add-form.component';
import { TicketEditFormComponent } from './ticket_form/ticket-edit-form.component';
import { TicketDelFormComponent } from './ticket_form/ticket-del-form.component';
import {Router} from '@angular/router';
import {TranslatePipe} from "ng2-translate";
import {CapitalizeFirstLetterPipe} from "../../../shared/pipes/capitalize-first-letter";
import {RouteConfig, ROUTER_DIRECTIVES } from '@angular/router-deprecated';
import {User} from "../../../shared/models/User";
import {CurrentUserService} from "./../../../shared/services/current.user.service";
import {Notice} from './../../header/notice';
import {NoticeService} from './../../header/header.notice.service';
import {PageRequest} from '../../../shared/models/page.request';
import {HeaderComponent} from "../../header/header.component";
import {ToasterContainerComponent, ToasterService} from "angular2-toaster/angular2-toaster";
import {onErrorResourceNotFoundToastMsg, onErrorServerNoResponseToastMsg} from "../../../shared/error/error.handler.component";
import {FileSelectDirective, FileDropDirective} from "ng2-file-upload";
import {FileUploadComponent} from "../../admin/components/attachment/modals/file-upload-modal";
import {Attachment} from "../../admin/components/attachment/attachment.interface";
import {VoteComponent} from "../../home/voting/vote.component";
@Component({
    selector: 'ticket',
    templateUrl: './src/app/user/ticket/ticket.component.html',
    providers: [TicketService, ToasterService],
    directives: [RouterOutlet, ROUTER_DIRECTIVES, MODAL_DIRECTIVES, CORE_DIRECTIVES,
        TicketAddFormComponent, TicketEditFormComponent, TicketDelFormComponent,
        FileSelectDirective, FileDropDirective, FileUploadComponent,VoteComponent],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    styleUrls: ['src/app/user/ticket/ticket.css', 'src/shared/css/loader.css', 'src/shared/css/general.css']
})

export class TicketComponent implements OnInit {

    private ticketArr:ITicket[] = [];
    private updatedTicket:ITicket;
    private currentUser:User;
    private dates:string[] = [];
    private pageCreator:PageCreator<Ticket>;
    private pageNumber:number = 1;
    private pageList:Array<number> = [];
    private totalPages:number;
    private pending = false;
    private selectedRow:number = 10;
    private order:boolean = false;
    private nameSort:string = "time";
    private status:string = "";
    private email:string = "";
    private emailAssign:string = "";
    private pageRequest:PageRequest;

    constructor(private ticketService:TicketService,
                private _toasterService:ToasterService,
                private _currentUserService:CurrentUserService,
                private router:Router) {
        this._currentUserService = HeaderComponent.currentUserService;
        this.currentUser = this._currentUserService.currentUser;
        console.log("name"+this.currentUser.role);

    }

    ngOnInit() {
        this.getTicketsByPageNum(this.pageNumber, this.selectedRow);
    }

    initUpdatedTicket(ticket:ITicket):void {
        this.updatedTicket = ticket;
    }

    createTicket(ticket:ITicket):void {
        this.ticketService.addTicket(ticket)
            .then(ticket => this.addTicket(ticket));
    }


    private addTicket(ticket:ITicket):void {
        this.ticketArr.unshift(ticket);
    }

    editTicket(ticket:ITicket):void {
        this.ticketService.editTicket(ticket);
        let index = this.ticketArr.indexOf(this.updatedTicket);
        if (index > -1) {
            this.ticketArr[index] = ticket;
        }
    }

    setDiscussed( ticket: ITicket):void {
        ticket.discussed = new Date();
    }

    deleteTicket(ticket:ITicket):void {
        this.ticketService.deleteTicket(ticket).then(ticket => this.deleteTicketFromArr(ticket));
    }


    private deleteTicketFromArr(ticket:ITicket):void {
        let index = this.ticketArr.indexOf(ticket);
        if (index > -1) {
            this.ticketArr.splice(index, 1);
        }
    }

    getTicketsByPageNum(pageNumber:number, selectedRow:number) {
        console.log("getTicketsByPageNum " + this.pageNumber);
        this.pageNumber = +pageNumber;
        this.pending = true;
        this.selectedRow = +selectedRow;
        this.email = '';
        this.emailAssign = '';
        this.status = '';
        this.pageRequest = new PageRequest(this.pageNumber, this.selectedRow, this.nameSort, this.order);
        return this.ticketService.getTicketsByPage(this.pageRequest)
            .subscribe((data) => {
                    this.pending = false;
                    this.pageCreator = data;
                    this.ticketArr = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                    this.dates = data.dates;
                },
                (error) => {
                    this.handleErrors(error);
                    this.pending = false;
                    console.error(error)
                });
    }

    findTicketByName(name:string) {
        console.log("findTicketByName");
        this.pending = true;
        this.pageRequest = new PageRequest(this.pageNumber, this.selectedRow, this.nameSort, this.order);
        return this.ticketService.findByNameDescription(this.pageRequest, this.currentUser.osbbId, name)
            .subscribe((data) => {
                    this.pending = false;
                    this.pageCreator = data;
                    this.ticketArr = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                    this.dates = data.dates;
                },
                (error) => {
                    this.pending = false;
                    console.error(error)
                });
    }

    findMyTickets() {
        console.log("findMyTickets");
        this.pending = true;
        this.emailAssign = '';
        this.email = this.currentUser.email;
        this.pageRequest = new PageRequest(this.pageNumber, this.selectedRow, this.nameSort, this.order);
        return this.ticketService.findByUser(this.pageRequest, this.email, this.status)
            .subscribe((data) => {
                    this.pending = false;
                    this.pageCreator = data;
                    this.ticketArr = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                    this.dates = data.dates;
                },
                (error) => {
                    this.pending = false;
                    console.error(error)
                });

    }

    findMyAssigned() {
        console.log("findMyAssigned");
        this.pending = true;
        this.email = '';
        this.emailAssign = this.currentUser.email;
        this.pageRequest = new PageRequest(this.pageNumber, this.selectedRow, this.nameSort, this.order);
        return this.ticketService.findByAssigned(this.pageRequest, this.emailAssign, this.status)
            .subscribe((data) => {
                    this.pending = false;
                    this.pageCreator = data;
                    this.ticketArr = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                    this.dates = data.dates;
                },
                (error) => {
                    this.pending = false;
                    console.error(error)
                });
    }

    findTicketByState(state:string) {
        console.log("findTicketByState");
        this.pending = true;
        this.status = state;
        if (this.email != "") {
            this.findMyTickets();
        }
        else if (this.emailAssign != "") {
            this.findMyAssigned();
        }
        else {
            this.pageRequest = new PageRequest(this.pageNumber, this.selectedRow, this.nameSort, this.order);
            return this.ticketService.findByState(this.pageRequest, state)
                .subscribe((data) => {
                        this.pending = false;
                        this.pageCreator = data;
                        this.ticketArr = data.rows;
                        this.preparePageList(+this.pageCreator.beginPage,
                            +this.pageCreator.endPage);
                        this.totalPages = +data.totalPages;
                        this.dates = data.dates;
                    },
                    (error) => {
                        this.pending = false;
                        console.error(error)
                    });
        }
    }

    singleTicket(id:number) {
        this.router.navigate([this.router.url, id]);
    }


    getTime(time:Date):string {
        return new Date(time).toLocaleString();
    }

    getDiscussed(discussed:Date):string {
        return new Date(discussed).toLocaleString();
    }

    isCreator():boolean {

        if (this.currentUser.role == 'ROLE_ADMIN' || this.currentUser.role == 'ROLE_MANAGER') {
            return true;
        }
        return false;

    }

    selectRowNum(row:number) {
        console.log("selectRowNum");

        if (this.status != "") {
            this.findTicketByState(this.status);
        }
        else if (this.email != "") {
            this.findMyTickets();
        }
        else {
            this.getTicketsByPageNum(this.pageNumber, row);
        }

    }

    prevPage() {
        console.log("prevPage");

        this.pageNumber = this.pageNumber - 1;
        if (this.status != "") {
            this.findTicketByState(this.status);
        } else if (this.email != "") {
            this.findMyTickets();
        } else if (this.emailAssign != "") {
            this.findMyAssigned();
        } else {
            this.getTicketsByPageNum(this.pageNumber, this.selectedRow);

        }
    }

    nextPage() {
        console.log("nextPage");
        this.pageNumber = this.pageNumber + 1;
        this.getTicketsByPageNum(this.pageNumber, this.selectedRow);

    }

    initPageNum(pageNumber:number, selectedRow:number) {
        console.log("initPageNum");

        this.pageNumber = +pageNumber;
        this.selectedRow = +selectedRow;

        if (this.status != "") {
            this.findTicketByState(this.status);
        } else if (this.email != "") {
            this.findMyTickets();
        } else if (this.emailAssign != "") {
            this.findMyAssigned();
        } else {
            this.getTicketsByPageNum(pageNumber, selectedRow);
        }
    }

    emptyArray() {
        while (this.pageList.length) {
            this.pageList.pop();
        }
    }

    preparePageList(start:number, end:number) {
        this.emptyArray();
        for (let i = start; i <= end; i++) {
            this.pageList.push(i);
        }
    }

    sortBy(name:string) {
        console.log("sortBy");
        this.emailAssign = '';
        this.email = '';
        this.status = '';
        if (name != '') {
            this.nameSort = name;
            this.order = !this.order;
        }
        this.pageRequest = new PageRequest(this.pageNumber, this.selectedRow, this.nameSort, this.order);
        return this.ticketService.getTicketsSorted(this.pageRequest)
            .subscribe((data) => {
                    this.pageCreator = data;
                    this.ticketArr = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                },
                (error) => {
                    console.error(error)
                });
    }

    private handleErrors(error:any) {
        if (error.status === 404 || error.status === 400) {
            console.log('server error 400');
            this._toasterService.pop(onErrorResourceNotFoundToastMsg);
            return;
        }

        if (error.status === 500) {
            console.log('server error 500');
            this._toasterService.pop(onErrorServerNoResponseToastMsg);
            return;
        }
    }

}