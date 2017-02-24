import {Component, OnInit} from '@angular/core';
import {
  Http,
  Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import "rxjs/add/operator/map";
import 'rxjs/add/operator/catch';
import "rxjs/add/operator/toPromise";
import { API_URL } from '../../../shared/models/localhost.config';
import { LoginService } from '../../shared/login/login.service';
import { OsbbService } from './osbb.service';
import { ChartsModule } from "ng2-charts/ng2-charts";


@Component({
  selector: 'osbb',
  styleUrls: ['../../../assets/css/manager.page.layout.scss'],
  templateUrl: './osbb.component.html',
  providers: [ LoginService,  OsbbService ]
})

export class OsbbBillsComponent implements OnInit {
  public data: any;
  public title: string = `Osbb`;

  constructor(
    public http: Http,
    public login: LoginService,
    public osbb: OsbbService
  ) { }
  public ngOnInit() {

    this.osbb.getEventData().subscribe(responze => {
      this.data = responze.rows;
    });
  };
}

