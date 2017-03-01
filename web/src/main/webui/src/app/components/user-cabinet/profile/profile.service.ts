import { Injectable }   from '@angular/core';
import { Http }         from '@angular/http';
import { User }         from '../../../models/user.model';
import { API_URL }      from '../../../../shared/models/localhost.config';
import { LoginService } from '../../../shared/login/login.service';
@Injectable()
export class ProfileService {

    private url = API_URL + '/restful/user/';
    private updateUrl: string;
    constructor(private http: Http, private loginUserService: LoginService) {
    }

    public updateUser(user: User) {
        this.updateUrl = this.url + user.userId;
        return this.http.put(this.updateUrl, user, this.loginUserService.getRequestOptionArgs());
    }
}
