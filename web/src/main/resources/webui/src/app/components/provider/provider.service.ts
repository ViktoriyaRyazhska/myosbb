import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { API_URL } from '../../../shared/models/localhost.config';
import { Provider } from "../../models/provider.interface";



@Injectable()
export class ProviderService {

    constructor(private http: Http) {}

    public getAllProviders(): Observable < any > {
        return this.http.get(`${API_URL}/restful/provider/`)
            .map((res: Response) => res.json())
            .catch((error) => Observable.throw(error));
    }

    public getProvidersByOsbb(osbbId):Observable <any> {
        return this.http.get(`${API_URL}/restful/osbb/`+osbbId+`/providers`)
            .map((res: Response) => res.json())
            .catch((error) => Observable.throw(error));
    }

    public getAllProviderTypes () : Observable < any > {
        return this.http.get(`${API_URL}/restful/providertype/`)
            .map((res: Response) => res.json())
            .catch((error) => Observable.throw(error));
    }

    public addProvider(provider: Provider) {
        console.log("sending http POST" );
        console.log("saving ", provider);
        provider.active = true;
        let options = new RequestOptions({headers: new Headers({'Content-Type': 'application/json'})});
        return this.http.post(`${API_URL}/restful/provider/`,provider, options)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
    }

    public editProvider(provider: Provider,providerId) {
        console.log("Editing ", provider);
        let options = new RequestOptions({headers: new Headers({'Content-Type': 'application/json'})});
        return this.http.put(`${API_URL}/restful/provider/${providerId}`,provider, options)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
    }
    
    public deleteProvider(providerId){
        console.log(providerId);
        return this.http.delete(`${API_URL}/restful/provider/${providerId}`)
            .map((res) => res)
            .catch((error) => Observable.throw(error));
    }

     public addProviderToOsbb(provider:Provider,osbbId) {
       console.log("sending http POST" );
        console.log("Adding ", provider.description );
        let options = new RequestOptions({headers: new Headers({'Content-Type': 'application/json'})});
        return this.http.post(`${API_URL}/restful/osbb/provider/${osbbId}`,provider, options)
      .map((res: Response) => res.json())
      .catch((error) => Observable.throw(error));
     
  }
}