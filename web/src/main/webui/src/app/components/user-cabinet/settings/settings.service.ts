import { Injectable }    from '@angular/core';
import { Http }          from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

import { API_URL }       from '../../../../shared/models/localhost.config';
import { User }          from '../../../models/User';
import { Settings }      from './settings';
import { Observable }    from 'rxjs/Observable';
import { LoginService }  from '../../../shared/login/login.service';

@Injectable()
export class SettingsService {

    private getUrl: string = API_URL + '/restful/settings';
    private url: string = API_URL + '/restful/settings';

    constructor(private http: Http, private currentUserService: LoginService) {
    }

    public save(settings: Settings): Promise<Settings> {
        return this.http.put(this.url, JSON.stringify(settings),
               this.currentUserService.getRequestOptionArgs())
            .toPromise()
            .then( (res) => res.json())
            .catch(this.handleError);
    }

    public createForNewUser(id: number): Promise<Settings> {
        return this.http.post(this.url, JSON.stringify(id),
               this.currentUserService.getRequestOptionArgs())
            .toPromise()
            .then((res) => res.json())
            .catch(this.handleError);
    }
    public getSettingsForUser(): Promise<Settings> {
        return this.http.get(this.getUrl, this.currentUserService.getRequestOptionArgs())
            .toPromise()
            .then((res) => { return res.json(); })
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        return Promise.reject(error.message || error);
    }
}
