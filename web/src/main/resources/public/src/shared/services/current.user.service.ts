import {Injectable, Component} from "@angular/core";
import {User} from "../../shared/models/User";
import {Response} from "@angular/http";
import {LoginService} from "../../app/login/login.service";
import {Http, Headers}  from "@angular/http";
import ApiService = require("../services/api.service");
import {Observable} from "rxjs/Observable";
import {Router} from '@angular/router';


@Injectable()
export class CurrentUserService {

    private getUrl:string = ApiService.serverUrl + '/restful/user';
    private _pathUrl = ApiService.serverUrl;
    private role:string = "";
    public currentUser:User

    constructor(private _loginservice:LoginService,
    private http:Http,
    private router:Router
    ) { 
        this.currentUser = new User();
        this.initUser();
        this.setRole();
    }

    setUser(user:Response) {
        this.currentUser = <User>user.json();
    }

    setUserPromise(user:User) {
        this.currentUser = user;
        return this.currentUser;
    }

    getUser():User {
        return this.currentUser;
    }

    initUser():User {
        if (this._loginservice.checkLogin()) {
            this._loginservice.sendToken().subscribe(data=> {
                this.setUser(data);
            })
        }
    }

    getRole():string {
        return this.role;
    }

    setRole() {
        if (this._loginservice.checkLogin()) {
            this.role = this.decodeAccessToken(localStorage.getItem("access_token"))["authorities"][0];
        }
    }

    public  decodeAccessToken(access_token:string) {
        return JSON.parse(window.atob(access_token.split('.')[1]));
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
        localStorage.setItem("expires_in", new Date().setMilliseconds(data.expires_in * 1000));
        localStorage.setItem("scope", data.scope);
        localStorage.setItem("jti", data.jti);
        localStorage.setItem("refresh_token", data.refresh_token);
    }

    getUserById(id:number):Observable<any> {
          let url = `${this.getUrl}/${id}`;
        return this.http.get(url)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    toUser(id:number) {
        console.log("toUser:"+id);
        
        if (id == this.currentUser.userId) {
            this.router.navigate(['home/user/main'])
        }else{

             if (this.currentUser.role == "ROLE_ADMIN") {
                this.router.navigate(['admin/friend', id]);
            }
            if (this.currentUser.role == "ROLE_MANAGER") {
                this.router.navigate(['manager/friend', id]);
            }
            else {
                this.router.navigate(['home/friend', id]);
            }
    }
}
    
}