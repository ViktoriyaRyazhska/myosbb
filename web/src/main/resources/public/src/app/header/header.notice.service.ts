import { Injectable } from "@angular/core";
import { Http, Headers } from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import ApiService = require("../../shared/services/api.service");
import { User } from './../user/user';
import { Notice } from './notice';
import { Observable } from "rxjs/Observable";
@Injectable()
export class NoticeService {

    private getUrl:string = ApiService.serverUrl + '/restful/notice';
    private deleteUrl:string = ApiService.serverUrl + '/restful/notice';

    constructor(private http:Http) {
    }

    getNotice():Observable<any> {
        return this.http.get(this.getUrl)
            .map((response)=> response.json())
            .catch((error)=>Observable.throw(error));
    }

    deleteNotice(notice:Notice):Promise<Notice> {
        let url = `${this.deleteUrl}/${notice.noticeId}`;
        return this.http.delete(url)
            .toPromise()
            .then(res => notice)
            .catch(this.handleError);
    }

    private handleError(error:any):Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }

}