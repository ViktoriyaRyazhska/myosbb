import {Injectable} from "@angular/core";
import {Http,Headers} from "@angular/http";
import {Osbb} from "../../../shared/models/osbb";
import {House} from "../../../shared/models/house";
import {IApartment} from "../../../shared/models/apartment.interface";
import {Observable} from 'rxjs/Observable';
import ApiService = require("../../../shared/services/api.service");
import "rxjs/add/operator/toPromise";
@Injectable()
export class JoinOsbbService{
    private osbbURL=ApiService.serverUrl + '/restful/osbb';
    public houseURL: string = ApiService.serverUrl + '/restful/house/all';
    public apartmentURL: string = ApiService.serverUrl + '/restful/apartment/';
    
    constructor(private http:Http){
    }

    getAllOsbb(): Observable<any> {
        return this.http.get(this.osbbURL)
                .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllHouses(): Observable<any> {
        return this.http.get(this.houseURL)
                .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    getAllApartments(): Observable<any> {
        return this.http.get(this.apartmentURL)
                .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    
    private handleError(error: any):Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }


}
