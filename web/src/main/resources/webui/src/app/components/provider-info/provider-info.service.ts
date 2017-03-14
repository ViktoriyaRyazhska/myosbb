import { Injectable } from '@angular/core';
import {Http,Response} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { LoginService } from '../../shared/login/login.service';
import { API_URL } from '../../../shared/models/localhost.config';

@Injectable()

export class ProviderInfoService {

    constructor(private http: Http, public LoginService: LoginService) {}
    public getProvider(providerId: number): Observable<any> {
        return this.http.get(`${API_URL}/restful/provider/${providerId}`,
            this.LoginService.getRequestOptionArgs())
            .map((res: Response) => res.json())
            .catch((error) => Observable.throw(error));
    }
}