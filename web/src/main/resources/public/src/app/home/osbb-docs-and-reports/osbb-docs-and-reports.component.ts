import { Component, OnInit } from '@angular/core';
import { ROUTER_DIRECTIVES, Router, ActivatedRoute } from "@angular/router";

import { TranslatePipe } from 'ng2-translate';
import { CapitalizeFirstLetterPipe } from '../../../shared/pipes/capitalize-first-letter';
import { User } from '../../../shared/models/User';
import { CurrentUserService } from "../../../shared/services/current.user.service";
import { FolderService } from './folder-manager/folder.service';

@Component({
    selector: 'osbb-docs-and-reports',
    templateUrl: 'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css'],
    directives: [ROUTER_DIRECTIVES],
    providers: [FolderService],
    pipes:[CapitalizeFirstLetterPipe, TranslatePipe]
})
export class OsbbDocumentsAndReportsComponent implements OnInit {
    private currentRole: string;
    private folder: string;

    constructor(private userService: CurrentUserService, private activatedRoute: ActivatedRoute, private folderService: FolderService) {
        this.folder = this.activatedRoute.snapshot.params['folder'];
    }

    ngOnInit(): any {
        console.log('Initializing OSBB Docs and Reports...');
        this.currentRole = this.userService.getRole();
        this.folderService.getRootFolders();
    }
}
