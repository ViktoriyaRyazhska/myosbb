import { Component, OnInit } from '@angular/core';

import { OsbbDTO } from "../../../shared/models/osbbDTO";
import { OsbbService } from '../../admin/components/osbb/osbb.service';
import { TranslatePipe } from "ng2-translate";
import { CapitalizeFirstLetterPipe } from "../../../shared/pipes/capitalize-first-letter";
import { CurrentUserService } from "../../../shared/services/current.user.service";

@Component({
    selector: 'user-menu-osbb-contacts',
    templateUrl: 'src/app/home/osbb-contacts/osbb-contacts.html',
    styleUrls: ['src/app/home/home_wall/home.wall.css']
})
export class OsbbContactsComponent implements OnInit {

    userOsbb: OsbbDTO;

    constructor(private osbbService: OsbbService, private userService: CurrentUserService) { }

    ngOnInit() {
        this.osbbService.getDTOOsbbById(this.userService.getUser().osbbId)
            .then(data => this.userOsbb = data);
    }
}