import { Component, OnInit } from '@angular/core';

import { OsbbDTO } from '../../models/osbb-dto.model';
import { User } from '../../models/user.model';
import { OsbbService } from '../../services/osbb.service';
import { LoginService } from '../../shared/login/login.service';

@Component({
  selector: 'user-menu-osbb-contacts',
  templateUrl: './osbb-contacts.html',
  styleUrls: ['./osbb-contacts.scss'],
})
export class OsbbContactsComponent implements OnInit {

  public userOsbb: OsbbDTO;
  public user: User;
  public osbbRetrieved = false;

  constructor(private osbbService: OsbbService, private loginService: LoginService) {}

  public ngOnInit(): any {
    this.loginService.sendToken().subscribe( (data) => {
      this.user = data.json();
      this.getOsbb();
     });
  }

  public getOsbb() {
    this.osbbService.getDTOOsbbById(this.user.osbbId)
      .then( (osbb) => {
        this.userOsbb = osbb;
        this.osbbRetrieved = true;
      });
  }
}
