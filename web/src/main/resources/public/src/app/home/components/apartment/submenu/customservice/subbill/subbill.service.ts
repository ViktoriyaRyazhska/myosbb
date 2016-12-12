import { Http, Headers, RequestOptions } from '@angular/http';
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {BillDTO} from "../customservice.dto.interface";
import ApiService = require("../../../../../../../shared/services/api.service");

@Injectable()
export class subbillService {

    private billsURL: string = ApiService.serverUrl + '/restful/bill';

    constructor(private http: Http) { }

    getCurrentBill(id: number): Observable<any> {
        return this.http.get(this.billsURL + '/parentbillid/subbill/' + id)
            .map((response) => response.json());
    }
    getAllParentId(): Observable<any> {
        return this.http.get(this.billsURL + '/parentbillid')
            .map((response) => response.json())
            .catch((error) => Observable.throw(error));
    }
}
