import { Component, OnInit } from '@angular/core';

import { OsbbDTO } from "../../../shared/models/osbbDTO";
import { OsbbService } from '../../admin/components/osbb/osbb.service';
import { TranslatePipe } from 'ng2-translate';
import { CapitalizeFirstLetterPipe } from '../../../shared/pipes/capitalize-first-letter';
import { CurrentUserService } from "../../../shared/services/current.user.service";
import { User } from '../../../shared/models/User';
import { HeaderComponent } from '../../header/header.component';

@Component({
    selector: 'user-menu-osbb-contacts',
    templateUrl: 'src/app/home/osbb-contacts/osbb-contacts.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css'],
    providers: [OsbbService],
    pipes:[CapitalizeFirstLetterPipe, TranslatePipe]
})
export class OsbbContactsComponent implements OnInit {

    private userOsbb: OsbbDTO;
    private user: User;
    private osbbRetrieved = false;

    constructor(private osbbService: OsbbService, private userSevice: CurrentUserService) {
        this.userOsbb = null;
     }

    ngOnInit(): any {
        console.log('Initializing OSBB contacts...');
        this.getUser();
        this.getOsbb();
    }

    getUser() {
        this.user = this.userSevice.getUser();
        console.log('Current user is ' + this.user.firstName + ' ' + this.user.lastName+' '+this.user.osbbId);
    }

    getOsbb() {
        console.log(this.user);
        this.osbbService.getDTOOsbbById(this.user.osbbId)
            .then(osbb => {
                this.userOsbb = osbb;
                console.log('Retrieving OSBB for ' + this.user.firstName + ' ' + this.user.lastName);
                console.log(this.userOsbb);
                this.osbbRetrieved = true;
            });
    }
}
