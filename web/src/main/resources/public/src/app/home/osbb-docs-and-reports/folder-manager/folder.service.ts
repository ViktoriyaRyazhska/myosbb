import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import {Observable} from 'rxjs/Observable';

import ApiService = require('../../../../shared/services/api.service');
import { Folder } from './Folder';

@Injectable()
export class FolderService {
    private folders: Folder[];
    private controllerUrl = ApiService.serverUrl + "/restful/folder";
    constructor(private http: Http) { }

    getRootFolders(): Observable<Folder[]> {
        console.log('Retrieving all folders...');
        return this.http.get(this.controllerUrl)
            .map(response => response.json())
            .catch(error => Observable.throw(error));
    }

    getRoots() {
        console.log('Retrieving all folders...');
        this.http.get(this.controllerUrl).subscribe(
            data => {
                console.log(data);
                this.folders = data.json();
                console.log(this.folders);
                console.log('Done!');
            }
        );
    }
}