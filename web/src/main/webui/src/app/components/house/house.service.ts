import { Injectable } from '@angular/core';
import {
    Http,
    Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { LoginService } from '../../shared/login/login.service';
import { API_URL } from '../../../shared/models/localhost.config';

@Injectable()

export class HouseService {
    public houseId: number;
    constructor(private http: Http, public LoginService: LoginService) {}
    public getHouse(houseId: number): Observable < any > {
        return this.http.get(`${API_URL}/restful/house/${houseId}`,
            this.LoginService.getRequestOptionArgs())
            .map((res: Response) => res.json())
            .catch((error) => Observable.throw(error));
    }
}
