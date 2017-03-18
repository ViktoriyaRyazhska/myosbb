import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { API_URL } from '../../../shared/models/localhost.config';
import { LoginService } from '../../shared/login/login.service';
import { Utility } from '../../models/utility.model';

@Injectable()
export class UtilityService {

    private osbbId: number;
    private userId: number;
    constructor(private http: Http, private LoginService: LoginService) {
        this.osbbId = this.LoginService.currentUser.osbbId;
        this.userId = this.LoginService.getUser().userId;
     }

     public getUtilitiesForUser(): Observable<any> {
        return this.http.get(`${API_URL}/restful/utility/user/${this.userId}`)
            .map((response) => response.json())
            .catch((error) => Observable.throw(error));
    }

    public listAllUtilities(): Observable<any> {
        return this.http.get(`${API_URL}/restful/utility/osbb/${this.osbbId}`)
            .map((response) => response.json())
            .catch((error) => Observable.throw(error));
    }
    public listAllOsbbs(): Observable<any> {
        return this.http.get(`${API_URL}/restful/osbb`)
            .map((response) => response.json())
            .catch((error) => Observable.throw(error));
    }
    public listAllHouses(): Observable<any> {
        return this.http.get(`${API_URL}/restful/house/all/${this.osbbId}`)
            .map((response) => response.json())
            .catch((error) => Observable.throw(error));
    }

    saveUtility(utility: any): Observable<any> {
        let options = new RequestOptions({ headers: new Headers({ 'Content-Type': 'application/json' }) });
        return this.http.post(`${API_URL}/restful/utility`, JSON.stringify(utility), options)
            .map((response) => response.json())
            .catch((error) => Observable.throw(error));
    }

    deleteUtility(utility: Utility){
        console.log(utility);
        return this.http.delete(`${API_URL}/restful/utility/${utility.utilityId}`)
            .map((res) => res)
            .catch((error) => Observable.throw(error));
    }

    private handleError(error: any): Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }
}
