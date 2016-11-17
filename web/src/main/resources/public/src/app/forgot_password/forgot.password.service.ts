import { Injectable } from '@angular/core';
import { Http, Response, Headers } from "@angular/http";
import { Observable } from "rxjs/Observable";
import { User } from "../../shared/models/User";
import ApiService = require("../../shared/services/api.service");

@Injectable()
export class ForgotPasswordService{
    private _pathUrl = ApiService.serverUrl;

    constructor(private http:Http) {
    }

    validateKey(data){
        let validate=this._pathUrl+"/validateForgotPasswordKey";
        let headers=new Headers({'Content-Type':'application/json'});
        return this.http.post(validate, data,{headers:headers});
    }
    
    updatePassword(id:string,password){
        let url=this._pathUrl+"/restful/user/"+id+"/password";
        let headers=new Headers({'Content-Type':'application/json'});
        return this.http.post(url, password,{headers:headers});
    }

}