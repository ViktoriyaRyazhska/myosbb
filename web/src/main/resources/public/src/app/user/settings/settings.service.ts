import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import ApiService = require("../../../shared/services/api.service");
import {User} from './../user';
import {Settings} from './settings';
import {Observable} from "rxjs/Observable";


@Injectable()
export class SettingsService {

    private getUrl:string = ApiService.serverUrl + '/restful/settings';
    private url:string = ApiService.serverUrl + '/restful/settings';

    constructor(private http:Http) {
    }

    save(settings:Settings):Promise<Settings> {
        return this.http.put(this.url, JSON.stringify(settings))
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

    createForNewUser(id:number):Promise<Settings> {
        return this.http.post(this.url, JSON.stringify(id))
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }
    getSettingsForUser():Promise<Settings> {
        return this.http.get(this.getUrl)
            .toPromise()
            .then(res => res.json())
            .catch(this.handleError);
    }

     private handleError(error:any):Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }

}