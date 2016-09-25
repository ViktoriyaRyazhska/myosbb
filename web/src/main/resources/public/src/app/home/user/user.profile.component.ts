import {Component, OnInit} from '@angular/core'
import {User} from '../../../shared/models/User';
import {Router,ROUTER_DIRECTIVES} from '@angular/router';
import {REACTIVE_FORM_DIRECTIVES} from '@angular/forms';
import {TranslatePipe} from "ng2-translate";
import {CapitalizeFirstLetterPipe} from "../../../shared/pipes/capitalize-first-letter"
import MaskedInput from 'angular2-text-mask';
import {Subscription} from "rxjs";
import {HeaderComponent} from "../../header/header.component";
import {CurrentUserService} from "../../../shared/services/current.user.service";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'friend',
    templateUrl: 'src/app/home/user/user.profile.html',
    directives: [REACTIVE_FORM_DIRECTIVES, MaskedInput, HeaderComponent, ROUTER_DIRECTIVES],
    styleUrls: ['src/app/home/user/profile.css', 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe]

})
export class UserProfileComponent implements OnInit {
    private user:User = new User();
    private sub:Subscription;
    private userId:number;
    currentUser:User;
    _currentUserService = null;

    constructor(private router:Router,
                private routeParams:ActivatedRoute,
                private userService:CurrentUserService) {
        this._currentUserService = HeaderComponent.currentUserService;
        this.currentUser = this._currentUserService.getUser();
    }

    ngOnInit():any {
        this.sub = this.routeParams.params.subscribe((params)=> {
            this.userId = +params['id'];
            this.userService.getUserById(this.userId)
                .subscribe((data) => {
                        this.user = data
                    },
                    (error)=> {
                        console.error(error)
                    }
                )
        });

    }

    getTime(time:Date):string {
        return new Date(time).toLocaleDateString();
    }

}