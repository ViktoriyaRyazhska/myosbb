import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { API_URL } from '../../../shared/models/localhost.config';
import { LoginConstants } from '../../shared/login/login.constants';
import { UserApartment } from '../../models/userWithApartment.model';

@Injectable()

export class ApartmentService {
    public _pathUrlForUserWithApartment = LoginConstants.Login.serverUrl + '/restful/house/withUser/';
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

    public registerApartmentWithUser(apartmentWithUser: UserApartment, houseId : number): Observable<any> {
    let options = new RequestOptions({headers: new Headers({'Content-Type': 'application/json'})});
    return this.http.post(this._pathUrlForUserWithApartment + houseId, apartmentWithUser, options)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
}
