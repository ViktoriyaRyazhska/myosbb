import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import ApiService = require('../../../../shared/services/api.service');
import { DriveFile } from './drive-file';

declare var saveAs: any;

const ANY = 'application/octet-stream';

@Injectable()
export class GoogleDriveService {
    private controllerUrl = ApiService.serverUrl + "/restful/google-drive";
    private pending: boolean = false;
    private hasError: boolean = false;

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
        return this.http.delete(this.controllerUrl + '/delete/' + id)
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

    download(id: string, fileName: string) {        
        let xhr = new XMLHttpRequest();
        let url = this.controllerUrl + '/download/' + id;
        xhr.open('GET', url, true);
        xhr.withCredentials = false;
        xhr.responseType = 'blob';
        console.log('Preparing download...');

        xhr.onreadystatechange = function () {
            if (xhr.readyState != 4) return;

            clearTimeout(serverTimeout);

            if (xhr.status === 200) {
                let mimeType = 'image/jpeg';
                var blob = new Blob([this.response], {type: mimeType});
                saveAs(blob, fileName);
            } else {
                console.error('Error while loading resources');
            }

        };

        let serverTimeout: number = setTimeout(()=> {
            console.log('terminating server connection d/t too long connection time');
            xhr.abort();
        }, 15000);

        xhr.send();
    }

}
