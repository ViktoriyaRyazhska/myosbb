import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';
import {
  NgModule,
  ApplicationRef
} from '@angular/core';
import {
  removeNgStyles,
  createNewHosts,
  createInputTransfer
} from '@angularclass/hmr';
import {
  RouterModule,
  PreloadAllModules
} from '@angular/router';

import { TranslateModule, TranslateLoader, TranslateStaticLoader } from 'ng2-translate';
import { ToasterModule, ToasterService } from 'angular2-toaster';
import { FileSelectDirective, } from 'ng2-file-upload';
import { TextMaskModule } from 'angular2-text-mask';
import { SelectModule } from 'ng2-select';
import { MomentModule } from 'angular2-moment';
import {
   ScheduleModule,
   DialogModule,
   InputTextModule,
   InputMaskModule,
   CheckboxModule,
   ButtonModule,
   TabViewModule,
   CalendarModule,
   CodeHighlighterModule
} from 'primeng/primeng';
import {
  AlertModule,
  DatepickerModule,
  ModalModule
} from 'ng2-bootstrap';
import { Calendar } from 'primeng/components/calendar/calendar';
/*
 * Platform and Environment providers/directives/pipes
 */
import { ENV_PROVIDERS } from './environment';
import { ROUTES } from './app.routes';
import { AppComponent } from './app.component';
import { APP_RESOLVER_PROVIDERS } from './app.resolver';
import { AppState, InternalStateType } from './app.service';
import { LoginComponent } from './shared/login';
import { RegistrationComponent } from './components/registration';
import { RegistrationSuccessComponent } from './components/registration/registration-sucess';
import { AppHeaderComponent } from './shared/header';
import { WallComponent } from './components/wall';
import { HouseComponent } from  './components/house';
import { EventsComponent } from './components/events';
import { OsbbBillsComponent } from './components/osbbBils';
import { ContractsComponent } from  './components/contracts';
import { TicketComponent } from './components/ticket';
import { ProviderComponent } from './components/provider';
import { ApartmentComponent } from './components/apartment';
import { CalendarComponent } from './components/calendar';
import { BreadcrumbComponent } from './components/breadcrumb';
import { SidebarComponent } from './shared/sidebar';
import { SubTicketComponent } from './components/ticket/components/subticket';
import { SetLanguageComponent } from './shared/set-language/';
import { OsbbDocumentsAndReportsComponent } from './components/osbb-docs-and-reports';
import { OsbbContactsComponent } from './components/osbb-contacts';
import { TicketAddFormComponent } from './components/ticket/components/ticketAddFormComponent/ticket-add-form.component';
import { TicketEditFormComponent } from './components/ticket/components/ticketEditFromComponent/ticket-edit-form.component';
import { TicketDelFormComponent } from './components/ticket/components/ticketDelFormComponent/ticket-del-form.component'
import { TicketEditDiscussedFormComponent } from './components/ticket/components/ticketEditDistFormComponent/ticket-editdiscussed-form.component';

import { AdminComponent } from './adminComponent/admin.component';
import { UserComponent } from './userComponent/user.component';
import { ManagerComponent } from './managerComponent/manager.component';

// pipes
import { CapitalizeFirstLetterPipe } from '../assets/pipes/capitalize-first-letter';
import { CapitalizeLetterPipe } from '../assets/pipes/capitalize.firstletter';
// services
import { OsbbService } from '../assets/services/osbb.service';
import { OsbbConstants } from '../assets/services/osbb.constants';
import { LoginService } from './shared/login/login.service';
import { LogedInGuard } from '../assets/services/loged-in-guard.service';
// Application wide providers

const APP_PROVIDERS = [
  ...APP_RESOLVER_PROVIDERS,
  AppState
];

type StoreType = {
  state: InternalStateType,
  restoreInputValues: () => void,
  disposeOldHosts: () => void
};

@NgModule({
  bootstrap: [ AppComponent ],
  declarations: [
    AppHeaderComponent,
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    RegistrationSuccessComponent,
    ManagerComponent,
    AdminComponent,
    UserComponent,
    WallComponent,
    HouseComponent,
    EventsComponent,
    ApartmentComponent,
    BreadcrumbComponent,
    CalendarComponent,
    ProviderComponent,
    OsbbBillsComponent,
    ContractsComponent,
    TicketComponent,
    SidebarComponent,
    SubTicketComponent,
    SetLanguageComponent,
    CapitalizeFirstLetterPipe,
    CapitalizeLetterPipe,
    OsbbDocumentsAndReportsComponent,
    OsbbContactsComponent,
    FileSelectDirective,
    TicketAddFormComponent,
    TicketEditFormComponent,
    TicketDelFormComponent,
    TicketEditDiscussedFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(ROUTES, { useHash: false, preloadingStrategy: PreloadAllModules }),
    TranslateModule.forRoot({
        provide: TranslateLoader,
        useFactory: (http: Http) => new TranslateStaticLoader(http, 'assets/i18n', '.json'),
        deps: [Http]
    }),
    TextMaskModule,
    SelectModule,
    MomentModule,
    ToasterModule,
    RouterModule.forRoot(ROUTES, { useHash: true, preloadingStrategy: PreloadAllModules }),
    ScheduleModule,
    DialogModule,
    InputTextModule,
    InputMaskModule,
    CheckboxModule,
    ButtonModule,
    TabViewModule,
    CalendarModule,
    CodeHighlighterModule,
    AlertModule.forRoot(),
    DatepickerModule.forRoot(),
    ModalModule.forRoot()
  ],
  providers: [
    ENV_PROVIDERS,
    APP_PROVIDERS,
    ToasterService,
    OsbbService,
    OsbbConstants,
    LoginService,
    LogedInGuard
  ]
})
export class AppModule {

  constructor(
    public appRef: ApplicationRef,
    public appState: AppState
  ) {}

  public hmrOnInit(store: StoreType) {
    if (!store || !store.state) {
      return;
    }
    this.appState._state = store.state;
    if ('restoreInputValues' in store) {
      let restoreInputValues = store.restoreInputValues;
      setTimeout(restoreInputValues);
    }
    this.appRef.tick();
    delete store.state;
    delete store.restoreInputValues;
  }

  public hmrOnDestroy(store: StoreType) {
    const cmpLocation = this.appRef.components.map((cmp) => cmp.location.nativeElement);
    const state = this.appState._state;
    store.state = state;
    store.disposeOldHosts = createNewHosts(cmpLocation);
    store.restoreInputValues  = createInputTransfer();
    removeNgStyles();
  }

  public hmrAfterDestroy(store: StoreType) {
    store.disposeOldHosts();
    delete store.disposeOldHosts;
  }
}
