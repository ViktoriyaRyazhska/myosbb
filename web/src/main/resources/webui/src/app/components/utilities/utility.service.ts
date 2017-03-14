import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { API_URL } from '../../../shared/models/localhost.config';
import { LoginService } from '../../shared/login/login.service';

@Injectable()

export class UtilityService {
    private userId = this.login.getUser().userId;

    constructor(
        private http: Http,
        private login: LoginService
    ) { }

    public getUtilitiesForUser(): Observable<any> {
        return this.http.get(`${API_URL}/restful/utility/user/${this.userId}`)
            .map((response) => response.json())
            .catch((error) => Observable.throw(error));
    }
}
