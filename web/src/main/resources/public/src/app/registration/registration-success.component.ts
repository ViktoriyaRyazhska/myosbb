import { Component, OnInit } from "@angular/core";
import { TranslatePipe } from "ng2-translate";
import { ROUTER_DIRECTIVES, Router } from "@angular/router";

@Component({
    selector: 'register-success',
    templateUrl: 'src/app/registration/registration-success.html',
    styleUrls: ['assets/css/registration/registration.css'],
    directives: [ROUTER_DIRECTIVES],
    pipes: [TranslatePipe]
})

export class RegistrationSuccessComponent {

    constructor(private router:Router){}
}
