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
    id:string="";
    FormValid=false;


    constructor(private _router:Router,private route:ActivatedRoute,private forgotPassService:ForgotPasswordService) {
        this.route.params.subscribe(params =>
        {   this.id=params['id']||'None';
            this.key = params['key'] || 'None';});
    }

    ngOnInit():any {
        this.validateKey();

    }
    confirmPass() {
        this.errorMsg="";
        var password = this.confirmPassword;
        var password2 = this.password;
        this.FormValid=false;
        if (password.length != 0) {
            if (password != password2) {
                this.errorMsg = "Passwords don't match. Please try again";
                this.errorConfirm = true;
                this.confirmPassword = "";
                return;
            }
            this.FormValid=true;
        }

    }
    validateKey() {
        this.forgotPassService.validateKey({id:this.id,key:this.key}).subscribe(
            data=> {
                if (data.json() === "FOUND") {
                    this.keyValid=true;
                } else {

                    this.keyValid = false;
                this.errorMsg="Your url is not valid. Try again or stop lamatu this Nazar."
                }
            }
        )
    }
    updatePassword(){
        this.forgotPassService.updatePassword(this.id,this.password).subscribe(
            data=>{
                this._router.navigate(['/login'])
            }
            ,
            error=>{
                console.log(error);
            }
        )
    }

}
