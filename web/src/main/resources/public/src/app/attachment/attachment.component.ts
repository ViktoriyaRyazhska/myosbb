import {Component, OnInit, OnDestroy, EventEmitter, ViewChild, Input, Output} from "@angular/core";
import {CORE_DIRECTIVES} from "@angular/common";
import "rxjs/Rx";
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {FileSelectDirective, FileDropDirective, FileUploader} from "ng2-file-upload/ng2-file-upload";
import {TranslatePipe} from "ng2-translate/ng2-translate";
import ApiService = require("../../shared/services/api.service");
import {CapitalizeFirstLetterPipe} from "../../shared/pipes/capitalize-first-letter";
import FileLocationPath = require("../../shared/services/file.location.path");
import {AttachmentService} from "../admin/components/attachment/attachment.service";
import {Attachment} from "../admin/components/attachment/attachment.interface";

const attachmentUploadUrl = ApiService.serverUrl + '/restful/attachment/';
const fileUploadPath = FileLocationPath.fileUploadPath;
const fileDownloadPath = FileLocationPath.fileDownloadPath;

@Component({
    selector: 'myosbb-attachment',
    template: `
<div class="row">
                                <div class="col-md-3">
                                    <p>{{ 'selectFiles' | translate | capitalize }}</p>
                                    <input type="file" ng2FileSelect [uploader]="uploader" multiple  /><br/>
                                    <div ng2FileDrop
                                         [ngClass]="{'nv-file-over': hasDropZoneOver}"
                                         (fileOver)="fileOverBase($event)"
                                         [uploader]="uploader"
                                         class="well my-drop-zone">
                                        {{ 'dropZone' | translate | capitalize }}
                                    </div>
                                </div>

                                <div class="col-md-9" style="margin-bottom: 40px">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th width="30%">{{ 'name' | translate | capitalize }}</th>
                                            <th>{{ 'size' | translate | capitalize }}</th>
                                            <th>{{ 'status' | translate | capitalize }}</th>
                                            <th width="40%">{{ 'actions' | translate | capitalize }}</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr *ngFor="let item of uploader.queue">
                                            <td><strong>{{ item.file.name }}</strong></td>
                                            <td nowrap>{{ transform(item.file.size) }}</td>
                                            <td class="text-center">
                                                <span *ngIf="item.isSuccess"><i class="glyphicon glyphicon-ok"></i></span>
                                                <span *ngIf="item.isCancel"><i class="glyphicon glyphicon-ban-circle"></i></span>
                                                <span *ngIf="item.isError"><i class="glyphicon glyphicon-remove"></i></span>
                                            </td>
                                            <td nowrap>
                                                <button type="button" class="btn btn-success btn-xs"
                                                        (click)="onUpload()" [disabled]="item.isReady || item.isUploading || item.isSuccess">
                                                    <span class="glyphicon glyphicon-upload"></span> {{ 'upload' | translate | capitalize }}
                                                </button>
                                                <button type="button" class="btn btn-danger btn-xs"
                                                        (click)="onRemove()">
                                                    <span class="glyphicon glyphicon-trash"></span> {{ 'remove' | translate | capitalize }}
                                                </button>
                                                 <button  *ngIf="enableLogoBtn" [disabled]="disabledLogoBtn" type="button" class="btn btn-warning btn-xs"
                                                                                    (click)="onLogoChanged(item.file.name)">
                                                                                <span class="glyphicon glyphicon-camera"></span> Logo
                                                 </button>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>

                                    <div>
                                        <div>
                                            {{ 'progress' | translate | capitalize }}:
                                            <div class="progress" style="">
                                                <div class="progress-bar" role="progressbar" [ngStyle]="{ 'width': uploader.progress + '%' }"></div>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-success btn-s"
                                                (click)="onUpload()" [disabled]="!uploader.getNotUploadedItems().length">
                                            <span class="glyphicon glyphicon-upload"></span> {{ 'upload' | translate | capitalize }} {{ 'all' | translate }}
                                        </button>
                                        <button type="button" class="btn btn-warning btn-s"
                                                (click)="onCancel()" [disabled]="!uploader.isUploading">
                                            <span class="glyphicon glyphicon-ban-circle"></span> {{ 'cancel' | translate | capitalize }} {{ 'all' | translate }}
                                        </button>
                                        <button type="button" class="btn btn-danger btn-s"
                                                (click)="onRemove()" [disabled]="!uploader.queue.length">
                                            <span class="glyphicon glyphicon-trash"></span> {{ 'remove' | translate | capitalize }} {{ 'all' | translate }}
                                        </button>
                                    </div>
                                </div>

                            </div>`,
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