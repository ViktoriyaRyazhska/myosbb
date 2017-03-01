import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { OsbbDocumentsAndReportsConstants } from '../osbb-docs-and-reports.constants';
import { DriveFile } from './drive-file.model';
import { saveAs } from 'save-as';

@Injectable()
export class GoogleDriveService {
  private pending: boolean = false;
  private hasError: boolean = false;

  constructor(
    private http: Http,
    private docsConsts: OsbbDocumentsAndReportsConstants
  ) {}

  public createFolder(name: string, parentId: string = 'root'): Observable<DriveFile> {
    return this.http.post(this.docsConsts.default.ctrlUrl + '/create/' + parentId, name)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }

  public delete(id: string): Observable<string> {
    return this.http.delete(this.docsConsts.default.ctrlUrl + '/delete/' + id)
      .catch((error) => Observable.throw(error));
  }

  public getFile(id: string): Observable<DriveFile> {
    return this.http.get(this.docsConsts.default.ctrlUrl + '/' + id)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }

  public getFilesByParent(id: string): Observable<DriveFile[]> {
    return this.http.get(this.docsConsts.default.ctrlUrl + '/parent/' + id)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }

  public update(id: string, name: string): Observable<DriveFile> {
    return this.http.put(this.docsConsts.default.ctrlUrl + '/update/' + id, name)
      .map((response) => response.json())
      .catch((error) => Observable.throw(error));
  }

  public download(id: string, fileName: string) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', this.docsConsts.default.ctrlUrl + '/download/' + id, true);
    xhr.withCredentials = false;
    xhr.responseType = 'blob';
    xhr.onreadystatechange = () => {
      if (xhr.readyState !== 4) { return; };
      if (xhr.status === 200) { saveAs(xhr.response, fileName); };
    };
    xhr.send();
  }
}
