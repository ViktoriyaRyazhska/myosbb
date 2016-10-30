import {Component, OnInit, OnDestroy, ViewChild} from "@angular/core";
import {EventService} from "./event.service";
import {Event} from "./event.interface";
import {HeaderComponent} from "../header/header.component";
import {ActivatedRoute, RouterOutlet, RouterLink, ROUTER_DIRECTIVES} from "@angular/router";
import {Subscription} from "rxjs/Subscription";
import {CapitalizeFirstLetterPipe} from "../../shared/pipes/capitalize-first-letter";
import {TranslatePipe} from "ng2-translate";
import {Location, CORE_DIRECTIVES} from '@angular/common';
import moment from 'moment';
import {DateTime} from "ng2-datetime-picker/dist/datetime";
import {User} from "../../shared/models/User";
import {AttachmentService} from "../admin/components/attachment/attachment.service";
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {Attachment} from "../admin/components/attachment/attachment.interface";

@Component({
    selector: 'my-user-event',
    templateUrl: 'src/app/event/event.show.html',
    directives: [HeaderComponent, MODAL_DIRECTIVES, RouterOutlet, RouterLink, ROUTER_DIRECTIVES],
    providers: [EventService, AttachmentService],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    viewProviders: [BS_VIEW_PROVIDERS, CORE_DIRECTIVES]
})
export class EventShowComponent implements OnInit, OnDestroy {

    @ViewChild('previewModal')
    public previewModal:ModalDirective;
    private event: Event;
    private eventId: number;
    private sub: Subscription;
    private attachment: Attachment = <Attachment>{fileName: "", url: ""};

    constructor(private _eventService: EventService, private _routeParams: ActivatedRoute,
                private _location: Location, private _attachmentService: AttachmentService) {
        this.event = new Event();
        this.event.author = new User();
    }

    ngOnInit(): any {
        this.sub = this._routeParams.params.subscribe((params)=> {
            this.eventId = +params['id'];
            this._eventService.getEvent(this.eventId)
                .subscribe((data) => {
                    this.event = data;
                },
                (error) => {
                    console.error(error)
                });
        })
    }

    ngOnDestroy(): any {
        if (this.sub)
            this.sub.unsubscribe();
    }

    backClicked() {
        this._location.back();
    }

    openPreviewModal(attachment: Attachment) {
        this.attachment = attachment;
        this.previewModal.show();
    }

    getPreview(attachment: Attachment) {
        return this._attachmentService.getPreview(attachment);
    }

    formatDate(date: DateTime) {
        return moment(date).format("DD.MM.YYYY hh:mm A");
    }

    getStatus(status: string) {
        switch (status) {
            case "FUTURE": return "future"; break;
            case "IN_PROCESS": return "in_process"; break;
            case "FINISHED": return "finished"; break;
        }
    }
}