import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import ApiService = require("../../../shared/services/api.service");
import {User} from './../../../shared/models/User';
import {ITicket,TicketState} from './ticket';
import {PageRequest} from '../../../shared/models/page.request';
import {Observable} from "rxjs/Observable";
import {Mail} from "../../../shared/models/mail.interface";


@Injectable()
export class TicketService {

    private url:string = ApiService.serverUrl + '/restful/ticket';
    private getAssignUser:string = ApiService.serverUrl + '/restful/user/osbb/';
    private getTicketByPage:string = ApiService.serverUrl + '/restful/ticket/page';
    private getUsers:string = ApiService.serverUrl + '/restful/user/osbb';
    private findTicketByName:string = ApiService.serverUrl + '/restful/ticket/findName';
    private findTicketByState:string = ApiService.serverUrl + '/restful/ticket/state';
    private findTicketByUser:string = ApiService.serverUrl + '/restful/ticket/user';


    constructor(private http:Http) {
    }

    getTicketsByPage(pageRequest:PageRequest):Observable<any> {
        return this.http.post(this.getTicketByPage, JSON.stringify(pageRequest))
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getTicketsSorted(pageRequest:PageRequest):Observable<any> {
        return this.http.post(this.getTicketByPage, JSON.stringify(pageRequest))
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    findByNameDescription(pageRequest:PageRequest, osbbId:number, findName:string):Observable<any> {
        return this.http.post(this.findTicketByName + '?name=' + findName + '&&osbbId=' + osbbId, JSON.stringify(pageRequest))
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    findByUser(pageRequest:PageRequest, email:string, state:TicketState):Observable<any> {
        return this.http.post(this.findTicketByUser + '?user=' + email + '&&state=' + state, JSON.stringify(pageRequest))
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    findByAssigned(pageRequest:PageRequest, email:string, state:TicketState):Observable<any> {
        return this.http.post(this.findTicketByUser + '?assign=' + email + '&&state=' + state, JSON.stringify(pageRequest))
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    findByState(pageRequest:PageRequest, state:TicketState):Observable<any> {
        return this.http.post(this.findTicketByState + '?state=' + state, JSON.stringify(pageRequest))
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllTicket():Promise<ITicket[]> {
        return this.http.get(this.url)
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    getAllUsers(osbbId:number):Promise<User[]> {
        return this.http.get(`${this.getUsers}/${osbbId}`)
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    getTicketbyId(ticketId:number):Promise<ITicket> {
        let url = `${this.url}/${ticketId}`;
        return this.http.get(url)
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    addTicket(ticket:ITicket):Promise<ITicket> {
        return this.http.post(this.url, JSON.stringify(ticket))
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    editTicket(ticket:ITicket):Promise<ITicket> {
        return this.http.put(this.url, JSON.stringify(ticket))
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    deleteTicket(ticket:ITicket):Promise<ITicket> {
        let url = `${this.url}/${ticket.ticketId}`;
        return this.http.delete(url)
            .toPromise()
            .then(res => ticket)
            .catch(this.handleError);
    }


    private handleError(error:any):Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }

}