import { Component, OnInit, Input } from '@angular/core';
import { Router }                 from '@angular/router';

import { User }                   from '../../../models/user.model';
import { ProfileService }         from './profile.service';
import { LoginService }           from '../../../shared/login/login.service';
import { ProfileConstants }       from './profile.constants';

@Component({
  selector: 'profile',
  providers: [ProfileService, ProfileConstants],
  styleUrls: [ './profile.css' ],
  templateUrl: './profile.template.html',

})

export class ProfileComponent implements OnInit {
    public currentUser: User;
    public updateUser: User;
    public profileMask = ProfileConstants.ProfileMask;
    public emailMask = this.profileMask.emailMask;
    public textMask = this.profileMask.textMask;
    public phoneMask = this.profileMask.phoneMask;

    private expToken: string;

    constructor(private router: Router, private profileService: ProfileService,
                private currentUserService: LoginService) {
        this.expToken = localStorage.getItem('expires_in');
        this.expToken = new Date( parseInt(this.expToken, 10)).toLocaleString();
    }
    public ngOnInit() {
        this.getCurrentUser();
    }
    public getTime(time: Date): string {
        return new Date(time).toLocaleDateString();
    }

    public getCurrentUser() {
        this.currentUser = this.currentUserService.getUser();
        this.updateUser = Object.assign({}, this.currentUser);
    }

    public changeUser() {
        let user: User;
        this.profileService.updateUser(this.updateUser).subscribe((data) => {
            user = <User> data.json();
            this.currentUserService.setEditUser(user);
            this.currentUser = this.currentUserService.currentUser;
            this.updateUser = Object.assign({}, this.currentUser);
         });
     }
}
