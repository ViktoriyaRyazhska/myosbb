import {RouterConfig,RouterOutlet} from "@angular/router";
import {Component, OnInit} from '@angular/core';
import {CORE_DIRECTIVES} from '@angular/common';
import {RouteConfig, ROUTER_DIRECTIVES } from '@angular/router-deprecated';
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS} from 'ng2-bootstrap/ng2-bootstrap';
import {TranslatePipe} from "ng2-translate";
import {CapitalizeFirstLetterPipe} from "../../../shared/pipes/capitalize-first-letter"
import {SettingsService} from './settings.service';
import {HeaderComponent} from "../../header/header.component";
import {User} from './../user';
import {Settings} from './settings';
import {CurrentUserService} from "./../../../shared/services/current.user.service";
import {ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {ToasterContainerComponent, ToasterService, ToasterConfig} from 'angular2-toaster/angular2-toaster';

@Component({
    selector: 'settings',
    templateUrl: './src/app/user/settings/settings.component.html',
    providers: [ SettingsService,ToasterService],
    directives: [RouterOutlet, ROUTER_DIRECTIVES, CORE_DIRECTIVES,ModalDirective,ToasterContainerComponent],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TranslatePipe,CapitalizeFirstLetterPipe]
})


export class SettingsComponent implements OnInit {

    private currentUser:User;
    private _currentUserService = null;
    private settings:Settings;

    constructor(private settingsService:SettingsService,
                private currentUserService:CurrentUserService,
                private toasterService:ToasterService
    ) {
        this._currentUserService=HeaderComponent.currentUserService;
        this.currentUser = this._currentUserService.getUser();
        this.settings = new Settings();
            }

   ngOnInit() {
       this.settingsService.getSettingsForUser()
       .then(settings => this.settings = settings);
    }

    save(){
        this.settingsService.save(this.settings);
        this.toasterService.pop('success'
                              , "Saved!");                               
    }

    changeAssigned(){
        this.settings.assigned  = !this.settings.assigned;   
    }

    setAssigned(){
      return this.settings.assigned == true ? 'switch-on':'switch-off';
    }

    changeCreator(){
        this.settings.creator  = !this.settings.creator;
    }

    setCreator(){
      return this.settings.creator == true ? 'switch-on':'switch-off';
    }

    changeComment(){
        this.settings.comment  = !this.settings.comment;  
    }

    setComment(){
      return this.settings.comment == true ? 'switch-on':'switch-off';
    }

    
    changeAnswer(){
        this.settings.answer  = !this.settings.answer;       
    }

    setAnswer(){
      return this.settings.answer == true ? 'switch-on':'switch-off';
    }


    
}