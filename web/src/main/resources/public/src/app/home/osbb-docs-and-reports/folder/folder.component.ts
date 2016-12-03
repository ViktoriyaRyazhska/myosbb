import { Component } from '@angular/core';
import { TranslatePipe } from 'ng2-translate';

import { User } from '../../../../shared/models/User';
import { CurrentUserService } from "../../../../shared/services/current.user.service";

@Component({
    selector: 'osbb-folder',
    templateUrl: 'src/app/home/osbb-docs-and-reports/folder/folder.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css'],
    pipes:[TranslatePipe]
})
export class FolderComponent {
    private currentRole: string;

    constructor(private userService: CurrentUserService) {
    }

    ngOnInit(): any {
        console.log('Initializing Folder component...');
        this.currentRole = this.userService.getRole();
    }
}
