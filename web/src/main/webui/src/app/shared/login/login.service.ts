import { Injectable } from '@angular/core';
import { Http, Response, Headers,
 RequestOptions, RequestOptionsArgs } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { LoginConstants } from './login.constants';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../../assets/models/user.model';
import { API_URL } from '../../../assets/models/localhost.config.ts';

@Injectable()
export class LoginService {
  public _pathUrl = LoginConstants.Login;
  public tokenName: string = 'access_token';
  public role: string;
  public isLoggedIn: boolean;
  public currentUser: User;
  private getUrl: string = API_URL + '/restful/user';

  constructor(public http: Http, public _router: Router,
    ) {}
  public sendCredentials(model) {
      let options = this.getRequestOptionArgs();
      let tokenUrl = this._pathUrl.serverUrl + '/oauth/token';
      let data = 'username=' + encodeURIComponent(model.username)
       + '&password=' + encodeURIComponent(model.password) + '&grant_type=password';
      return this.http.post(tokenUrl, data, options);
  }
  public sendToken(): Observable<any> {
      let options = this.getRequestOptionArgs();
      let userUrl = this._pathUrl.serverUrl + '/restful/user/getCurrent';
      return this.http.get(userUrl, options);
  }
  public checkLogin(): boolean {
    return  !!localStorage.getItem(this.tokenName);
   }
  public getRequestOptionArgs(options?: RequestOptionsArgs, url?: string): RequestOptionsArgs {
    if (!options) {
        options = new RequestOptions();
        options.headers = new Headers();
    }
    if (!!localStorage.getItem(this.tokenName)) {
            options.headers.append(this._pathUrl.auth, this._pathUrl.bearer
             + localStorage.getItem(this.tokenName));
            options.headers.append(this._pathUrl.contentType, this._pathUrl.appJson);
    } else {
        options.headers.append(this._pathUrl.auth, this._pathUrl.basicToken);
        options.headers.append(this._pathUrl.contentType, this._pathUrl.appXwfu);
        options.headers.append(this._pathUrl.accept, this._pathUrl.appJson);
    }
    return options;
  }
  public  decodeAccessToken( accessToken: string) {
    return JSON.parse(window.atob(accessToken.split('.')[1]));
   }

  public onSubmit(model) {
    this.sendCredentials(model)
      .subscribe((data) => {
        this.tokenParseInLocalStorage(data.json());
        if (this.checkLogin()) {
          this.sendToken().subscribe((subData) => {
            this.setRole();
            let user: any = subData.json();
            localStorage.setItem('user', user.userId);
            this.currentUser = user;
            this.switchRole(this.getRole());
          });
        };
      }
    );
  }
  public switchRole(data) {
    switch (data) {
      case 'ROLE_USER':
        this._router.navigate(['./user']);
        break;
      case 'ROLE_ADMIN':
        this._router.navigate(['./admin']);
        break;
      case 'ROLE_MANAGER':
        this._router.navigate(['./manager']);
        break;
      default :
        this._router.navigate(['./login']);
        break;
    }
  }
  public tokenParseInLocalStorage(data: any) {
    localStorage.setItem('access_token', data.access_token);
    localStorage.setItem('token_type', data.token_type);
    localStorage.setItem('scope', data.scope);
    localStorage.setItem('jti', data.jti);
    localStorage.setItem('refresh_token', data.refresh_token);
  }

  public setUser() {
    this.sendToken().subscribe( (data) => this.currentUser = data.json());
  }

  public getUser(): User {
    return this.currentUser;
  }

  public getRole(): string {
    return this.role;
  }

  public getRoleFromLocalStorage(): string {
    return this.decodeAccessToken(localStorage.getItem(this.tokenName))['authorities'][0];
  }

  public setRole() {
    if (this.checkLogin()) {
      this.role = this.getRoleFromLocalStorage();
    }
  }

  public setData() {
    if (this.checkLogin()) {
      this.setRole();
      this.setUser();
    }
  }
}
