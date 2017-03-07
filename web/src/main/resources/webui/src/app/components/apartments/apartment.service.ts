import { Injectable } from '@angular/core';
import {
    Http,
    Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { API_URL } from '../../../shared/models/localhost.config';
import { LoginConstants } from '../../shared/login/login.constants';

@Injectable()

export class ApartmentService {

    public houseAllURL: string = LoginConstants.Login.serverUrl + '/restful/house/all';

    constructor(private http: Http) {}

    public getApartmentData(): Observable < any > {
        return this.http.get(`${API_URL}/restful/apartment/`)
            .map((res: Response) => res.json())
            .catch((error) => Observable.throw(error));
    }

    public getAllHouses(): Observable<any> {
     return this.http.get(this.houseAllURL)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
    }
}
