import { Component, OnInit, OnDestroy } from '@angular/core';
import { ROUTER_DIRECTIVES, Router, ActivatedRoute } from "@angular/router";
import { TranslatePipe } from 'ng2-translate';

import { Subscription } from 'rxjs/Rx';
import { User } from '../../../../shared/models/User';
import { CapitalizeFirstLetterPipe } from '../../../../shared/pipes/capitalize-first-letter';
import { CurrentUserService } from "../../../../shared/services/current.user.service";
import { FolderService } from './folder.service';
import { Folder } from './Folder';

@Component({
    selector: 'folder-manager',
    templateUrl: './src/app/home/osbb-docs-and-reports/folder-manager/folder-manager.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css', 
                'src/app/home/osbb-docs-and-reports/folder-manager/folder-manager.css'],
    directives: [ROUTER_DIRECTIVES],
    providers: [FolderService],
    pipes:[CapitalizeFirstLetterPipe, TranslatePipe]
})
export class FolderManagerComponent implements OnInit, OnDestroy {
    private subscription: Subscription;
    private currentRole: string;
    private folders: Folder[]; 
    private newFolder: Folder = new Folder();
    private parentId: number; 

    constructor(private userService: CurrentUserService,
                private router: Router, 
                private activatedRoute: ActivatedRoute, 
                private folderService: FolderService) 
    {
        this.subscription =  activatedRoute.params.subscribe(
            (params) => {
                this.parentId = params['id'];
                this.checkParentId();
                this.initFolders(this.parentId);
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

    private initFolders(parentId: number) {
        this.checkParentId();
        this.folderService.getFoldersByParentId(parentId)
            .subscribe(
                data => this.folders = data,                    
                error => console.log(error)                
            );
    }

    private createNewFolder(name: string) {
        console.log('Saving folder ' + this.newFolder.name + ' with parentId=' + this.parentId);
        this.folderService.saveFolder(name, this.parentId)
            .subscribe(
                data => {
                    this.newFolder = data;
                    console.log('Successfully saved: name=' + this.newFolder.name + ', id=' + this.newFolder.id);
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
}
