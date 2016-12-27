import {Injectable} from "@angular/core";
import {HTTP_PROVIDERS, Http, Headers, Response, RequestOptions} from "@angular/http";
import {Observable} from 'rxjs/Observable';
import ApiService = require("../../../shared/services/api.service");
import RegistrationComponent = require("./registration.component");
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {UserRegistration} from "../../../shared/models/user_registration";
import {OsbbRegistration} from "../../../shared/models/osbb_registration";
@Injectable()
export class RegisterService {

    public _pathUrlForOsbb = ApiService.serverUrl + '/registration/osbb';
    public _pathUrlForUser = ApiService.serverUrl + '/registration/';
    private osbbURL = ApiService.serverUrl + '/restful/osbb';
    public houseAllURL: string = ApiService.serverUrl + '/restful/house/all';
    public houseURL: string = ApiService.serverUrl + '/restful/house';
    public apartmentURL: string = ApiService.serverUrl + '/restful/apartment/';

    constructor(private http: Http) {

    }

    registerOsbb(osbb: OsbbRegistration): Observable<any> {
        let headers = new Headers({'Content-Type': 'application/json'});
        let options = new RequestOptions({headers: headers});
        return this.http.post(this._pathUrlForOsbb, osbb, options)
            .map((res: Response) => res.json())
            .catch((error)=>Observable.throw(error));
    }

    registerUser(user: UserRegistration): Observable<any> {
        let headers = new Headers({'Content-Type': 'application/json'});
        let options = new RequestOptions({headers: headers});
        return this.http.post(this._pathUrlForUser, user, options)
            .map((res: Response) => res.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllOsbb(): Observable<any> {
        return this.http.get(this.osbbURL)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllHouses(): Observable<any> {
        return this.http.get(this.houseAllURL)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllHousesByStreet(id: number): Observable<any> {
        return this.http.get(this.houseURL + '/street/' + id)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error))
    }

    getHouseByNumberHouseAndStreetId(numberHouse: number, streetId: number): Observable<any> {
        return this.http.get(this.houseURL + '/numberHouse/'+numberHouse+'/street/'+streetId)
            .map((response)=>response.json())
            .catch((error) => Observable.throw(error))
    }

     getAllHousesByOsbb(id: number): Observable<any> {
        return this.http.get(this.houseAllURL + '/' + id)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error))
    }

    getAllApartments(): Observable<any> {
        return this.http.get(this.apartmentURL)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllApartmentsByHouse(houseId: number): Observable<any> {
        return this.http.get(this.houseURL + '/' + houseId + '/apartments')
            .map((response)=>response.json())
            .catch((error) => Observable.throw(error))
    }
}
