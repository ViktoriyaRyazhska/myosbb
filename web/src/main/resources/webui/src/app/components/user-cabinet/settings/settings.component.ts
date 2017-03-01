import { Component, OnInit, Input } from '@angular/core';

import { Settings }     from './Settings';
import { User }         from '../../../models/user.model';

import { SettingsService } from './settings.service';
import { LoginService }    from '../../../shared/login/login.service';

import { ToasterService } from 'angular2-toaster';

@Component({
  selector: 'settings',
  providers: [SettingsService, ToasterService],
  templateUrl: './settings.template.html',
  styleUrls: [ './settings.css' ],
})

export class SettingsComponent implements OnInit {

    private currentUser: User;
    private settings: Settings;

    constructor(private settingsService: SettingsService,
                private currentUserService: LoginService,
                private toasterService: ToasterService,
    ) {
        this.currentUser = this.currentUserService.getUser();
        this.settings = new Settings();
    }

    public ngOnInit() {
       this.settingsService.getSettingsForUser()
       .then( (settings) => this.settings = settings);
    }

    public save() {
        this.settingsService.save(this.settings);
        this.toasterService.pop('success', 'Your changes is saved successfully');
    }

    public changeAssigned() {
      this.settings.assigned  = !this.settings.assigned;
    }

    public changeCreator() {
        this.settings.creator  = !this.settings.creator;
    }

    public changeComment() {
        this.settings.comment  = !this.settings.comment;
    }

    public changeAnswer() {
        this.settings.answer  = !this.settings.answer;
      }
}
