import {Component, OnInit, OnDestroy, ViewChild} from "@angular/core";
import {CORE_DIRECTIVES} from "@angular/common";
import {Attachment} from "./attachment.interface";
import {AttachmentService} from "./attachment.service";
import {PageCreator} from "../../../shared/services/page.creator.interface";
import "rxjs/Rx";
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {FileSelectDirective, FileDropDirective, FileUploader} from "ng2-file-upload/ng2-file-upload";
import {TranslatePipe} from "ng2-translate/ng2-translate";
import {CapitalizeFirstLetterPipe} from "../../../shared/pipes/capitalize-first-letter";
import ApiService = require("../../../shared/services/api.service");
import FileLocationPath = require("../../../shared/services/file.location.path");

const attachmentUploadUrl = ApiService.serverUrl + '/restful/attachment/';
const fileUploadPath = FileLocationPath.fileUploadPath;
const fileDownloadPath = FileLocationPath.fileDownloadPath;
declare var saveAs:any;

@Component({
    selector: 'my-attachment',
    templateUrl: 'src/app/user/attachment/attachment.html',
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    providers: [AttachmentService],
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, FileSelectDirective, FileDropDirective],
    viewProviders: [BS_VIEW_PROVIDERS]
})
export class UserAttachmentComponent implements OnInit, OnDestroy {

    public uploader:FileUploader = new FileUploader({url: attachmentUploadUrl, authToken: 'Bearer '
                                                    + localStorage.getItem('access_token')});
    public hasDropZoneOver:boolean = false;
    public fileOverBase(e:any):void {
        this.hasDropZoneOver = e;
    }

    private attachments:Attachment[];
    private pageCreator:PageCreator<Attachment>;
    private pageNumber:number = 1;
    private pageList:Array<number> = [];
    private totalPages:number;
    @ViewChild('delModal') public delModal:ModalDirective;
    @ViewChild('delAllModal') public delAllModal:ModalDirective;
    @ViewChild('uploadModal') public uploadModal:ModalDirective;
    order:boolean = true;
    private pending:boolean = false;
    private attachmentId:number;

    constructor(private _attachmentService:AttachmentService) {
    }

    transform(bytes) {
        if(bytes == 0) return '0 Bytes';
        var k = 1000;
        var sizes = ['Bytes', 'KB', 'MB', 'GB'];
        var i = Math.floor(Math.log(bytes) / Math.log(k));
        return (bytes / Math.pow(k, i)).toFixed(1) + ' ' + sizes[i];
    }

    openUploadModal() {
        this.uploadModal.show();
    }

    closeUploadModal() {
        console.log('closing upload modal');
        this._attachmentService.getAllAttachments(this.pageNumber);
        this.getAttachmentsByPageNum(this.pageNumber);
        console.log('closing upload modal');
        this.uploadModal.hide();
    }

    openDelModal(id:number) {
        this.attachmentId = id;
        console.log('show', this.attachmentId);
        this.delModal.show();
    }

    closeDelModal() {
        console.log('delete', this.attachmentId);
        this._attachmentService.deleteAttachmentById(this.attachmentId);
        this._attachmentService.getAllAttachments(this.pageNumber);
        this.getAttachmentsByPageNum(this.pageNumber);
        this.delModal.hide();
    }

    openDelAllModal() {
        this.delAllModal.show();
    }

    closeDelAllModal() {
        console.log('delete all');
        this._attachmentService.deleteAllAttachments();
        this._attachmentService.getAllAttachments(this.pageNumber);
        this.getAttachmentsByPageNum(this.pageNumber);
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
                    for (let i = 0; i < this.attachments.length; i++){
                        this.attachments[i].url = this.attachments[i].path.replace(String(fileUploadPath),String(fileDownloadPath));
                    }
                    console.log(fileUploadPath,fileDownloadPath);
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
                    for (let i = 0; i < this.attachments.length; i++){
                        this.attachments[i].path = this.attachments[i].path.replace('/var/www/html/',attachmentUploadUrl);
                    }
                // this.loadImage(this.attachments[0].path);
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