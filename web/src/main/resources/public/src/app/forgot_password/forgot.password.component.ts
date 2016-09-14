import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute}       from '@angular/router';
import {ForgotPasswordService} from './forgot.password.service';

@Component({
    selector: 'forgot-pass',
    templateUrl: 'src/app/forgot_password/forgotpass.html',
    styleUrls: ['assets/css/registration/registration.css'],
    providers:[ForgotPasswordService]
})
export class ForgotPasswordComponent implements OnInit {
     key;
    keyValid:boolean=false;
    password:string = "";
    confirmPassword:string = "";
    errorMsg:string = "";
    errorConfirm:boolean = false;
    email:string="";


    constructor(private route:ActivatedRoute,private forgotPassService:ForgotPasswordService) {
       this.validateKey();
    }

    ngOnInit():any {
        this.route.params.subscribe(params =>
        {   this.email=params['id']||'None';
            this.key = params['key'] || 'None';});
    }
    confirmPass() {
        this.errorMsg="";
        var password = this.confirmPassword;
        var password2 = this.password;
        if (password.length != 0) {
            if (password != password2) {
                this.errorMsg = "Passwords don't match. Please try again";
                this.errorConfirm = true;
                this.confirmPassword = "";
                return;
            }
        }
    }
    validateKey() {
        this.forgotPassService.validateKey({email:this.email,key:this.key}).subscribe(
            data=> {
                if (data.json() === "FOUND") {
                    keyValid=true;
                } else {
                    this.emailValid = false;
                }
            }
        )
    }

}
