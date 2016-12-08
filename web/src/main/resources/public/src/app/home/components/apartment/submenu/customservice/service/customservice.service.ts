import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import {BillDTO} from "../customservice.dto.interface";
import ApiService = require("../../../../../../../shared/services/api.service");

@Injectable()
export class CustomserviceService {

    private billsURL: string = ApiService.serverUrl + '/restful/bill';

    constructor(private _http: Http) {
    
    }

    getAllParentId(): Observable<any> {  
         return this._http.get(this.billsURL+'/parentbillid')
             .map((response)=> response.json())
             .catch((error)=>Observable.throw(error));
     }
}
