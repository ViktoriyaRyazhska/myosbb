import { Injectable } from '@angular/core';
import {
  Http,
  Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';
import { API_URL } from '../../../../../assets/models/localhost.config';
import { LoginService } from '../../../../shared/login/login.service';

@Injectable()

export class SubTicketService {

  constructor(
    private http: Http,
    private login: LoginService
  ) { }

  public getTicketData(ticket: string): Observable<any> {
    return this.http.get(`${API_URL}/restful/ticket/${ticket}`, this.login.getRequestOptionArgs())
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
}
