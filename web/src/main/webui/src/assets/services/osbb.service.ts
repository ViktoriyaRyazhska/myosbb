import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/catch';

import { Osbb } from '../models/osbb.model';
import { OsbbDTO } from '../models/osbb-dto.model';
import { LoginService } from '../../app/shared/login/login.service';
import { Attachment } from '../models/attachment.model';
import { OsbbConstants } from './osbb.constants';

@Injectable()
export class OsbbService {

  constructor(
    private http: Http,
    private loginService: LoginService,
    private osbbConsts: OsbbConstants
    ) {}

  public getAllOsbb(): Promise<Osbb[]> {
      return this.http.get(this.osbbConsts.default.osbbUrl)
               .toPromise()
               .then( (res) => res.json())
               .catch(this.handleError);
  }

  public upload(file: File): Promise<Attachment> {
      return new Promise((resolve, reject) => {
          const formData: FormData = new FormData();
          const xhr: XMLHttpRequest = new XMLHttpRequest();
          formData.append('file', file, file.name);
          xhr.onreadystatechange = () => {
              if (xhr.readyState === 4) {
                  if (xhr.status === 200) {
                      resolve(JSON.parse(xhr.response));
                  } else {
                      reject(xhr.response);
                  }
              }
          };
          xhr.open('POST', this.osbbConsts.default.attachmentUploadUrl + '/logo', true);
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('access_token'));
          xhr.send(formData);
      });
  }

  public getByAvailable(available: boolean): Promise<Osbb[]> {
      return this.http.get(this.osbbConsts.default.osbbUrl + '/status/' + available)
               .toPromise()
               .then( (res) => res.json())
               .catch(this.handleError);
  }

  public getAllOsbbByNameContaining(osbbName: string): Promise<Osbb[]> {
      let url = this.osbbConsts.default.osbbUrl + '/search/' + osbbName;
      return this.http.get(url)
               .toPromise()
               .then( (res) => res.json())
                .catch(this.handleError);
  }

 public getAllOsbbByOrder(sortedBy: string, asc: boolean) {
      let url = this.osbbConsts.default.osbbUrl + '?sortedBy=' + sortedBy + '&&asc=' + asc;
      return this.http.get(url)
               .toPromise()
               .then( (res) => res.json())
               .catch(this.handleError);
  }

  public getDTOOsbbById(osbbId: number): Promise<OsbbDTO> {
       let url = this.osbbConsts.default.osbbUrl + '/dto/' + osbbId;
       return this.http.get(url, this.loginService.getRequestOptionArgs())
               .toPromise()
               .then( (res) => res.json())
               .catch(this.handleError);
  }

  public getOsbbById(osbbId: number): Promise<Osbb> {
       let url = this.osbbConsts.default.osbbUrl + '/' + osbbId;
       return this.http.get(url)
               .toPromise()
               .then( (res) => res.json())
               .catch(this.handleError);
  }

  public addOsbb(osbb: Osbb): Promise<Osbb> {
      return this.http.post(this.osbbConsts.default.osbbUrl, JSON.stringify(osbb))
                      .toPromise()
                      .then( (res) => res.json())
                      .catch(this.handleError);
  }

  public editOsbb(osbb: Osbb): Promise<Osbb> {
      return this.http.put(this.osbbConsts.default.osbbUrl, JSON.stringify(osbb))
                      .toPromise()
                      .then( (res) => res.json())
                      .catch(this.handleError);
  }

  public deleteOsbb(osbb: Osbb): Promise<Osbb> {
      return this.http.delete(this.osbbConsts.default.osbbUrl + '/' + osbb.osbbId)
                  .toPromise()
                  .then( (res) => osbb)
                  .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
      return Promise.reject(error.message || error);
  }
}
