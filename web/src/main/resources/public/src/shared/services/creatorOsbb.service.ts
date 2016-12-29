import { Injectable } from "@angular/core";
import { Http, Response, Headers } from "@angular/http";
import {Observable} from 'rxjs/Observable';
import ApiService = require("./api.service");
import "rxjs/add/operator/map";
import 'rxjs/add/operator/catch';
import "rxjs/add/operator/toPromise";

@Injectable()
export class CreatorOsbbService {

    constructor(private http: Http) { }

    private creatorURL: string = ApiService.serverUrl +'/restful/creator/osbb/';

    getCreatorOsbb(osbbId: number): Observable<any> {
        let url = this.creatorURL+osbbId;
        return this.http.get(url)
            .map((res:Response)=> res.json())
            .catch((error)=>Observable.throw(error)); 
     }     
}
