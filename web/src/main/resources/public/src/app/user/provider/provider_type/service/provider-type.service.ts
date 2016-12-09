/**
 * Created by Anastasiia Fedorak  8/4/16.
 */
import {Injectable} from "@angular/core";
import {Http, Response, Headers, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Rx";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import {ProviderType} from "../../../../../shared/models/provider.type.interface";
import ApiService = require("../../../../../shared/services/api.service");


@Injectable()
export class ProviderTypeService {
    private url = ApiService.serverUrl + '/restful/providertype/';

    constructor(private _http: Http) {
    }

    getProviderTypes(): Observable<any> {
        return this._http.get(this.url)
            .map(res => res.json())
            .catch((err) => Observable.throw(err));
    }

    getProviderTypeById(id: number): Observable<any> {
        return this._http.get(this.url + id)
            .map(res => res.json())
            .catch((err) => Observable.throw(err))
    }


    deleteProviderTypeById(providerTypeId: number) {
        let url = this.url + providerTypeId;
        return this._http.delete(url)
            .map(res => res.json())
            .catch((err) => Observable.throw(err))
    }

    editAndSaveType(type: ProviderType) {
        return this._http.put(this.url + type, JSON.stringify(type),)
            .map(res => res.json())
            .catch((err) => Observable.throw(err))
    }

}