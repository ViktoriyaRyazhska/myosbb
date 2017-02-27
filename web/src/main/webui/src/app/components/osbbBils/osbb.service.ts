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
import { PageParams } from '../../../assets/models/pageParam.interface';
import { UrlListConfig } from '../../../assets/services/apiUrl.service';

@Injectable()

export class OsbbService {

  constructor(
    private _http: Http,
    public login: LoginService,
  ) { }

  public getBilsData(parameters: PageParams, status: string): Observable<any> {
    return this._http.post(UrlListConfig.URL_LIST.billUrl + status,
      JSON.stringify(parameters), this.login.getRequestOptionArgs())
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  };

  public findBillById(billId: number): Observable<any> {
    return this._http.get(UrlListConfig.URL_LIST.idUrl + `${billId}`,
      this.login.getRequestOptionArgs())
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  };
}
