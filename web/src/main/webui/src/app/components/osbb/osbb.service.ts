import { Injectable } from '@angular/core';
import {
  Http,
  Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { API_URL } from '../../../shared/models/localhost.config';

@Injectable()

export class OsbbService {

  constructor(private http: Http) { }

  public getOSBB(): Observable<any> {
    return this.http.get(`${API_URL}/restful/osbb`)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
  }
}
