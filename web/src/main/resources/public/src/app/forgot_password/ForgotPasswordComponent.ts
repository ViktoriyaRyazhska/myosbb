import {Component,OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component{
    selector:'forgot-pass',
        templateUrl:'src/app/forgot_password/forgotpass.html'
}
export class ForgotPasswordComponent implements OnInit{
    email="";
    constructor(private route:ActivatedRoute ){}

    ngOnInit():any{
        this.email=this.route.queryParams.map(params => params['email'] || 'None');
}

}
