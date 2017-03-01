import { Component, OnInit } from '@angular/core';
import { TranslateService } from 'ng2-translate';

@Component({
    selector: 'set-language',
    templateUrl: './set-language.component.html',
    styleUrls: ['./set-language.component.scss']
})
export class SetLanguageComponent implements OnInit {
  public lanKey: string;

  constructor(public translate: TranslateService) {}

  public ngOnInit() {
    this.translate.setDefaultLang('en');
    this.lanKey = 'en';
  }
  public setLanguage(key) {
    this.translate.use(key);
    this.lanKey = key;
  }
}
