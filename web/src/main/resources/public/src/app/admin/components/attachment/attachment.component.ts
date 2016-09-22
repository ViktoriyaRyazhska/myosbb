import {Component, OnInit, OnDestroy, ViewChild} from "@angular/core";
import {CORE_DIRECTIVES} from "@angular/common";
import {Attachment} from "./attachment.interface";
import {AttachmentService} from "./attachment.service";
import {PageCreator} from "../../../../shared/services/page.creator.interface";
import "rxjs/Rx";
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {FileSelectDirective, FileDropDirective} from "ng2-file-upload/ng2-file-upload";
import {TranslatePipe} from "ng2-translate/ng2-translate";
import {CapitalizeFirstLetterPipe} from "../../../../shared/pipes/capitalize-first-letter";
import ApiService = require("../../../shared/services/api.service");
import FileLocationPath = require("../../../shared/services/file.location.path");
import {FileUploadComponent} from "./modals/file-upload-modal";

declare var saveAs:any;

@Component({
    selector: 'my-attachment',
    templateUrl: 'src/app/admin/components/attachment/attachment.html',
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    providers: [AttachmentService],
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, FileSelectDirective, FileDropDirective, FileUploadComponent],
    viewProviders: [BS_VIEW_PROVIDERS],
    styleUrls: ['src/app/admin/components/attachment/attachment.css']
})
export class AttachmentAdminComponent implements OnInit, OnDestroy {

    private attachments:Attachment[];
    private pageCreator:PageCreator<Attachment>;
    private pageNumber:number = 1;
    private pageList:Array<number> = [];
    private totalPages:number;
    @ViewChild('delModal') public delModal:ModalDirective;
    @ViewChild('delAllModal') public delAllModal:ModalDirective;
    order:boolean = true;
    private pending:boolean = false;
    private attachmentId:number;

    constructor(private _attachmentService:AttachmentService) {
    }

    public onUploadAttachment(attachments: Attachment[]) {
        attachments.forEach(a => this.attachments.push(a));
    }

    getPreview(attachment: Attachment): string {
        switch (attachment.type) {
            case "DATA": return "assets/img/attachment_type/data.png"; break;
            case "TEXT": return "assets/img/attachment_type/text.png"; break;
            case "AUDIO": return "assets/img/attachment_type/audio.png"; break;
            case "VIDEO": return "assets/img/attachment_type/video.png"; break;
            default: return attachment.url;
        }
    }

    openDelModal(id:number) {
        this.attachmentId = id;
        console.log('show', this.attachmentId);
        this.delModal.show();
    }

    closeDelModal() {
        console.log('delete', this.attachmentId);
        this._attachmentService.deleteAttachmentById(this.attachmentId)
            .then(() => this.getAttachmentsByPageNum(this.pageNumber));
        this.delModal.hide();
    }

    openDelAllModal() {
        this.delAllModal.show();
    }

    closeDelAllModal() {
        console.log('delete all');
        this._attachmentService.deleteAllAttachments()
            .then(() => this.getAttachmentsByPageNum(this.pageNumber));
        this.delAllModal.hide();
    }

    ngOnInit():any {
        this.getAttachmentsByPageNum(this.pageNumber);
    }

    getAttachmentsByPageNum(pageNumber:number,) {
        this.pageNumber = +pageNumber;
        this.emptyArray();
        return this._attachmentService.getAllAttachments(this.pageNumber)
            .subscribe((data) => {
                    this.pageCreator = data;
                    this.attachments = data.rows;
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
        this.getAttachmentsByPageNum(this.pageNumber)
    }

    nextPage() {
        this.pageNumber = this.pageNumber + 1;
        this.getAttachmentsByPageNum(this.pageNumber)
    }

    emptyArray() {
        while (this.pageList.length) {
            this.pageList.pop();
        }
    }

    preparePageList(start:number, end:number) {
        for (let i = start; i <= end; i++) {
            this.pageList.push(i);
        }
    }

    sortBy(name:string) {
        console.log('sorted by ', name);
        this.order = !this.order;
        console.log('order by asc', this.order);
        this.emptyArray();
        this._attachmentService.getAllAttachmentsSorted(this.pageNumber, name, this.order)
            .subscribe((data) => {
                    this.pageCreator = data;
                    this.attachments = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                },
                (error) => {
                    console.error(error)
                });
    }

    ngOnDestroy():any {
        //this.subscriber.unsubscribe();
    }

    onSearch(search:string){
        console.log("inside search: search param" + search);
        this._attachmentService.findAttachmentByPath(search)
            .subscribe((attachments) => {
                console.log("data: " + attachments);
                this.attachments = attachments;
            });
    }
}