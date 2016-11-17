import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { Observable } from "rxjs/Observable";
import "rxjs/add/operator/map";
import { Event } from "./event.interface";
import "rxjs/add/operator/toPromise";
import moment from 'moment';
import ApiService = require("../../shared/services/api.service");

@Injectable()
export class EventService {

    private eventUrl = ApiService.serverUrl + '/restful/event/';
    private getEventPageUrl = ApiService.serverUrl + '/restful/event?pageNumber=';
    private getEventsByStatusUrl = ApiService.serverUrl + '/restful/event/status?status=';
    private getEventsByAuthorUrl = ApiService.serverUrl + '/restful/event/author';

    constructor(private _http:Http) {
    }

    getEvents() {
        return this._http.get(this.eventUrl)
            .toPromise()
            .then(res => <any[]> res.json())
            .then(data => { return data; });
    }

    getEvent(id:number): Observable<any> {
        console.log('get event by id: ' + id);
        return this._http.get(this.eventUrl + id)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllEvents(pageNumber:number):Observable<any> {
        return this._http.get(this.getEventPageUrl + pageNumber)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllEventsSorted(pageNumber:number, title:string, order:boolean):Observable<any> {
        return this._http.get(this.getEventPageUrl + pageNumber + '&&sortedBy=' + title + '&&asc=' + order)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    deleteEventById(id:number) {
        let url = this.eventUrl + id;
        console.log('delete event by id: ' + id);
        return this._http.delete(url)
            .toPromise()
            .catch((error)=>console.error(error));
    }

    deleteAllEvents() {
        console.log('delete all events');
        return this._http.delete(this.eventUrl)
            .toPromise()
            .catch((error)=>console.error(error));
    }

    editAndSave(event:Event) {
        if (event.id) {
            console.log('updating event with id: ' + event.id);
            return this.put(event);
        }
    }

    put(event:Event) {
        event.start = <Date>moment(event.start).format("YYYY-MM-DDTHH:mmZZ");
        event.end = <Date>moment(event.end).format("YYYY-MM-DDTHH:mmZZ");
        return this._http.put(this.eventUrl, JSON.stringify(event))
            .toPromise()
            .then(()=>event)
            .catch((error)=>console.error(error));
    }

    addEvent(event:Event): Promise<Event> {
        event.start = <Date>moment(event.start).format("YYYY-MM-DDTHH:mmZZ");
        event.end = <Date>moment(event.end).format("YYYY-MM-DDTHH:mmZZ");
        return this._http.post(this.eventUrl, JSON.stringify(event))
            .toPromise()
            .then(()=>event)
            .catch((error)=>console.error(error));
    }

    findEventsByNameOrAuthorOrDescription(search: string): Observable<any>{
        console.log("searching events");
        console.log("param is" + search);
        return  this._http.get(this.eventUrl + "find?title=" + search)
            .map(res => res.json())
            .catch((err)=>Observable.throw(err));
    }

    findEventsByStatus(status: string): Observable<any>{
        console.log("searching events by status - " + status);
        return  this._http.get(this.getEventsByStatusUrl + status)
            .map(res => res.json())
            .catch((err)=>Observable.throw(err));
    }

    findEventsByAuthor(): Observable<any>{
        console.log("filtering events by author");
        return  this._http.get(this.getEventsByAuthorUrl)
            .map(res => res.json())
            .catch((err)=>Observable.throw(err));
    }
}