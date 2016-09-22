import {Injectable} from "@angular/core";
import {HTTP_PROVIDERS, Http, Headers, Response, RequestOptions} from "@angular/http";
import {User} from "../../../shared/models/User";
import {Osbb} from "../../../shared/models/osbb";
import {Observable} from 'rxjs/Observable';
import ApiService = require("../../../shared/services/api.service");
import RegistrationComponent = require("./registration.component");
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
@Injectable()
export class RegisterService {

    public _pathUrlForOsbb = ApiService.serverUrl + '/registration/osbb';
    public _pathUrlForUser = ApiService.serverUrl + '/registration/';

    constructor(private http: Http) {
        
    }

    addOSBB(osbb: Osbb): Observable<any> {
        let headers = new Headers({ 'Content-Type': 'application/json' }); 
        let options = new RequestOptions({ headers: headers });
        return  this.http.post(this._pathUrlForOsbb,osbb, options).map((res: Response) => res.json());
    }
     addUser(user: User): Observable<any> {
        let headers = new Headers({ 'Content-Type': 'application/json' }); 
        let options = new RequestOptions({ headers: headers });
        return  this.http.post(this._pathUrlForUser,user, options).map((res: Response) => res.json());
    }

}












 
 // sendOsbb(osbb: Osbb) {
    //     console.log(osbb);
    //     let headers = new Headers({ 'Content-Type': 'application/json' });
    //     return this.http.post(this._pathUrl1, JSON.stringify(osbb), { headers: headers });
    // }

    // sendUser(user:User){
    //     console.log(user);
    //     let headers=new Headers({'Content-Type':'application/json'});
    // return this.http.put(this._pathUrl2,JSON.stringify(user),{headers:headers});
    //}

    // sendU(user: User){
    //     console.log()
    //     return this.userId;
    // }