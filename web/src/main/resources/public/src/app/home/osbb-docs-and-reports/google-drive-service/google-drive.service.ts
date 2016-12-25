import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import ApiService = require('../../../../shared/services/api.service');
import { DriveFile } from './drive-file';

@Injectable()
export class GoogleDriveService {
    private controllerUrl = ApiService.serverUrl + "/restful/google-drive";

    constructor(private http: Http) { }

    createFolder(name: string, parentId: string): Observable<DriveFile> {
        if (!parentId) {
            parentId = "root";
        }

        return this.http.post(this.controllerUrl + '/create/' + parentId, name)
            .map(response => response.json())
            .catch(error => Observable.throw(error));
    }

    delete(id: string): Observable<string> {
        return this.http.put(this.controllerUrl + '/delete', id)
            .catch(error => Observable.throw(error));
    }

    getFile(id: string): Observable<DriveFile> {
        return this.http.get(this.controllerUrl + '/' + id)
            .map(response => response.json())
            .catch(error => Observable.throw(error));
    }

    getFilesByParent(id: string): Observable<DriveFile[]> {
        return this.http.get(this.controllerUrl + '/parent/' + id)
            .map(response => response.json())
            .catch(error => Observable.throw(error));
    }

    update(id: string, name: string): Observable<DriveFile> {
        console.log(id);
        console.log(name);
        return this.http.put(this.controllerUrl + '/update/' + id, name)
            .map(response => response.json())
            .catch(error => Observable.throw(error));
    }

}
