import { Injectable } from "@angular/core";
import { Http, Response, Headers } from "@angular/http";
import {Observable} from 'rxjs/Observable';

import "rxjs/add/operator/map";
import 'rxjs/add/operator/catch';
import "rxjs/add/operator/toPromise";

import { IOsbb } from "../../shared/models/osbb";
import { OsbbDTO } from "../../shared/models/osbbDTO";
import ApiService = require("./api.service");
import { SelectItem } from "../../shared/models/ng2-select-item.interface";
import { Region, City, Street, AddressDTO } from "../../shared/models/addressDTO";

const attachmentUploadUrl = ApiService.serverUrl + '/restful/attachment';

@Injectable()
export class AddressService { 

    private addressUrl:string = ApiService.serverUrl + '/restful/address';

    constructor(private http: Http) { 
    }

     getAllRegions(): Observable<Region[]> {
        console.log('Get all regions: ');
        let url = this.addressUrl + '/region';
        return this.http.get(url)
            .map((res:Response)=> res.json())
            .catch((error)=>Observable.throw(error)); 
     }           

     getAllCitiesOfRegion(regionID: number): Observable<City[]> {
        console.log('Get all cities: ');
        let url = this.addressUrl + '/city/' + regionID;
        return this.http.get(url)
            .map((res:Response)=> res.json())
            .catch((error)=>Observable.throw(error)); 
     }           

     getAllStreetsOfCity(cityID: number): Observable<Street[]> {
        console.log('Get all streets: ');
        let url = this.addressUrl + '/street/' + cityID;
        return this.http.get(url)
            .map((res:Response)=> res.json())
            .catch((error)=>Observable.throw(error)); 
     }           

     getStreetById(streetID: number): Observable<Street> {
        console.log('Get street: ');
        let url = this.addressUrl + '/street/id/' + streetID;
        return this.http.get(url)
            .map((res:Response)=> res.json())
            .catch((error)=>Observable.throw(error)); 
     }           
}
