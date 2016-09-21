import {bootstrap} from '@angular/platform-browser-dynamic';
import {HTTP_PROVIDERS, Http,  XHRBackend, RequestOptions,} from '@angular/http';
import {ROUTER_DIRECTIVES,Router} from '@angular/router';
import {APP_ROUTER_PROVIDERS} from "./app/app.routes";
import {AppComponent} from "./app/app.component";
import {disableDeprecatedForms, provideForms} from '@angular/forms';
import {TranslateService, TranslateLoader, TranslateStaticLoader} from "ng2-translate/ng2-translate";
import {CurrentUserService} from "./shared/services/current.user.service";
 import {enableProdMode} from "@angular/core";
import {LoginService}from "./app/login/login.service";
import {HttpClient} from "./shared/services/HttpClient";
import {AdminLoginGuard} from "./shared/guard/admin.login.guard";
import {ManagerLoginGuard} from "./shared/guard/manager.login.guard";

 enableProdMode();
bootstrap(AppComponent,
    [HTTP_PROVIDERS, APP_ROUTER_PROVIDERS,
        ROUTER_DIRECTIVES, CurrentUserService, LoginService,HttpClient,
        AdminLoginGuard,
        ManagerLoginGuard,
        TranslateService,
        {
            provide: TranslateLoader,
            useFactory: (http: Http) => new TranslateStaticLoader(http, 'assets/i18n', '.json'),
            deps: [Http]
        },
       { provide:Http, 
        useFactory: (xhrBackend: XHRBackend, requestOptions: 
        RequestOptions, router: Router) => 
               new HttpClient(xhrBackend, requestOptions, router),
        deps: [XHRBackend, RequestOptions, Router]
    }
        , disableDeprecatedForms(),
        provideForms()])
    .catch(err => console.error(err));