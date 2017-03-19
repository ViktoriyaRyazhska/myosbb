import { Injectable } from '@angular/core';
import {
  Http,
  Response,
  RequestOptions,
  Headers
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';
import { LoginService } from '../../shared/login/login.service';
import { PageRequest } from '../../models/pageRequest.model';
import { UrlListConfig } from '../../services/apiUrl.service';
import {Ticket, TicketState, ITicket} from "../../models/ticket.model";
import { LoginConstants } from '../../shared/login/login.constants';
import {User} from "../../models/user.model";
import { API_URL } from '../../models/localhost.config';

@Injectable()
export class TicketService {
  private pageRequest = new PageRequest(1, 10, 'time', false);
  private getUsers:string=API_URL+'/restful/user/osbb';
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

getAllUsers(osbbId:number):Promise<User[]> {
        return this.http.get(`${this.getUsers}/${osbbId}`, this.login.getRequestOptionArgs())
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }
listAllUsersInOsbb(osbbId:number):Observable<any>{
 return this.http.get(`${this.getUsers}/${osbbId}`, this.login.getRequestOptionArgs())
            .map(res => res.json())
            .catch(this.handleError);
}
addTicket(ticket:ITicket):Promise<ITicket> {
  console.log('ticket service.add start')
        return this.http.post(UrlListConfig.URL_LIST.ticketUrl, JSON.stringify(ticket), this.login.getRequestOptionArgs())
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    };
    private handleError(error:any):Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    };

 deleteTicket(ticket:ITicket):Promise<ITicket> {
        return this.http.delete(UrlListConfig.URL_LIST.ticketUrl+`${ticket.ticketId}`,this.login.getRequestOptionArgs())
            .toPromise()
            .then(res => ticket)
            .catch(this.handleError);
    }

}
