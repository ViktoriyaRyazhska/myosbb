import { Component, OnInit } from '@angular/core';
import { ROUTER_DIRECTIVES, Router, ActivatedRoute } from "@angular/router";
import { TranslatePipe } from 'ng2-translate';
import { ToasterContainerComponent, ToasterService, ToasterConfig } from 'angular2-toaster/angular2-toaster';
import { TranslateService} from 'ng2-translate';

import { Subscription } from 'rxjs/Subscription';
import { User } from '../../../shared/models/User';
import { CapitalizeFirstLetterPipe } from '../../../shared/pipes/capitalize-first-letter';
import { CurrentUserService } from "../../../shared/services/current.user.service";
import { DriveFile } from './google-drive-service/drive-file';
import { GoogleDriveService } from './google-drive-service/google-drive.service';

@Component({
    selector: 'docs-and-reports',
    templateUrl: 'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css',
        'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.css'],
    directives: [ROUTER_DIRECTIVES, ToasterContainerComponent],
    providers: [GoogleDriveService, ToasterService],
    pipes: [CapitalizeFirstLetterPipe, TranslatePipe]
})
export class OsbbDocumentsAndReportsComponent implements OnInit {
    private subscription: Subscription;
    private currentRole: string;
    private currentFolder: string = "root";
    private newFolder: DriveFile = new DriveFile();
    private editable: DriveFile = new DriveFile();
    private deleteId: string;
    private editMode: boolean;
    private files: DriveFile[];
    private parents: string[];

    constructor(private userService: CurrentUserService,
                private router: Router,
                private activatedRoute: ActivatedRoute,
                private toasterService: ToasterService,
                private translateService: TranslateService,
                private driveService: GoogleDriveService) {
        this.currentFolder = 'appDataFolder';
        this.parents = new Array<string>();    
        console.log(this.parents.length);    
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
                    this.openFolder(this.currentFolder);
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
                this.openFolder(this.currentFolder);
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
                        this.openFolder(this.currentFolder);
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

    private openFolder(id: string) {
        this.parents.push(this.currentFolder); 
        this.currentFolder = id;
        this.initFolder(this.currentFolder);        
    }

    private initFolder(id: string) {        
        this.driveService.getFilesByParent(id).subscribe(
            data => this.files = data,
            error => console.error(error)
        );
    }

    private root() {
        if (this.parents.length != 0) {
            this.openFolder('appDataFolder');
        }
    }

    private up() {        
        this.currentFolder = this.parents.pop();
        this.initFolder(this.currentFolder);                
    }

}
