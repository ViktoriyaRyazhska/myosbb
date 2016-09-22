import {Component, OnInit, Output} from '@angular/core'
import {EventEmitter} from '@angular/core';
import {User} from "../../../shared/models/User";
import {Osbb} from "../../../shared/models/osbb";
import {RegisterService} from "./register.service";
import {ROUTER_DIRECTIVES, Router} from "@angular/router";
import MaskedInput from 'angular2-text-mask';
import emailMask from 'node_modules/text-mask-addons/dist/emailMask.js';
import {GoogleplaceDirective} from "./googleplace.directive";
import {TimerWrapper} from '@angular/core/src/facade/async';


@Component({
    selector: 'app-register',
    templateUrl: 'src/app/registration/registration_user/registration.html',
    styleUrls: ['assets/css/registration/registration.css'],
    providers: [RegisterService],
    directives: [ROUTER_DIRECTIVES, MaskedInput, GoogleplaceDirective]
})
export class RegistrationComponent implements OnInit {
    registered: boolean;
    options = ['Приєднатись до існуючого ОСББ', 'Створити нове ОСББ'];
    newUser: User = new User();
    newOsbb: Osbb = new Osbb();
    public emailMask = emailMask;
    public textmask = [/[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/];
    public phoneMask = ['(', /[0]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];
    confirmPassword: number = "";
    error: boolean = false;
    errorConfirm: boolean = false;
    errorMsg = "";
    constructor(private registerService: RegisterService, private _router: Router) {
         this.newUser.password = "";
         this.newUser.activated = true;
         this.newOsbb.creationDate = new Date;
    }
    
    onSubmitUser(){
        this.registered = true;
    }

    onSubmitOsbb(){
        this.Sender();
        setTimeout(() => {
        this._router.navigate(['/login']);
        },2000);
    }

    Sender (){
        this.registerService.addUser(this.newUser).subscribe(
                data => {
                    this.newUser=data;  
                    this.newOsbb.creator = this.newUser; 
                    this.registerService.addOSBB(this.newOsbb).subscribe(data=>{
                        console.log(data)
                    });
                    console.log(data);
                error => console.log("Error HTTP Post Service");
                () => console.log("Job Done Osbb!");
                });        
    }



    public address: Object;
    getAddress(place: Object) {
        this.newOsbb.address = place['formatted_address'];
        var location = place['geometry']['location'];
        var lat = location.lat();
        var lng = location.lng();
        console.log("Address Object", place);
    }
    confirmPass() {
        this.error = false;
        var password = this.confirmPassword;
        var password2 = this.newUser.password;
        if (password.length != 0) {
            if (password != password2) {
                this.errorMsg = "Passwords don't match. Please try again";
                this.errorConfirm = true;
                this.confirmPassword = "";
                return;
            }
        }
        if (password2.length < 4 || password2.length > 16) {
            this.errorMsg = "Password cannot be shorter than 4 and longer than 16 characters";
            this.error = true;
            this.errorConfirm = false;
        } else {
            this.errorConfirm = false;
        }
    }

    onUserClick(status) {
        if (status == this.options[1]) {
            console.log('CreateNew');
        }
        else if (status == this.options[0]) {
            console.log('JoinToExist');
            this._router.navigate(['/join/osbb']);
        }
    }
}
