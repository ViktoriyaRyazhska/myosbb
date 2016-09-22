import {Component, OnInit, OnDestroy, ViewChild} from "@angular/core";
import {EventService} from "../../../event/event.service";
import {Event} from "../../../event/event.interface";
import {HeaderComponent} from "../../../header/header.component";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs/Subscription";
import {CapitalizeFirstLetterPipe} from "../../../../shared/pipes/capitalize-first-letter";
import {TranslatePipe} from "ng2-translate";
import {Location, CORE_DIRECTIVES} from '@angular/common';
import moment from 'moment';
import {DateTime} from "ng2-datetime-picker/dist/datetime";
import {User} from "../../../../shared/models/User";
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {Attachment} from "../attachment/attachment.interface";
import {AttachmentService} from "../attachment/attachment.service";
import {Attachment} from "../attachment/attachment.interface";

@Component({
    selector: 'my-user-event',
    templateUrl: 'src/app/admin/components/event/event.show.html',
    directives: [HeaderComponent, MODAL_DIRECTIVES],
    providers: [EventService, AttachmentService],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    viewProviders: [BS_VIEW_PROVIDERS, CORE_DIRECTIVES],
    styleUrls: ['src/app/event/event.css']
})
export class EventShowAdminComponent implements OnInit, OnDestroy {

    private event: Event;
    private eventId: number;
    private sub: Subscription;
    @ViewChild('previewModal')
    public previewModal:ModalDirective;
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

    formatDate(date: DateTime) {
        return moment(date).format("DD.MM.YYYY hh:mm A");
    }

    getPreview(attachment: Attachment) {
        return this._attachmentService.getPreview(attachment);
    }
}