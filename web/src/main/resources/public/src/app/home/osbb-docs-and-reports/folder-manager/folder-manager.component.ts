import { Component } from '@angular/core';
import { TranslatePipe } from 'ng2-translate';
import { ROUTER_DIRECTIVES, Router, ActivatedRoute } from "@angular/router";

import { User } from '../../../../shared/models/User';
import { CapitalizeFirstLetterPipe } from '../../../../shared/pipes/capitalize-first-letter';
import { CurrentUserService } from "../../../../shared/services/current.user.service";
import { FolderService } from './folder.service';
import { Folder } from './Folder';


@Component({
    selector: 'folder-manager',
    templateUrl: 'src/app/home/osbb-docs-and-reports/folder-manager/folder-manager.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css'],
    directives: [ROUTER_DIRECTIVES],
    providers: [FolderService],
    pipes:[TranslatePipe]
})
export class FolderManagerComponent {
    private folders: Folder[];
    private currentRole: string;
    private currentUrl: string;

    constructor(private userService: CurrentUserService, private activatedRoute: ActivatedRoute, private folderService: FolderService) {

    }

    ngOnInit(): any {
        console.log('Initializing Folder component...');
        this.currentRole = this.userService.getRole();
        this.folderService.getRootFolders().subscribe(
            data => this.folders = data
        );
    }
}
