import {Injectable,Component} from "@angular/core";
import {User} from "../../shared/models/User";
import {Response} from "@angular/http";
import {LoginService} from "../../app/login/login.service";
import {Http,Headers}  from "@angular/http";
import ApiService = require("../services/api.service");


@Injectable()
export class CurrentUserService {


    private _pathUrl=ApiService.serverUrl ;

    private currentUser: User

    constructor(private _loginservice: LoginService) {
        this.currentUser = new User();
        this.getUser();        
    }

    setUser(user: Response) {
        this.currentUser = <User>user.json();
    }
    
    setUserPromise(user: User) {
        this.currentUser = user;
        return this.currentUser;
    }

    getUser(): User {
        if ((this.currentUser.firstName.length == 0) && (this._loginservice.checkLogin())) {
            this._loginservice.sendToken().subscribe(
                data => this.setUser(data));
        }
        return this.currentUser;
    }
    refreshToken() {
        console.log("Refreshing token");
        var url = this._pathUrl + "/oauth/token";
        var headers = new Headers();
        headers.append('Authorization', `Basic Y2xpZW50YXBwOjEyMzQ1Ng==`);
        headers.append('Accept', `application/json`);
        headers.append('Content-Type', `application/x-www-form-urlencoded`);
        var data = 'grant_type=refresh_token&refresh_token=' + encodeURIComponent(localStorage.getItem('refresh_token'));
        console.log("Headers have been formed");
        this.http.post(url, data, {headers: headers}).subscribe(
            data => {
                this.tokenParseInLocalStorage(data.json());
             
            },
            err => {
                console.log(err);
            }
        );
    }
    tokenParseInLocalStorage(data:any) {
        localStorage.setItem("access_token", data.access_token);
        localStorage.setItem("token_type", data.token_type);
        localStorage.setItem("expires_in", new Date().setMilliseconds(data.expires_in*1000));
        localStorage.setItem("scope", data.scope);
        localStorage.setItem("jti", data.jti);
        localStorage.setItem("refresh_token", data.refresh_token);
    }

}