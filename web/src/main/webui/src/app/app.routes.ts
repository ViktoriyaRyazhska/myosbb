import { Routes } from '@angular/router';
import { LoginComponent } from './shared/login';
import { ManagerRoutes } from './manager/manager.routes';
import { AdminRoutes } from './admin/admin.routes';
import { UserRoutes } from "./user/user.routes";
export const ROUTES: Routes = [

  { path: 'login',  component: LoginComponent },
  ...ManagerRoutes,
  ...AdminRoutes,
  ...UserRoutes


];
