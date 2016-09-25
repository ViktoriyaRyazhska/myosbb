import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";

import {Vote} from "./vote";
import ApiService = require("../../../shared/services/api.service");


@Injectable()
export class VoteService { 

    private url:string = ApiService.serverUrl +'/restful/vote';

    constructor(private http: Http) {
     }

    getAllVotes(osbbId: number): Promise<Vote[]> {
        return this.http.get(this.url + '/all/' + osbbId)
                 .toPromise()
                 .then(res => res.json())
                 .catch(this.handleError);
    }

    addVote(vote:Vote): Promise<Vote> {
        return this.http.post(this.url, JSON.stringify(vote))
                        .toPromise()
                        .then(res => res.json())
                        .catch(this.handleError);
    }

    deleteVote(vote:Vote): Promise<Vote> {
        let url = this.url + '/' + vote.voteId;
        return this.http.delete(url)
                    .toPromise()
                    .then(res => vote)
                    .catch(this.handleError);
    }

    closeVote(voteId: number):Promise<Vote>  {
        return this.http.get(this.url + '/close/' + voteId)
                        .toPromise()
                        .then(res => res.json())
                        .catch(this.handleError);
    }

    private handleError(error: any):Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }
}