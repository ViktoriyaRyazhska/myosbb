import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {User} from '../../../shared/models/User';
import ApiService = require("../../../shared/services/api.service");
@Injectable()
export class ProfileService{

    private url = ApiService.serverUrl + '/restful/user/';
    private updateUrl:string;
    constructor(private http:Http){
    }
    updateUser(user:User){
        this.updateUrl=this.url + user.userId;
        console.log('Updating user with id: ' + user.userId);
        console.log("sending http PUT to " +this.updateUrl);    
        console.log("json obj: " + JSON.stringify(user));
        return this.http.put(this.updateUrl,user);
    }

}