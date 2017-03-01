import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ApiService } from '../shared/login/api.service';
import { RegistrationConstants } from './registration.constant';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class AddressService {
  public addresUrl = RegistrationConstants.Masks;

  constructor(private http: Http) {
  }

  public getAllRegions(): Observable<any[]> {
    const url = this.addresUrl.allRegions;
    return this.http.get(url)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
  public getAllCitiesOfRegion(regionID: number): Observable<any[]> {
    const url = this.addresUrl.allCities + regionID;
    return this.http.get(url)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
  public getAllStreetsOfCity(cityID: number): Observable<any[]> {
    const url = this.addresUrl.allStreets + cityID;
    return this.http.get(url)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
  public getStreetById(streetID: number): Observable<any> {
    const url = this.addresUrl.streetById + streetID;
    return this.http.get(url)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
  public getAllDistrictsOfCity(cityID: number): Observable<any[]> {
    const url = this.addresUrl.allDistricts + cityID;
    return this.http.get(url)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
  public getDistrictById(districtID: number): Observable<any> {
    const url = this.addresUrl.districtById + districtID;
    return this.http.get(url)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
}
