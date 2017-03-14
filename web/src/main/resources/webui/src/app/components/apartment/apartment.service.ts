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
// import { LoginConstants } from '../shared/login/login.constants';
@Injectable()

export class ApartmentService {

    public apartmentId: number;
    constructor(private http: Http, public LoginService: LoginService) { }

    public getApartment(apartmentId: number): Observable<any> {
        return this.http.get(`${API_URL}/restful/apartment/${apartmentId}`,
            this.LoginService.getRequestOptionArgs())
            .map((res: Response) => res.json())
            .catch((error) => Observable.throw(error));
    }

    public getUsers(apartmentId: number): Observable<any> {
        return this.http.get(`${API_URL}/restful/apartment/users${apartmentId}`)
            .map((res: Response) => res.json())
            .catch((error) => Observable.throw(error));
    }

    public getOwner(apartmentId: number): Observable<any> {
        return this.http.get(`${API_URL}/restful/apartment/owner/${apartmentId}`)
            .map((res: Response) => res.json())
            .catch((error) => Observable.throw(error));
    }



}
