/**
 * Created by Anastasiia Fedorak  8/13/16.
 */
import {Injectable} from "@angular/core";
import ApiService = require("../services/api.service");
import {HTTP_PROVIDERS, Http, Headers, Response, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Rx";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import {Mail} from "../models/mail";

@Injectable()
export class MailService {
    private url = ApiService.serverUrl + '/restful/mail';

    constructor(private http:Http){
    }

    sendEmail(mail:Mail): Observable<any> {
        console.log('Send mail '+mail );
        let headers=new Headers({'Content-Type':'application/json'});
        const body = JSON.stringify(mail);
            return this.http.post(this.url, body, {headers: headers})
            .map((response)=>response.json())
            .catch((error) => Observable.throw(error))     
    }
}
