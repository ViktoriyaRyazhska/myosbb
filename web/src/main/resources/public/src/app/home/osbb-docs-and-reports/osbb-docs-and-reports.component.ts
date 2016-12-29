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
const root: string = 'appDataFolder';
const space: string = ' ';

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

    public uploader: FileUploader;
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
        private driveService: GoogleDriveService) { }

    ngOnInit() {
        this.currentRole = this.userService.getRole();
        this.newFolder = new DriveFile();
        this.editable = new DriveFile();
        this.uploader = new FileUploader({
            url: uploadUrl + '/' + this.currentFolder,
            authToken: 'Bearer ' + localStorage.getItem('access_token')
        });
        this.goToRoot();
    }

    private createNewFolder(name: string) {
        if (this.exist(name)) {
            this.toasterService.pop('error', name + space + this.translate('exist'));
        } else {
            this.driveService.createFolder(name, this.currentFolder).subscribe(
                data => {
                    this.initFolder(this.currentFolder);
                    this.toasterService.pop('success', name + space + this.translate('created'));
                },
                error => this.toasterService.pop('error', name + space + this.translate('exist'))
                );
        }
        this.newFolder = new DriveFile();
    }

    private delete() {
        this.driveService.delete(this.deleteId).subscribe(
            data => {
                this.initFolder(this.currentFolder);
                this.toasterService.pop('success', this.translate('deleted'));
                this.deleteId = "";
            },
            error => this.toasterService.pop('error', 'could_not_delete')
        );
    }

    private update() {
        let name: string = this.editable.name;
        if (this.exist(name)) {
            this.toasterService.pop('error', name + space + this.translate('exist'));
        } else {
            this.driveService.update(this.editable.id, name).subscribe(
                data => {
                    this.editable = data;
                    this.initFolder(this.currentFolder);
                },
                error => this.toasterService.pop('error', 'could_not_update')
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

    private exist(name: string): boolean {
        var i: number;
        for (i = 0; i < this.files.length; i++) {
            if (this.files[i].name.toUpperCase() == name.toUpperCase()) {
                return true;
            }
        }
        return false;
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

    private openFolder(id: string, folderName: string) {
        this.parents.push(this.currentFolder);
        this.currentFolder = id;
        this.initFolder(this.currentFolder);
        this.paths.push(folderName);
    }

    private initFolder(id: string) {
        this.driveService.getFilesByParent(id).subscribe(
            data => {
                this.files = data;
                this.sortFiles();
            },
            error => console.error(error)
        );
        this.uploader.setOptions({ url: uploadUrl + '/' + this.currentFolder });
    }

    private goToRoot() {
        if (!this.parents || this.parents.length != 0) {
            this.parents = new Array<string>();
            this.paths = new Array<string>();
            this.currentFolder = root;
            this.initFolder(this.currentFolder);
        }
    }

    private goUp() {
        if (this.currentFolder != root) {
            this.currentFolder = this.parents.pop();
            this.paths.pop();
            this.initFolder(this.currentFolder);
        }
    }

    private goTo(index: number) {
        if (index + 1 == this.parents.length) {
            return;
        }
        this.initFolder(this.parents[index + 1]);
        this.parents = this.parents.slice(0, index + 1);
        this.paths = this.paths.slice(0, index + 1);
    }

    private onUpload() {
        let i: number;

        for (i = 0; i < this.uploader.queue.length; i++) {
            let name: string = this.uploader.queue[i].file.name;
            if (this.exist(name)) {
                this.toasterService.pop('error', name + space + this.translate('exist'));
                this.uploader.queue[i].remove();
            } else {
                this.uploader.queue[i].upload();
            }
        }        
    }

    private onDownload(id: string, name: string) {
        this.driveService.download(id, name);
    }

    private sortFiles() {
        this.sortByName();
        this.sortFoldersFirst();
    }

    private sortByName() {
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

    private clearQueue() {
        this.uploader.clearQueue();
    }

}
