import {Component, OnInit} from '@angular/core'
import {User} from '../../../shared/models/User';
import {Router,ROUTER_DIRECTIVES} from '@angular/router';
import {REACTIVE_FORM_DIRECTIVES} from '@angular/forms';
import {TranslatePipe} from "ng2-translate";
import {CapitalizeFirstLetterPipe} from "../../../shared/pipes/capitalize-first-letter"
import MaskedInput from 'angular2-text-mask';
import emailMask from 'node_modules/text-mask-addons/dist/emailMask.js';
import {HeaderComponent} from "../../header/header.component";
import {CurrentUserService} from "../../../shared/services/current.user.service"
@Component({
    selector: 'my-user-profile',
    templateUrl: 'src/app/user/profile/profile.html',
    directives: [REACTIVE_FORM_DIRECTIVES, MaskedInput, HeaderComponent, ROUTER_DIRECTIVES],
    styleUrls: ['src/app/user/profile/profile.css'],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe]

})
export class ProfileComponent implements OnInit {
    currentUser:User;
    currentUserService:CurrentUserService;
    updateUser:User = new User();
    private expToken:string;
    public emailMask = emailMask;
    public textmask = [/[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/];
    public phoneMask = ['(', /[0]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];

    constructor(private router:Router, private currentUserService:CurrentUserService) {
        this.currentUser = HeaderComponent.currentUserService.currentUser;
        console.log('constructore');
        this.expToken = localStorage.getItem('expires_in');
        this.expToken = new Date(parseInt(this.expToken)).toLocaleString();
        console.log(this.currentUser);
    }

    ngOnInit():any {
        // this.currentUser.birthDate = new Date(this.currentUser.birthDate).toLocaleDateString();
    }

    refreshToken() {
        this.currentUserService.refreshToken();
        this.expToken = localStorage.getItem('expires_in');
        this.expToken = new Date(parseInt(this.expToken)).toLocaleString();

    }


    getTime(time:Date):string {
        return new Date(time).toLocaleString();
    }

}