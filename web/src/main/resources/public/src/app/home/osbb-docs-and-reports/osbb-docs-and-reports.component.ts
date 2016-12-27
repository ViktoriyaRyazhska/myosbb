import { Component, OnInit } from '@angular/core';
import { TranslatePipe } from 'ng2-translate';
import { TranslateService} from 'ng2-translate';
import { ToasterContainerComponent, ToasterService, ToasterConfig } from 'angular2-toaster/angular2-toaster';
import { FILE_UPLOAD_DIRECTIVES, FileSelectDirective, FileUploader } from 'ng2-file-upload/ng2-file-upload';
import ApiService = require('../../../shared/services/api.service');

import { Subscription } from 'rxjs/Subscription';
import { User } from '../../../shared/models/User';
import { CapitalizeFirstLetterPipe } from '../../../shared/pipes/capitalize-first-letter';
import { CurrentUserService } from "../../../shared/services/current.user.service";
import { DriveFile } from './google-drive-service/drive-file';
import { GoogleDriveService } from './google-drive-service/google-drive.service';

const uploadUrl = ApiService.serverUrl + '/restful/google-drive/upload';

@Component({
    selector: 'docs-and-reports',
    templateUrl: 'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css',
        'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.css'],
    directives: [FILE_UPLOAD_DIRECTIVES, FileSelectDirective, ToasterContainerComponent],
    providers: [GoogleDriveService, ToasterService],
    pipes: [CapitalizeFirstLetterPipe, TranslatePipe]
})
export class OsbbDocumentsAndReportsComponent implements OnInit {

    public uploader: FileUploader = new FileUploader({url: uploadUrl, authToken: 'Bearer ' + localStorage.getItem('access_token')});    
    private subscription: Subscription;
    private currentRole: string;
    private currentFolder: string;
    private newFolder: DriveFile;
    private editable: DriveFile;
    private deleteId: string;
    private editMode: boolean;
    private files: DriveFile[];
    private parents: string[];
    private paths: string[];

    constructor(private userService: CurrentUserService,
                private toasterService: ToasterService,
                private translateService: TranslateService,
                private driveService: GoogleDriveService) {
        this.newFolder = new DriveFile();
        this.editable = new DriveFile();
        this.currentFolder = 'appDataFolder';
        this.parents = new Array<string>();
        this.uploader = new FileUploader({url: uploadUrl + '/' + this.currentFolder, authToken: 'Bearer ' + localStorage.getItem('access_token')});        
    }

    ngOnInit() {
        this.currentRole = this.userService.getRole();
        this.initFolder(this.currentFolder);
    }

    private createNewFolder(name: string) {
        if (this.folderExist(name)) {
            this.toasterService.pop('error', this.translate('folder_exist'));
        } else {
            console.log('Saving folder ' + this.newFolder.name + ' with parentId=' + this.currentFolder);
            this.driveService.createFolder(name, this.currentFolder)
                .subscribe(
                data => {
                    // this.openFolder(this.currentFolder);
                    this.initFolder(this.currentFolder);
                    this.toasterService.pop('success', this.translate('folder_created'));
                },
                error => this.errorHandler(error, 'folder_exist')
                );
        }
        this.newFolder = new DriveFile();
    }

    private delete() {
        this.driveService.delete(this.deleteId).subscribe(
            data => {
                // this.openFolder(this.currentFolder);
                this.initFolder(this.currentFolder);
                this.toasterService.pop('success', this.translate('folder_deleted'));
                this.deleteId = "";
                console.log('Folder ' + data + ' deleted');
            },
            error => this.errorHandler(error, 'could_not_delete')
        );
    }

    private update() {
        if (this.folderExist(this.editable.name)) {
            this.toasterService.pop('error', this.translate('folder_exist'));
        } else {
            this.driveService.update(this.editable.id, this.editable.name)
                .subscribe(
                    data => {
                        this.editable = data;
                        // this.openFolder(this.currentFolder);
                        this.initFolder(this.currentFolder);
                    },
                    error => this.errorHandler(error, 'could_not_update')
            );    
        }    
    }

    private getFile(id: string) {
        this.driveService.getFile(id).subscribe(
            data => this.editable = data,
            error => console.log(error)
        );
    }

    private toggleEditMode() {
        this.editMode = !this.editMode;
    }

    private folderExist(name: string): boolean {
        var exist: boolean;
        var i: number;
        var size = this.files.length;

        for (i = 0; i < size; i++) {
            if (this.files[i].name.toUpperCase() == name.toUpperCase()) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    private translate(message: string): string {
        let translation: string;
        this.translateService.get(message).subscribe(
            data => translation = data
        );
        return translation;
    }

    private setDeleteId(id: string) {
        this.deleteId = id;
    }

    private errorHandler(error: any, i18n_key: string) {
        console.error(error);
        this.toasterService.pop('error', this.translate(i18n_key));
    }

    private openFolder(id: string, fileName: string) {
        this.parents.push(this.currentFolder); 
        this.currentFolder = id;
        this.initFolder(this.currentFolder);
        this.paths.push(fileName);        
    }

    private initFolder(id: string) {        
        this.driveService.getFilesByParent(id).subscribe(
            data => {
                this.files = data;
                this.sortFiles();
            },
            error => console.error(error)
        );
        this.uploader.setOptions({url: uploadUrl + '/' + this.currentFolder});
        console.log(this.uploader);          
    }

    private sortFiles() {
        this.sortByName();
        this.sortFoldersFirst();       
    }

    private sortByName(){
        this.files.sort((f1, f2) => {
            var nameA = f1.name.toUpperCase();
            var nameB = f2.name.toUpperCase();
            if (nameA < nameB) {
                return -1;
            }
            if (nameA > nameB) {
                return 1;
            }
            return 0;
        });
    }

    private sortFoldersFirst() {
        this.files.sort((f1, f2) => {            
            if (f1.folder && !f2.folder) {
                return -1;
            }
            if (!f1.folder && f2.folder) {
                return 1;
            }
            return 0;
        });
    }

    private root() {
        if (this.parents.length != 0) {
            this.openFolder('appDataFolder', "AppFolder");
            this.paths = new Array<string>();
        }
    }

    private up() {        
        this.currentFolder = this.parents.pop();
        this.paths.pop();
        this.initFolder(this.currentFolder);                
    }

    private goTo(index: number) {
        console.log("Folder index = " + index);
    }

    private onUpload() {
        let i: number;
        let len: number = this.uploader.queue.length;

        for (i = 0; i < len; i++) {
            console.log(this.uploader.queue[i]);
            this.uploader.queue[i].upload();
        }     
    }

    private onDownload(id: string, name: string) {
        this.driveService.download(id, name);
    }
}
