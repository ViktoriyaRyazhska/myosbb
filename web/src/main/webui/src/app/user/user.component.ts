import {
  Component,
  OnInit,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserRoutes } from './user.routes';

@Component({
  selector: 'user',
  template: `
  <sidebar-component></sidebar-component>
  <router-outlet></router-outlet>
`
})
export class UserComponent {
  public static routes = UserRoutes;
}
