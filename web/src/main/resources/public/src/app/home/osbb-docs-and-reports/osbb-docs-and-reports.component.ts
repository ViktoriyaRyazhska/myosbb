import { Component, OnInit, OnDestroy } from '@angular/core';
import { ROUTER_DIRECTIVES, Router, ActivatedRoute } from "@angular/router";
import { TranslatePipe } from 'ng2-translate';
import { ToasterContainerComponent, ToasterService, ToasterConfig } from 'angular2-toaster/angular2-toaster';
import { TranslateService} from 'ng2-translate';

import { Subscription } from 'rxjs/Subscription';
import { User } from '../../../shared/models/User';
import { CapitalizeFirstLetterPipe } from '../../../shared/pipes/capitalize-first-letter';
import { CurrentUserService } from "../../../shared/services/current.user.service";
import { FolderService } from './folder-manager/folder.service';
import { Folder } from './folder-manager/folder';

@Component({
    selector: 'docs-and-reports',
    templateUrl: 'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css', 
                'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.css'],
    directives: [ROUTER_DIRECTIVES, ToasterContainerComponent],
    providers: [FolderService, ToasterService],
    pipes:[CapitalizeFirstLetterPipe, TranslatePipe]
})
export class OsbbDocumentsAndReportsComponent implements OnInit, OnDestroy {
    private subscription: Subscription;
    private currentRole: string;
    private folders: Folder[]; 
    private newFolder: Folder = new Folder();
    private editableFolder: Folder = new Folder();
    private parentId: number;
    private deleteId: number = 0;
    private editMode: boolean = false;

    constructor(private userService: CurrentUserService,
                private router: Router, 
                private activatedRoute: ActivatedRoute, 
                private folderService: FolderService,
                private toasterService: ToasterService,
                private translateService: TranslateService) 
    {
        this.subscription =  activatedRoute.params.subscribe(
            (params) => {
                this.parentId = params['id'];
                this.checkParentId();
                this.initFolders();
            }
        );  
    }

    ngOnInit() {      
        this.currentRole = this.userService.getRole(); 
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    private initFolders() {
        this.checkParentId();
        this.folderService.getFoldersByParentId(this.parentId)
            .subscribe(
                data => this.folders = data,                    
                error => console.error(error)                
            );
    }

    private createNewFolder(name: string) {
        if (this.folderExist(name)) {
            this.toasterService.pop('error', this.translate('folder_exist'));
        } else {
            console.log('Saving folder ' + this.newFolder.name + ' with parentId=' + this.parentId);
            this.folderService.save(name, this.parentId)
                .subscribe(
                    data => {
                        this.newFolder = data;
                        this.initFolders();
                        this.toasterService.pop('success', this.translate('folder_created'));                        
                    },
                    error => console.error(error)
                );
        }
        this.newFolder = new Folder();
    }

    private deleteFolder() { 
        this.folderService.delete(this.deleteId).subscribe(
            data => {                    
                this.initFolders();
                this.toasterService.pop('success', this.translate('folder_deleted'));
                this.deleteId = 0;
            },
            error => {
                console.error(error);
                this.toasterService.pop('error', this.translate('could_not_delete'));
            }
        );
    }

    private updateFolder() {       
        if (this.folderExist(this.editableFolder.name)) {
            this.toasterService.pop('error', this.translate('folder_exist'));
        } else {
            this.folderService.update(this.editableFolder)
                .subscribe(
                    data => {
                        this.editableFolder = data;
                        this.initFolders();
                    },
                    error => console.error(error)
            );    
        }    
    }

    private getFolder(id: number) {
        this.folderService.getFolder(id)
            .subscribe(
                data => this.editableFolder = data,                    
                error => console.log(error)                
            );
    }
    
    private checkParentId() {
        if (!this.parentId) {
            this.parentId = 1;
             console.log('parentId set to 1');
        }
    }

    private toggleEditMode() {
        this.editMode = !this.editMode;
    }

    private folderExist(name: string): boolean {
        var exist: boolean = false;
        var i: number;
        var size = this.folders.length;

        for (i = 0; i < size; i++) {
            if (this.folders[i].name.toUpperCase() == name.toUpperCase()) {                
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

    private setDeleteId(id: number) {
        this.deleteId = id;
    }

}
