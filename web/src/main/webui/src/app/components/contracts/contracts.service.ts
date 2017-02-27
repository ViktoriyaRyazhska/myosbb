import { Injectable } from '@angular/core';
import {
  Http,
  Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';
import { LoginService } from '../../shared/login/login.service';
import { UrlListConfig } from '../../../assets/services/apiUrl.service';

@Injectable()

export class ContractsService {

  constructor(
    private http: Http,
    public loginService: LoginService
  ) { }

  public getContractsData(): Observable<any> {
    return this.http.get(UrlListConfig.URL_LIST.contractsUrl,
      this.loginService.getRequestOptionArgs())
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  };

  public findByProviderName(search: string): Observable<any> {
    return  this.http.get(UrlListConfig.URL_LIST.contactNameUrl + 'find?name=' + search,
      this.loginService.getRequestOptionArgs())
      .map((res) => res.json())
      .catch((error) => Observable.throw(error));
  };
}
