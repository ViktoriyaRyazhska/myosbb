import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { UserRegistration } from './models/user_registration';
import { OsbbRegistration } from './models/osbb_registration';
import { RegistrationConstants } from './registration.constant';
import { LoginConstants } from '../shared/login/login.constants';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
@Injectable()
export class RegisterService {
  public _pathUrlForUser = LoginConstants.Login.serverUrl + '/registration/';
  public _pathUrlForOsbb = LoginConstants.Login.serverUrl + '/registration/osbb';
  public houseAllURL: string = LoginConstants.Login.serverUrl + 'restful/house/all';
  public houseURL: string = LoginConstants.Login.serverUrl + '/restful/house';
  public apartmentURL: string = LoginConstants.Login.serverUrl + '/restful/apartment/';
  private creatorURL: string = LoginConstants.Login.serverUrl + '/restful/creator/osbb/';
  private osbbURL = LoginConstants.Login.serverUrl + '/restful/osbb';

  constructor(private http: Http) {}

  public registerOsbb(osbb: OsbbRegistration): Observable<any> {
    let options = new RequestOptions({headers: new Headers({'Content-Type': 'application/json'})});
    return this.http.post(this._pathUrlForOsbb, osbb, options)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
  public registerUser(user: UserRegistration): Observable<any> {
    let options = new RequestOptions({headers: new Headers({'Content-Type': 'application/json'})});
    return this.http.post(this._pathUrlForUser, user, options)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
  public getAllOsbb(): Observable<any> {
    return this.http.get(this.osbbURL)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }
  public getAllHouses(): Observable<any> {
    return this.http.get(this.houseAllURL)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }
  public getAllHousesByStreet(id: number): Observable<any> {
    return this.http.get(this.houseURL + '/street/' + id)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }
  public getHouseByNumberHouseAndStreetId(numberHouse: number, streetId: number): Observable<any> {
    return this.http.get(this.houseURL + '/numberHouse/' + numberHouse + '/street/' + streetId)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }
  public getAllHousesByOsbb(id: number): Observable<any> {
    return this.http.get(this.houseAllURL + '/' + id)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }
  public getAllApartments(): Observable<any> {
    return this.http.get(this.apartmentURL)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }
  public getAllApartmentsByHouse(houseId: number): Observable<any> {
    return this.http.get(this.houseURL + '/' + houseId + '/apartments')
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }
  public getCreatorOsbb(osbbId: number): Observable<any> {
    let url = this.creatorURL + osbbId;
    return this.http.get(url)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
}
