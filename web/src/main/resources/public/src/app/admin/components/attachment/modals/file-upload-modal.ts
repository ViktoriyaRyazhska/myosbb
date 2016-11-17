/**
 * Created by nataliia on 20.09.16.
 */

import { Component, Output, EventEmitter, ViewChild } from '@angular/core';
import { CORE_DIRECTIVES } from '@angular/common';
import { Attachment } from "../attachment.interface";
import { MODAL_DIRECTIVES, BS_VIEW_PROVIDERS } from 'ng2-bootstrap/ng2-bootstrap';
import { ModalDirective } from "ng2-bootstrap/ng2-bootstrap";
import { TranslatePipe } from "ng2-translate";
import { CapitalizeFirstLetterPipe } from "../../../../../shared/pipes/capitalize-first-letter";
import { FileSelectDirective, FileDropDirective, FileUploader, FileItem } from "ng2-file-upload";
import ApiService = require("../../../../../shared/services/api.service");

const attachmentUploadUrl = ApiService.serverUrl + '/restful/attachment/';

@Component({
    selector: 'file-upload-modal',
    templateUrl: 'src/app/admin/components/attachment/modals/file-upload-modal.html',
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, FileSelectDirective, FileDropDirective],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe]
})

export class FileUploadComponent {

    public uploader:FileUploader = new FileUploader({url: attachmentUploadUrl, authToken: 'Bearer '
                                    + localStorage.getItem('access_token')});
    public hasDropZoneOver:boolean = false;
    public fileOverBase(e:any):void {
        this.hasDropZoneOver = e;
    }

    @Output() upload: EventEmitter<any>;
    private attachments: Attachment[];
    @ViewChild('uploadModal')
    uploadModal:ModalDirective;

    constructor() {
        this.upload = new EventEmitter<any>();
        this.uploader.toReturn = [];
        this.uploader.onCompleteItem = function(item, response) {
            this.toReturn.push(JSON.parse(response));
        }
    }

    openUploadModal(attachmentId:Attachment): void {
        this.uploadModal.show();
    }

    closeUploadModal() {
        console.log('closing upload modal');
        this.upload.emit(this.uploader.toReturn);
        this.uploader.toReturn = [];
        this.uploader.clearQueue();
        this.uploadModal.hide();
    }

    transform(bytes) {
        if(bytes == 0) return '0 Bytes';
        var k = 1000;
        var sizes = ['Bytes', 'KB', 'MB', 'GB'];
        var i = Math.floor(Math.log(bytes) / Math.log(k));
        return (bytes / Math.pow(k, i)).toFixed(1) + ' ' + sizes[i];
    }
}