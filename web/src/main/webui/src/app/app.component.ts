import {
  Component,
  OnInit,
  ViewEncapsulation
} from '@angular/core';
import { AppState } from './app.service';
import { Router } from '@angular/router';

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
  public angularclassLogo = 'assets/img/favicon.ico';
  public name = 'Наш Двір';

  constructor(
    public appState: AppState,
    public router: Router
  ) { }

  public ngOnInit() {
    // this.router.navigate(['./login']);
  }

}

