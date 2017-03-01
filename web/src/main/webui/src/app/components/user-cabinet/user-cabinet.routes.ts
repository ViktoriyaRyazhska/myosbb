import { UserBillsComponent }        from './user-bills/user-bills.component';
import { ProfileComponent }          from './profile/profile.component';
import { ProfileSidebarComponent }   from './profile-sidebar/profile-sidebar.component';
import { SettingsComponent }         from './settings/settings.component';
import { UserCabinetComponent }      from './user-cabinet.component';

export const routesCabinet = [
  { path: 'cabinet',  component: UserCabinetComponent,
    children: [
    { path: '', component: ProfileComponent },
      { path: 'settings', component: SettingsComponent },
      { path: 'user_bills', component: UserBillsComponent },
      { path: 'profile', component: ProfileComponent },
    ]},
];
