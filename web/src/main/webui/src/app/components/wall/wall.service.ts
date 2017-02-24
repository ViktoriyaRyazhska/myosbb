import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import {Observable} from 'rxjs/Observable';
import "rxjs/add/operator/map";
import 'rxjs/add/operator/catch';
import "rxjs/add/operator/toPromise";
import { API_URL } from '../../../shared/models/localhost.config';
import { LoginService } from '../../shared/login/login.service';

@Injectable()

export class WallService {
  public num: number = Number(localStorage.getItem('user'));
  constructor(
    private http: Http,
    private login: LoginService
  ) {  }
  getWallData(): Observable<any> {
    return this.http.get(`${API_URL}/restful/user/${this.num}`, this.login.getRequestOptionArgs())
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
}
