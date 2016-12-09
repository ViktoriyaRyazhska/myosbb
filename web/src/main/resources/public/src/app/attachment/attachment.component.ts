import { Component, EventEmitter, ViewChild, Input, Output } from "@angular/core";
import { CORE_DIRECTIVES } from "@angular/common";
import "rxjs/Rx";
import { MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective } from "ng2-bootstrap/ng2-bootstrap";
import { FileSelectDirective, FileDropDirective, FileUploader } from "ng2-file-upload/ng2-file-upload";
import { TranslatePipe } from "ng2-translate/ng2-translate";
import ApiService = require("../../shared/services/api.service");
import { CapitalizeFirstLetterPipe } from "../../shared/pipes/capitalize-first-letter";
import FileLocationPath = require("../../shared/services/file.location.path");
import { AttachmentService } from "../admin/components/attachment/attachment.service";
import { Attachment } from "../admin/components/attachment/attachment.interface";

const attachmentUploadUrl = ApiService.serverUrl + '/restful/attachment/';
const fileUploadPath = FileLocationPath.fileUploadPath;
const fileDownloadPath = FileLocationPath.fileDownloadPath;

@Component({
    selector: 'myosbb-attachment',
    templateUrl: 'src/app/attachment/attachment.html',
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    providers: [AttachmentService],
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, FileSelectDirective, FileDropDirective],
    viewProviders: [BS_VIEW_PROVIDERS]
})
export class AttachmentComponent  {

    public uploader:FileUploader = new FileUploader({url: attachmentUploadUrl, authToken: 'Bearer '
                                                    + localStorage.getItem('access_token')});
    public hasDropZoneOver:boolean = false;
    public fileOverBase(e:any):void {
        this.hasDropZoneOver = e;
    }

    private attachments:Attachment[] = [];
    private disabledLogoBtn = true;
    @Input() private enableLogoBtn: boolean = false;

    @Output() attachmentChanged : EventEmitter<Attachment[]> = new EventEmitter();
    @Output() logoChanged : EventEmitter<String> = new EventEmitter();

    constructor(private _attachmentService:AttachmentService) {
    }

    transform(bytes) {
        if(bytes == 0) return '0 Bytes';
        var k = 1000;
        var sizes = ['Bytes', 'KB', 'MB', 'GB'];
        var i = Math.floor(Math.log(bytes) / Math.log(k));
        return (bytes / Math.pow(k, i)).toFixed(1) + ' ' + sizes[i];
    }

    onUpload(){
        console.log("uploading...");
        console.log("logo", this.enableLogoBtn);
        let count: number = this.uploader.queue.length;
        this.uploader.uploadAll();
        this.disabledLogoBtn = false;
        this._attachmentService.findLast(count).subscribe(
            (data) => {
                this.attachments = data;
                console.log("attachments ", this.attachments);
                this.attachmentChanged.emit(this.attachments);
            }, (err) => {
                console.log(err);
            }
        );
    }

    onLogoChanged(filename: String){
        console.log(fileUploadPath +  filename );
        this.logoChanged.emit(fileUploadPath +  filename );
    }

    onCancel(){
        console.log("cancelling");
        this.uploader.cancelAll();
    }

    onRemove(){
        console.log("removing");
        this.uploader.clearQueue()
    }
}
