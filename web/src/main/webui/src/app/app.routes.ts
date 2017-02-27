import { Routes } from '@angular/router';
import { LoginComponent } from './shared/login';
import { RegistrationComponent } from './components/registration';
import { RegistrationSuccessComponent } from './components/registration/registration-sucess';
import { ManagerRoutes } from './managerComponent/manager.routes';
import { AdminRoutes } from './adminComponent/admin.routes';
import { UserRoutes } from './userComponent/user.routes';

export const ROUTES: Routes = [
  { path: '',  component: LoginComponent },
  { path: 'login',  component: LoginComponent },
  { path: 'registration',  component: RegistrationComponent },
  { path: 'registration/success', component: RegistrationSuccessComponent },
  ...ManagerRoutes,
  ...AdminRoutes,
  ...UserRoutes
];
