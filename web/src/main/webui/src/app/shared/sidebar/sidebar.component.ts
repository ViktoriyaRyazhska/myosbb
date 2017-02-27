import {
  Component,
  OnInit
} from '@angular/core';
import { NgSwitch } from '@angular/common';
import { LoginService } from '../login/login.service';
import { SidebarConstants } from './sidebar.constants';

@Component({
  selector: 'sidebar-component',
  templateUrl: './sidebar.html',
  providers: [ NgSwitch ],
  styleUrls: ['./sidebar.style.css']

})
export class SidebarComponent implements OnInit {
  public authRole: string;
  public admins = SidebarConstants.admin;
  public managers = SidebarConstants.manager;
  public users = SidebarConstants.user;
  public providers = SidebarConstants.providers;
  constructor(
    public loginService: LoginService
  ) {}
  public ngOnInit(): any {
    this.loginService.setRole();
    this.authRole = this.loginService.getRole();
  }
}

