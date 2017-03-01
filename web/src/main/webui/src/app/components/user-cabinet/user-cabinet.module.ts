import { NgModule }           from '@angular/core';
import { CommonModule }       from '@angular/common';
import { FormsModule }        from '@angular/forms';
import { RouterModule }       from '@angular/router';
import { routesCabinet }      from './user-cabinet.routes.ts';
import { TextMaskModule }     from 'angular2-text-mask';
import { HttpModule, Http }   from '@angular/http';

import { UserBillsComponent }        from './user-bills/user-bills.component';
import { ProfileComponent }          from './profile/profile.component';
import { ProfileSidebarComponent }   from './profile-sidebar/profile-sidebar.component';
import { SettingsComponent }         from './settings/settings.component';
import { UserCabinetComponent }      from './user-cabinet.component';
import { UpperCaseFirstLetterPipe } from './upper-case-first-letter.pipe';

import { TranslateModule, TranslateLoader, TranslateStaticLoader } from 'ng2-translate';

import { ToasterModule, ToasterService } from 'angular2-toaster';

@NgModule({
  imports:      [
    CommonModule,
    FormsModule,
    TextMaskModule,
    ToasterModule,
    RouterModule.forChild(routesCabinet),
    TranslateModule.forRoot({
        provide: TranslateLoader,
        useFactory: (http: Http) => new TranslateStaticLoader(http, 'assets/i18n', '.json'),
        deps: [Http]
    }),
  ],
  declarations: [
    UserBillsComponent,
    ProfileComponent,
    ProfileSidebarComponent,
    SettingsComponent,
    UserCabinetComponent,
    UpperCaseFirstLetterPipe
  ],
  providers : [
  ToasterService
  ],

})
export class UserCabinetModule { }
