import {
  Component,
  OnInit,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ManagerRoutes } from './manager.routes';

@Component({
  selector: 'manager',
  template: `
  <sidebar-component></sidebar-component>
  <router-outlet></router-outlet>
`
})
export class ManagerComponent {
  public static routes = ManagerRoutes;
}
