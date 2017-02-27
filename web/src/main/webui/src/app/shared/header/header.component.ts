import {
  Component
} from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login/login.service';
@Component({
  selector: 'app-header',
  styleUrls: [ './header.scss' ],
  templateUrl: './header.html'
})
export class AppHeaderComponent {
  constructor(public loginService: LoginService, public _router: Router
    ) {}
  public logOut() {
    localStorage.clear();
    this._router.navigate(['./login']);
  }
}
