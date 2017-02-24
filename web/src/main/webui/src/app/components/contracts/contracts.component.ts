import { Component, OnInit } from '@angular/core';
import { myosbbLink } from '../../app.webpackHardcode.service';
import {
  Http,
  Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { ContractsService } from "./contracts.service";
import { LoginService } from '../../shared/login/login.service';

@Component({
  selector: 'contract',
  styleUrls: ['../../../assets/css/manager.page.layout.scss', './contracts.style.scss'],
  templateUrl: './contracts.component.html',
  providers: [ ContractsService, LoginService ]
})

export class ContractsComponent implements OnInit{
  public resData: any;
  public title: string = 'Contracts';
  constructor(
    private http: Http,
    public login: LoginService,
    public contract: ContractsService
  ) {  }
  public ngOnInit() {
    this.contract.getContractsData().subscribe(data => {
      this.resData = data.rows;
    })
  }
}

