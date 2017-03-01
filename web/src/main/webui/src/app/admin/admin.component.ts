import {
  Component,
  OnInit,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AdminRoutes } from './admin.routes';

@Component({
  selector: 'admin',
  template: `
  
  <sidebar-component></sidebar-component>
  <router-outlet></router-outlet>
`
})
export class AdminComponent {
  public static routes = AdminRoutes;
}
