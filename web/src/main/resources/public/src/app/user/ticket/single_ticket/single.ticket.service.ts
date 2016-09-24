import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import {Ticket, ITicket} from './../ticket';
import {IMessage} from './message';
import {PageRequest} from './../page.request';
import {Observable} from "rxjs/Observable";
import ApiService = require("../../../../shared/services/api.service");


@Injectable()
export class MessageService {
    private getOneUrl:string = ApiService.serverUrl + '/restful/ticket';
    private getUrlCommentsForTicket:string = ApiService.serverUrl + '/restful/message/comments';
    private putState:string = ApiService.serverUrl + '/restful/ticket/state';
    private url:string = ApiService.serverUrl + '/restful/message';
    private addAnswerUrl:string = ApiService.serverUrl + '/restful/message/answer';

    constructor(private http:Http) {
    }

    getAllMessages(ticket:number):Promise<IMessage[]> {
        let url = `${this.url}/${ticket}`;
        return this.http.get(url)
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    getMessagesForTicket(pageRequest:PageRequest, ticketId:number):Observable<any> {
        return this.http.post(this.getUrlCommentsForTicket+"?ticket="+ticketId, JSON.stringify(pageRequest))
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    addMessage(message:IMessage,ticket:number):Promise<IMessage> {
        return this.http.post(this.url +"?ticket="+ticket, JSON.stringify(message))
           .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }


    getTicketId(ticketId:number):Promise<Ticket> {
        let url = `${this.getOneUrl}/${ticketId}`;
        return this.http.get(url)
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    getTicketbyId(ticketId:number):Observable<any> {
        let url = `${this.getOneUrl}/${ticketId}`;
        return this.http.get(url)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    addAnswer(message:IMessage):Promise<IMessage> {
        let url = `${this.addAnswerUrl}`;
        return this.http.post(url, JSON.stringify(message))
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    editState(ticket:Ticket):Promise<ITicket> {
        return this.http.put(this.putState, JSON.stringify(ticket))
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    deleteMessage(messageId:number):Promise<IMessage> {
        let url = `${this.url}/${messageId}`;
        return this.http.delete(url)
            .toPromise()
            .then(res => messageId)
            .catch(this.handleError);
    }

    editMessage(message:IMessage):Promise<IMessage> {
        let url = `${this.url}`;
        return this.http.put(url, JSON.stringify(message))
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    private handleError(error:any):Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }
}