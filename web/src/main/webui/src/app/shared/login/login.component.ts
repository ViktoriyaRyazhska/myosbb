import {
  Component,
  OnInit
} from '@angular/core';
import { LoginService } from './login.service';
import { ApiService } from './api.service';
@Component({
  selector: 'login',
  providers: [LoginService],
  styleUrls: [ './login.css' ],
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  public model = { username : '', password : ''};
    constructor(public loginService: LoginService
    ) {}
  public  ngOnInit(): any {
    this.loginService.checkLogin();
  }
  public logIn() {
    this.loginService.onSubmit(this.model);
    this.model.username = '';
    this.model.password = '';
  }
}
