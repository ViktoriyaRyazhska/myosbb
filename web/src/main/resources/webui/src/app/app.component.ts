import {
  Component,
  OnInit,
  ViewEncapsulation
} from '@angular/core';
import { AppState } from './app.service';
import { Router } from '@angular/router';
import { LoginService } from './shared/login/login.service';

@Component({
  selector: 'app',
  encapsulation: ViewEncapsulation.None,
  styleUrls: [
    './app.component.scss',
    '../assets/css/default.style.scss'
  ],
  templateUrl: './app.html'
})
export class AppComponent implements OnInit {
  public name = 'Наш Двір';
  constructor(
    public appState: AppState,
    public _router: Router,
    public loginService: LoginService
  ) { }
  public ngOnInit() {
    this.loginService.setData();
  }
}
