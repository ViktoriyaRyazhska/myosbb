import { Injectable } from '@angular/core';
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
import { PageParams } from '../../../shared/models/pageParam.interface';

@Injectable()

export class OsbbService {
  private pageParams: PageParams = {pageNumber: 1, sortedBy: 'date', orderType: false, rowNum: 10};
  constructor(
    private http: Http,
    public login: LoginService,

  ) { }

  getEventData(): Observable<any> {
    return this.http.post(`${API_URL}/restful/bill/?status=ALL`, JSON.stringify(this.pageParams), this.login.getRequestOptionArgs())
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
}
