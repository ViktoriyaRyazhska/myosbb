import { Routes } from '@angular/router';
import { LoginComponent } from './shared/login';
import { RegistrationComponent } from './registration';
import { RegistrationSuccessComponent } from './registration/registration-sucess';
import { ManagerRoutes } from './manager/manager.routes';
import { AdminRoutes } from './admin/admin.routes';
import { UserRoutes } from "./user/user.routes";
import { routesCabinet } from './components/user-cabinet/user-cabinet.routes.ts';

export const ROUTES: Routes = [
  { path: '',  component: LoginComponent },
  { path: 'login',  component: LoginComponent },
  { path: 'cabinet', loadChildren: './components/user-cabinet#UserCabinetComponent' },
  { path: 'registration',  component: RegistrationComponent },
  { path: 'registration/success', component: RegistrationSuccessComponent },
  ...ManagerRoutes,
  ...AdminRoutes,
  ...UserRoutes

];
