import { Component } from '@angular/core';
import { TranslatePipe } from 'ng2-translate';
import { ROUTER_DIRECTIVES } from "@angular/router";

import { User } from '../../../../shared/models/User';
import { CapitalizeFirstLetterPipe } from '../../../../shared/pipes/capitalize-first-letter';
import { CurrentUserService } from "../../../../shared/services/current.user.service";

@Component({
    selector: 'folder-manager',
    templateUrl: 'src/app/home/osbb-docs-and-reports/folder-manager/folder-manager.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css'],
    directives: [ROUTER_DIRECTIVES],
    pipes:[TranslatePipe]
})
export class FolderManagerComponent {
    private currentRole: string;

    constructor(private userService: CurrentUserService) {
    }

    ngOnInit(): any {
        console.log('Initializing Folder component...');
        this.currentRole = this.userService.getRole();
    }
}
