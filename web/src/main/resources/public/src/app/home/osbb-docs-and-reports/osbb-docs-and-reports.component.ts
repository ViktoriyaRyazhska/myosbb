import { Component, OnInit, OnDestroy } from '@angular/core';
import { ROUTER_DIRECTIVES, Router, ActivatedRoute } from "@angular/router";
import { TranslatePipe } from 'ng2-translate';

import { Subscription } from 'rxjs/Subscription';
import { User } from '../../../shared/models/User';
import { CapitalizeFirstLetterPipe } from '../../../shared/pipes/capitalize-first-letter';
import { CurrentUserService } from "../../../shared/services/current.user.service";
import { FolderService } from './folder-manager/folder.service';
import { Folder } from './folder-manager/Folder';

@Component({
    selector: 'docs-and-reports',
    templateUrl: 'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css', 
                'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.css'],
    directives: [ROUTER_DIRECTIVES],
    providers: [FolderService],
    pipes:[CapitalizeFirstLetterPipe, TranslatePipe]
})
export class OsbbDocumentsAndReportsComponent implements OnInit, OnDestroy {
    private subscription: Subscription;
    private currentRole: string;
    private folders: Folder[]; 
    private newFolder: Folder = new Folder();
    private editableFolder: Folder = new Folder();
    private parentId: number;
    private editMode: boolean = false; 

    constructor(private userService: CurrentUserService,
                private router: Router, 
                private activatedRoute: ActivatedRoute, 
                private folderService: FolderService) 
    {
        this.subscription =  activatedRoute.params.subscribe(
            (params) => {
                this.parentId = params['id'];
                this.checkParentId();
                this.initFolders();
                console.log('Folder changed to ' + this.parentId);
            }
        );   
    }

    ngOnInit() {
        console.log('ngOnInit() initializing Folder component...');        
        this.currentRole = this.userService.getRole(); 
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        console.log('Unsubscribed from ActivatedRoure.params');
    }

    private initFolders() {
        this.checkParentId();
        this.folderService.getFoldersByParentId(this.parentId)
            .subscribe(
                data => this.folders = data,                    
                error => console.log(error)                
            );
    }

    private createNewFolder(name: string) {
        console.log('Saving folder ' + this.newFolder.name + ' with parentId=' + this.parentId);
        this.folderService.save(name, this.parentId)
            .subscribe(
                data => {
                    this.newFolder = data;
                    console.log('Successfully saved: name=' + this.newFolder.name + ', id=' + this.newFolder.id);
                    this.initFolders();
                },
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
        console.log(this.editMode);
        this.editMode = !this.editMode;
        console.log(this.editMode);
    }

    private deleteFolder(id: number) {        
        if (confirm()) {
            this.folderService.delete(id)
                .subscribe(
                    data => {                    
                        console.log('Successfully deleted');
                        this.initFolders();
                    },
                    error => console.log(error)
                );
        }
    }

    private updateFolder() {       
        this.folderService.update(this.editableFolder)
            .subscribe(
                data => {                    
                    console.log('Successfully updated');
                    this.editableFolder = data;
                    this.initFolders();
                },
                error => console.log(error)
        );        
    }

    private getFolder(id: number) {
        this.folderService.getFolder(id)
            .subscribe(
                data => this.editableFolder = data,                    
                error => console.log(error)                
            );
    }
}
