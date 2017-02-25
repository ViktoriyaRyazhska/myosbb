import {
  Component,
  OnInit
} from '@angular/core';
import { NgSwitch } from '@angular/common';
import { LoginService } from '../login/login.service';

@Component({
  selector: 'sidebar-component',
  templateUrl: './sidebar.html',
  providers: [ LoginService, NgSwitch ],
  styleUrls: ['./sidebar.style.css']

})
export class SidebarComponent implements OnInit {
  public authRole: string;
  constructor(
    public loginService: LoginService
  ) {}
  public ngOnInit(): any {
    this.loginService.setRole();
    this.authRole = this.loginService.getRole();
    console.log(this.authRole);
  }
}
