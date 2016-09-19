import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import ApiService = require("../../../shared/services/api.service");
import {User} from './../user';
import {ITicket,TicketState} from './ticket';
import {PageRequest} from './page.request';
import {Observable} from "rxjs/Observable";
import {Mail} from "../../../shared/models/mail.interface";


@Injectable()
export class TicketService {

    private deleteUrl:string = ApiService.serverUrl + '/restful/ticket';
    private postUrl:string = ApiService.serverUrl + '/restful/ticket';
    private putUrl:string = ApiService.serverUrl + '/restful/ticket';
    private getUrl:string = ApiService.serverUrl + '/restful/ticket';
    private getOneUrl:string = ApiService.serverUrl + '/restful/ticket';
    private getAssignUser:string = ApiService.serverUrl + '/restful/user/assigned';
    private getTicketByPage:string = ApiService.serverUrl + '/restful/ticket/page';
    private getUsers:string = ApiService.serverUrl + '/restful/user';
    private findTicketByName:string = ApiService.serverUrl + '/restful/ticket/findName';
    private sendEmailAssignUrl:string = ApiService.serverUrl + '/restful/ticket/sendEmailAssign';
    private sendEmailStateUrl:string = ApiService.serverUrl + '/restful/ticket/sendEmailState';
    private findTicketByState:string = ApiService.serverUrl + '/restful/ticket/state';
    private findTicketByUser:string = ApiService.serverUrl + '/restful/ticket/userEmail';
    private findTicketByAssigned:string = ApiService.serverUrl + '/restful/ticket/assigned';


    private aaa:string = ApiService.serverUrl + '/sendEmailMail';

    constructor(private http:Http) {
    }

    sendMailAssign(mail:Mail) {
        console.log("sending http POST to " + this.sendEmailAssignUrl);
        console.log("json obj: ", JSON.stringify(mail));
        return this.http.post(this.sendEmailAssignUrl, JSON.stringify(mail))
            .toPromise()
            .then(()=>mail)
            .catch((err)=>console.error(err));
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

    findByNameDescription(pageRequest:PageRequest, findName:string):Observable<any> {
        return this.http.post(this.findTicketByName + '?find=' + findName, JSON.stringify(pageRequest))
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

    findByState(pageRequest:PageRequest, findName:TicketState):Observable<any> {
        return this.http.post(this.findTicketByState + '?findName=' + findName, JSON.stringify(pageRequest))
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllTicket():Promise<ITicket[]> {
        return this.http.get(this.getUrl)
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    getAllUsers():Promise<User[]> {
        return this.http.get(this.getUsers)
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    getTicketbyId(ticketId:number):Promise<ITicket> {
        let url = `${this.getOneUrl}/${ticketId}`;
        return this.http.get(url)
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    addTicket(ticket:ITicket):Promise<ITicket> {
        return this.http.post(this.postUrl, JSON.stringify(ticket))
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    editTicket(ticket:ITicket):Promise<ITicket> {
        return this.http.put(this.putUrl, JSON.stringify(ticket))
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    deleteTicket(ticket:ITicket):Promise<ITicket> {
        let url = `${this.deleteUrl}/${ticket.ticketId}`;
        return this.http.delete(url)
            .toPromise()
            .then(res => ticket)
            .catch(this.handleError);
    }

    findAssignUser(name:string) {
        let url = `${this.getAssignUser}/${name}`;
        return this.http.get(url)
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    sendEmailAssign(ticketId:number) {
        return this.http.post(this.sendEmailAssignUrl, JSON.stringify(ticketId))
            .toPromise()
            .then(() => (ticketId))
            .catch(this.handleError);
    }

    private handleError(error:any):Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }


}