import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { CORE_DIRECTIVES } from "@angular/common";
import { Event } from "./event.interface";
import { EventService } from "./event.service";
import { PageCreator } from "../../shared/services/page.creator.interface";
import "rxjs/Rx";
import { MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective } from "ng2-bootstrap/ng2-bootstrap";
import { TranslatePipe } from "ng2-translate/ng2-translate";
import { CapitalizeFirstLetterPipe } from "../../shared/pipes/capitalize-first-letter";
import { SelectItem } from "../../shared/models/ng2-select-item.interface";
import { PeriodicityItems } from "../../shared/models/periodicity.const";
import { HeaderComponent } from "../header/header.component";
import { DROPDOWN_DIRECTIVES } from "ng2-bs-dropdown/dropdown";
import { SELECT_DIRECTIVES } from "ng2-select";
import { ActiveFilter } from "../../shared/pipes/active.filter";
import { User } from "../../shared/models/User";
import { CurrentUserService } from "../../shared/services/current.user.service";
import { Router } from "@angular/router";
import moment from 'moment';
import { DateTime } from "ng2-datetime-picker/dist/datetime";
import { FileSelectDirective, FileDropDirective } from "ng2-file-upload";
import { FileUploadComponent } from "../admin/components/attachment/modals/file-upload-modal";
import { Attachment } from "../admin/components/attachment/attachment.interface";
import { AttachmentService } from "../admin/components/attachment/attachment.service";

@Component({
    selector: 'my-event',
    templateUrl: 'src/app/event/event.html',
    providers: [EventService, AttachmentService],
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, DROPDOWN_DIRECTIVES, SELECT_DIRECTIVES,
        FileSelectDirective, FileDropDirective, FileUploadComponent],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe, ActiveFilter],
    styleUrls: ['src/shared/css/loader.css', 'src/shared/css/general.css'],
    inputs: ['admin']
})
export class EventComponent implements OnInit, OnDestroy {

    @ViewChild('delModal') public delModal:ModalDirective;
    @ViewChild('delAllModal') public delAllModal:ModalDirective;
    @ViewChild('createModal') public createModal:ModalDirective;
    @ViewChild('editModal') public editModal:ModalDirective;

    private active:boolean = true;
    private pageList:Array<number> = [];
    private pageNumber:number = 1;
    private pageCreator:PageCreator<Event>;
    private totalPages:number;
    private order:boolean = true;
    private events:Event[] = [];
    private selectedEvent:Event = new Event;
    private newEvent:Event = new Event;
    private currentUser:User;
    private showAllEvents: boolean = true;
    private admin: boolean;
    private id:number;
    private repeat: SelectItem[] = [];
    private pending: boolean = false;

    constructor(private _eventService:EventService,
                private _attachmentService: AttachmentService,
                private currentUserService:CurrentUserService,
                private _router: Router) {
        this.currentUser = currentUserService.currentUser;
    }

    ngOnInit():any {
        for (let i = 0; i < PeriodicityItems.length; i++) {
            this.repeat.push(PeriodicityItems[i]);
        }
        this.getRepeatTranslation();
        this.newEvent.attachments = [];
        this.getEventsByPageNum(this.pageNumber);
    }

    getEventsByPageNum(pageNumber:number) {
        this.showAllEvents = true;
        this.pageNumber = +pageNumber;
        this.emptyArray();
        this.pending = true;
        return this._eventService.getAllEvents(this.pageNumber)
            .subscribe((data) => {
                    this.pageCreator = data;
                    this.pending = false;
                    this.events = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                },
                (error) => {
                    console.error(error)
                });
    };

    prevPage() {
        this.pageNumber = this.pageNumber - 1;
        this.getEventsByPageNum(this.pageNumber)
    }

    nextPage() {
        this.pageNumber = this.pageNumber + 1;
        this.getEventsByPageNum(this.pageNumber)
    }

    preparePageList(start:number, end:number) {
        for (let i = start; i <= end; i++) {
            this.pageList.push(i);
        }
    }

    sortBy(title:string) {
        console.log('sorted by ', title);
        this.order = !this.order;
        console.log('order by asc', this.order);
        this.emptyArray();
        this._eventService.getAllEventsSorted(this.pageNumber, title, this.order)
            .subscribe((data) => {
                    this.pageCreator = data;
                    this.events = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                },
                (error) => {
                    console.error(error)
                });
    }

    onSearch(search:string){
        console.log("inside search: search param" + search);
        this._eventService.findEventsByNameOrAuthorOrDescription(search)
            .subscribe((events) => {
                console.log("data: " + events);
                this.events = events;
            });
    }

    filterByStatus(status:string){
        console.log("inside search: status is " + status);
        this._eventService.findEventsByStatus(status)
            .subscribe((events) => {
                console.log("data: " + events);
                this.events = events;
            });
    }

    filterByAuthor() {
        console.log("inside author filtering");
        this.showAllEvents = false;
        this._eventService.findEventsByAuthor()
            .subscribe((events) => {
                this.events = events;
            });
    }

    openCreateModal() {
        this.createModal.show();
    }

    onCreateEventSubmit() {
        this.active = false;
        console.log('creating event');
        this.newEvent.author = this.currentUser;
        this.newEvent.end = this.newEvent.end
            ? this.newEvent.end
            : <Date>moment(this.newEvent.end).hours(12).minute(0);
        this.newEvent.repeat = this.newEvent.repeat
            ? this.newEvent.repeat
            : this.newEvent.repeat = "ONE_TIME";
        this._eventService.addEvent(this.newEvent).then(() => this.getEventsByPageNum(this.pageNumber));
        this.createModal.hide();
        setTimeout(() => this.active = true, 0);
        this.newEvent = new Event();
    }

    closeCreateModal() {
        console.log('closing create modal');
        this.createModal.hide();
    }

    openEditModal(event:Event) {
        this.selectedEvent = event;
        console.log(this.selectedEvent.start + "\n" + this.selectedEvent.end);
        this.selectedEvent.start = <Date>moment(this.selectedEvent.start).format("YYYY-MM-DDTHH:mm:ss");
        this.selectedEvent.end = <Date>moment(this.selectedEvent.end).format("YYYY-MM-DDTHH:mm:ss");
        console.log(this.selectedEvent.start + "\n" + this.selectedEvent.end);
        console.log('selected event: ' + this.selectedEvent);
        this.editModal.show();
    }

    onEditEventSubmit() {
        this.active = false;
        console.log('saving event: ' + this.selectedEvent);
        this._eventService.editAndSave(this.selectedEvent).then(() => this.getEventsByPageNum(this.pageNumber));
        this.editModal.hide();
        setTimeout(() => this.active = true, 0);
    }

    closeEditModal() {
        console.log('closing edit modal');
        this.editModal.hide();
    }

    openDelModal(id:number) {
        this.id = id;
        console.log('show', this.id);
        this.delModal.show();
    }

    closeDelModal() {
        console.log('delete', this.id);
        this._eventService.deleteEventById(this.id).then(() => this.getEventsByPageNum(this.pageNumber));
        this.delModal.hide();
    }

    openDelAllModal() {
        this.delAllModal.show();
    }

    closeDelAllModal() {
        console.log('delete all');
        this._eventService.deleteAllEvents().then(() => this.getEventsByPageNum(this.pageNumber));
        this.delAllModal.hide();
    }

    public onUpload(attachments:Attachment[]) {
        if (this.createModal.isShown) {
            this.newEvent.attachments = this.newEvent.attachments.concat(attachments);
        }
        if (this.editModal.isShown) {
            this.selectedEvent.attachments = this.selectedEvent.attachments.concat(attachments);
        }
    }

    public removeAttachment(attachment) {
        if (this.createModal.isShown) {
            let index = this.newEvent.attachments.indexOf(attachment);
            this.newEvent.attachments.splice(index, 1);
        }
        if (this.editModal.isShown) {
            let index = this.selectedEvent.attachments.indexOf(attachment);
            this.selectedEvent.attachments.splice(index, 1);
        }
    }

    public getPreview(attachment: Attachment) {
        return this._attachmentService.getPreview(attachment);
    }

    public onSelectRepeat(value:SelectItem):void {
        this.newEvent.repeat = this.backToConst(value);
        this.selectedEvent.repeat = this.backToConst(value);
    }

    ngOnDestroy():any {
    }

    onNavigate(id: number) {
        console.log('navigating to event with id ', id);
        if (this.admin) {
            this._router.navigate(['admin/event', id]);
            return;
        }
        this._router.navigate(['home/event', id]);
    }

    toUser(id:number) {
        console.log("toUser" + id + " " + this.currentUser.userId);
        if (id == this.currentUser.userId) {
            this._router.navigate(['home/user/main']);
        } else if (this.admin) {
            this._router.navigate(['admin/friend/', id])
        } else {
            this._router.navigate(['home/friend', id]);
        }
    }

    emptyArray() {
        while (this.pageList.length) {
            this.pageList.pop();
        }
    }

    getRepeatTranslation() {
        console.log("got lang ", HeaderComponent.translateService.currentLang);
        for (let i = 0; i < this.repeat.length; i++){
            HeaderComponent.translateService.get(this.repeat[i].text)
                .subscribe((data : string) => {
                    this.repeat[i].text = data;
                })
        }
    }

    backToConst(item: SelectItem): string{
        var items : SelectItem[] =
            [{id: 1, text: 'ONE_TIME'},
             {id: 2, text: 'PERMANENT_DAYLY'},
             {id: 3, text: 'PERMANENT_WEEKLY'},
             {id: 4, text: 'PERMANENT_MONTHLY'},
             {id: 5, text: 'PERMANENT_YEARLY'}];
        for (let i=0; i<items.length; i++){
            if (item.id === items[i].id) {
                return items[i].text;
            }
        }
    }

    isDateValid(start, end):boolean {
        if (end == undefined) return true;
        return moment(end).isAfter(moment(start));
    }

    formatDate(date: DateTime) {
        return moment(date).format("DD.MM.YYYY hh:mm A");
    }

}
