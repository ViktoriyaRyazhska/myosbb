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
import { MODAL_DIRECTIVES } from 'ng2-bs3-modal';
import {Notice} from './notice';
import {NoticeService} from './header.notice.service';
import {User} from './../user/user';
import {TimerWrapper} from '@angular/core/src/facade/async';
import {Observable} from "rxjs/Observable";
import {Router} from '@angular/router';

@Component({
    selector: 'app-header',
    templateUrl: 'src/app/header/header.html',
    providers: [LoginStat,LoginService,NoticeService,CurrentUserService],
    inputs: ['isLoggedIn'],
    directives: [ROUTER_DIRECTIVES, DROPDOWN_DIRECTIVES,MODAL_DIRECTIVES,FORM_DIRECTIVES],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    styleUrls: ['src/app/user/ticket/ticket.css']


})

export class HeaderComponent implements OnInit,OnDestroy {
    
    //@Output() notice:EventEmitter<Notice>
    
    static translateService : TranslateService;
    static currentUserService:CurrentUserService;
    isLoggedIn:boolean;
    sub:any;
    
    languages: Array<string> = ['en', 'uk'];
    selectedLang : string = 'uk';

     noticeArr:Notice[] = [];
     arrLength:number = 0;

    constructor(private noticeService:NoticeService,
                private _currentUserService:CurrentUserService,
                private _loginService:LoginService,
                private _loginStat:LoginStat,
                private _route:ActivatedRoute, 
                private router:Router,
                private translate: TranslateService, 
                private http:Http,
                private _loginservice: LoginService) {

        this._loginStat.loggedInObserver$ .subscribe(stat => {
                this.isLoggedIn = stat;       });

        HeaderComponent.translateService = translate;
        HeaderComponent.currentUserService=_currentUserService;
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
    
     //let timer = Observable.timer(2000,1000);
  //  this.kk();

                }

    getNotice(){
        TimerWrapper.setInterval(() => {  
        console.log("get Notice");
        if(this._loginService.checkLogin())   {
         this.noticeService.getNotice().subscribe(
                data => {this.noticeArr = data,
               this.arrLength = this.noticeArr.length
                    
                }
        )}
        }, 7000)
         
      
    }

    
    ngOnInit():any {
        this.sub = this._route.params.subscribe(params=>
            this.isLoggedIn = params['status']);


      //TimerWrapper.setTimeout(() => {  
    this.getNotice();
//}, 1000);
       
    }


    ngOnDestroy():any {
        this.sub.unsubscribe();
    }

    goToPage(path:string){
        
    }

    onSelect(lang){
        this.selectedLang = lang;
        this.translate.use(lang);
        this.translate.currentLang = lang;
        console.log("current lang: ", this.translate.currentLang);
    }

removeNotice(notice:Notice){
     let index = this.noticeArr.indexOf(notice);
         if (index > -1) {
            console.log("deleting notice!!!");
            this.noticeArr.splice(index, 1);
        
        this.noticeService.deleteNotice(notice);
        this.arrLength--;
    }
}

    hideNotice(notice:Notice){
        this.removeNotice(notice);        
        this.router.navigate([''+notice.path]);
    }

    isArrLength(){
        this.arrLength != 0?true:false;
    }

    wordNews():string{
       let num = Math.abs((this.arrLength) % 100) % 10; 
	console.log("num"+num);
    
	if (num >= 5 && num < 20) 
		return "pending_task";
	if (num > 1 && num < 5) 
		return "pending_tasks";
	if (num == 1) 
		return "pending_task1";
	return "pending_task1";

    }

}