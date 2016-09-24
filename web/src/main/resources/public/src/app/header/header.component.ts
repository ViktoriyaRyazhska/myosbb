import {Component, OnInit, OnDestroy, Output} from '@angular/core';
import {ROUTER_DIRECTIVES, ActivatedRoute} from '@angular/router';
import {FORM_DIRECTIVES} from '@angular/common';
import 'rxjs/Rx';
import {LoginStat} from "../../shared/services/login.stats";
import {Http} from "@angular/http";
import {TranslateService, TranslatePipe} from "ng2-translate/ng2-translate";
import {DROPDOWN_DIRECTIVES} from 'ng2-bs-dropdown/dropdown';
import {LoginService} from "../login/login.service";
import {CurrentUserService} from "../../shared/services/current.user.service";
import {CapitalizeFirstLetterPipe} from "../../shared/pipes/capitalize-first-letter";
import {MODAL_DIRECTIVES } from 'ng2-bs3-modal';
import {Notice,NoticeType} from './notice';
import {NoticeService} from './header.notice.service';
import {User} from "../../shared/models/user";
import {TimerWrapper} from '@angular/core/src/facade/async';
import {Observable} from "rxjs/Observable";
import {Router} from '@angular/router';
import {SettingsService} from "./../user/settings/settings.service";
import {Settings} from "./../user/settings/settings";

@Component({
    selector: 'app-header',
    templateUrl: 'src/app/header/header.html',
    providers: [LoginStat, LoginService, NoticeService, SettingsService],
    inputs: ['isLoggedIn'],
    directives: [ROUTER_DIRECTIVES, DROPDOWN_DIRECTIVES, MODAL_DIRECTIVES, FORM_DIRECTIVES],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    styleUrls: ['src/app/user/ticket/ticket.css']


})

export class HeaderComponent implements OnInit,OnDestroy {
    static translateService:TranslateService;
    static currentUserService:CurrentUserService;
    isLoggedIn:boolean;
    sub:any;
    rrr:boolean = true;
    languages:Array<string> = ['en', 'uk'];
    selectedLang:string = 'uk';
    reouterUrl:string = 'login';
    noticeArr:Notice[] = [];
    tLength:number = 0;
    mLength:number = 0;

    wordT:string = '';
    wordM:string = '';

    settings:Settings;

    constructor(private noticeService:NoticeService,
                private settingsService:SettingsService,
                private _currentUserService:CurrentUserService,
                private _loginService:LoginService,
                private _loginStat:LoginStat,
                private _route:ActivatedRoute,
                private router:Router,
                private translate:TranslateService,
                private http:Http,
                private _loginservice:LoginService) {

        this._loginStat.loggedInObserver$ .subscribe(stat => {
            this.isLoggedIn = stat;
        });

        HeaderComponent.translateService = translate;
        HeaderComponent.currentUserService = _currentUserService;
        var userLang = navigator.language.split('-')[1]; // use navigator lang if available
        userLang = /(en|uk)/gi.test(userLang) ? userLang : 'uk';
        // this language will be used as a fallback when a translation isn't found in the current language
        translate.setDefaultLang('uk');

        // the lang to use, if the lang isn't available, it will use the current loader to get them
        this.selectedLang = userLang;
        translate.use(this.selectedLang);
        translate.currentLang = this.selectedLang;
        console.log("default lang: ", translate.currentLang);
        console.log("shared sevice: ", HeaderComponent.translateService);
        console.log("shared sevice: ", HeaderComponent.currentUserService);
    }

    ngOnInit():any {
        this.sub = this._route.params.subscribe(params=>
            this.isLoggedIn = params['status']);
        HeaderComponent.currentUserService = this._currentUserService;
        this.getNotice();
    }


    ngOnDestroy():any {
        this.sub.unsubscribe();
    }

    getNotice() {
        this.settingsService.getSettingsForUser()
            .then(settings =>this.settings = settings);
        TimerWrapper.setInterval(() => {
            if (this._loginService.checkLogin() && this.isSettings() == true) {
                this.noticeService.getNotice().subscribe(
                    data => {
                        this.noticeArr = data,
                            this.getLengthNotice();
                        this.wordT = this.wordTNews();
                        this.wordM = this.wordMNews();
                    }
                )
            }
        }, 7000)

    }

    isSettings():boolean {
        if (this.settings.answer == true ||
            this.settings.comment == true ||
            this.settings.creator == true ||
            this.settings.answer == true) {
            return true;
        }
        return false;
    }

    onSelect(lang) {
        this.selectedLang = lang;
        this.translate.use(lang);
        this.translate.currentLang = lang;
        console.log("current lang: ", this.translate.currentLang);
    }


    getLengthNotice() {
        let t = 0;
        let m = 0;
        for (let i = 0; i < this.noticeArr.length; i++) {
            if (this.noticeArr[i].typeNotice == 'TO_CREATOR' ||
                this.noticeArr[i].typeNotice == 'TO_ASSIGNED') {
                t++;
            }
            if (this.noticeArr[i].typeNotice == 'MESSAGE' ||
                this.noticeArr[i].typeNotice == 'ANSWER') {
                m++;
            }
        }
        this.mLength = m;
        this.tLength = t;

    }

    removeLengthNotice(notice:Notice) {
        if (notice.typeNotice == 'TO_CREATOR' ||
            notice.typeNotice == 'TO_ASSIGNED') {
            this.tLength--;
        }
        if (notice.typeNotice == 'MESSAGE' ||
            notice.typeNotice == 'ANSWER') {
            this.mLength--;
        }

    }

    removeNotice(notice:Notice) {
        let index = this.noticeArr.indexOf(notice);
        if (index > -1) {
            console.log("deleting notice!!!");
            this.noticeArr.splice(index, 1);
            this.noticeService.deleteNotice(notice);
            this.removeLengthNotice(notice);
        }
    }

    hideNotice(notice:Notice) {
        this.removeNotice(notice);
        this.router.navigate(['' + notice.path]);
    }

    getTime(time:Date):string {
        return new Date(time).toLocaleTimeString();
    }

    wordTNews():string {
        let num = Math.abs((this.tLength) % 100) % 10;
        if (num >= 5 && num < 20)
            return "pending_task";
        if (num > 1 && num < 5)
            return "pending_tasks";
        if (num == 1)
            return "pending_task1";
        return "pending_task1";

    }

    wordMNews():string {
        let num = Math.abs((this.mLength) % 100) % 10;
        if (num == 1)
            return "noticeComment";
        if (num > 1 && num < 5)
            return "noticeComment3";
        return "noticeComments";

    }

    routerNavigate() {
        if (this._loginService.checkLogin()) {
            if (this._currentUserService.getRole() == "ROLE_ADMIN") {
                this.router.navigate(['admin']);
            }
            if (this._currentUserService.getRole() == "ROLE_USER") {
                this.router.navigate(['home/wall']);
            }
            if (this._currentUserService.getRole() == "ROLE_MANAGER") {
                this.router.navigate(['manager']);
            }
        } else {
            this.router.navigate(['login']);
        }

    }

}
