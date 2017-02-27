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
import { PageRequest } from '../../../assets/models/pageRequest.interface';
import { UrlListConfig } from '../../../assets/services/apiUrl.service';

@Injectable()

export class TicketService {
  private pageRequest = new PageRequest(1, 10, 'time', false);
  constructor(
    private http: Http,
    private login: LoginService,
  ) { }

  public getTicketData(): Observable<any> {
    return this.http.post(UrlListConfig.URL_LIST.pageUrl,
      JSON.stringify(this.pageRequest), this.login.getRequestOptionArgs())
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  };

  public findByState(state: string): Observable<any> {
    return this.http.post(UrlListConfig.URL_LIST.stateUrl + '?state=' + state,
      JSON.stringify(this.pageRequest), this.login.getRequestOptionArgs())
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  };

  public findByAssigned(pageRequest: PageRequest, email: string, state: string): Observable<any> {
    return this.http.post(UrlListConfig.URL_LIST.assignUrl + '?assign=' + email +
      '&&state=' + state, JSON.stringify(pageRequest), this.login.getRequestOptionArgs())
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  };
}
