import { Injectable } from '@angular/core'
import { User } from "../../../../shared/models/User";
import { UserRegistration } from '../../../../shared/models/user_registration';
import { HTTP_PROVIDERS, Http,Headers,Response, RequestOptions } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/catch';
import ApiService = require("../../../../shared/services/api.service");

@Injectable()
export class UsersService { 
    
    private _pathUrl = ApiService.serverUrl + '/restful/user/';
    private _pathUrlForUser = ApiService.serverUrl + '/registration/';
    private _getUrl:string = ApiService.serverUrl + '/restful/role/';
    
    constructor( private http:Http){
    }

    getAllUsers():Observable<any>{
        console.log('in service');
        return this.http.get(this._pathUrl)
            .map(response => response.json());
    }

    updateUser(user:User):Observable<User>{
        return this.http.post(this._pathUrl+user.userId,JSON.stringify(user))
            .map((res:Response) => {return new User(res.json())});
    }

    deleteUser(user:User){
        console.log(user);
        return this.http.delete(this._pathUrl+user.userId).map(response => response.json());
    }

    saveUser(user:UserRegistration):Observable<UserRegistration>{
        return this.http.post(this._pathUrlForUser,JSON.stringify(user))
            .map((res:Response) => res.json());
    }

   changeActivation(user:User):Observable<User>{
        return this.http.post(this._pathUrl+user.userId+"/changeActivation");
    }

   listAllRoles():Observable<any> {
        console.log('Get all role');
        return this.http.get(this._getUrl)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));  
    }
}
