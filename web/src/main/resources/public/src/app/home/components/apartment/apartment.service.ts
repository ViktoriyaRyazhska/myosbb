import {Http, Headers,RequestOptions} from '@angular/http';
import {Injectable} from "@angular/core";
import {Observable} from 'rxjs/Observable';
import {Apartment} from '../../../../shared/models/apartment.interface';
import 'rxjs/add/operator/map';
import ApiService = require("../../../../shared/services/api.service");

@Injectable ()
export class ApartmentService{
    
    constructor (private http:Http){}
    
    getAllApartments(pageNumber:number):Observable<any>{
        let headers = new Headers({'Authorization': 'Bearer '+localStorage.getItem('token')});
        headers.append('Content-Type', 'application/json');
        return this.http.get(ApiService.serverUrl + "/restful/apartment?pageNumber="+pageNumber)
            .map(response => response.json());
            }



    getSortedApartments(pageNumber:number, name:string, order:boolean,osbbId:number,role:String):Observable<any> {
        let headers = new Headers({'Authorization': 'Bearer ' + localStorage.getItem('token')});
        headers.append('Content-Type', 'application/json');

        return this.http.get(ApiService.serverUrl + "/restful/apartment?pageNumber=" + pageNumber + '&&sortedBy=' + name + '&&asc=' + order+'&&osbbId='+osbbId+'&&role='+role)
            .map((res)=> res.json())
            .catch((err)=>Observable.throw(err));
    }

    getSortedApartmentsWithNumber(pageNumber:number, name:string, order:boolean,number:number,osbbId:number,role:String):Observable<any> {
        let headers = new Headers({'Authorization': 'Bearer ' + localStorage.getItem('token')});
        headers.append('Content-Type', 'application/json');

        return this.http.get(ApiService.serverUrl + "/restful/apartment?pageNumber=" + pageNumber + '&&sortedBy=' + name + '&&asc=' + order+'&&number='+number+'&&osbbId='+osbbId+'&&role='+role)
            .map((res)=> res.json())
            .catch((err)=>Observable.throw(err));
    }

    addApartment (am:ApartmentModel,houseId:number):Observable<any>{
        let body =JSON.stringify(am);

        console.log("add model...." + body);
        return this.http.post(ApiService.serverUrl + '/restful/house/'+houseId, body )
            .map(res=>res.json());
        
    }
    deleteApartment(am:Apartment):Observable<any>{
      
        let url = ApiService.serverUrl + '/restful/apartment/' + am.apartmentId;
        console.log("deleted item" + am);
       return  this.http.delete(url)
           .map(res=>res.json());

    }

    editAndSave(am:Apartment):Observable<any> {
        return this.http.put(ApiService.serverUrl + '/restful/apartment', JSON.stringify(am))
            .map(res=>res.json());
    }



    getAllUsersInApartment(id:number){
        let headers = new Headers({'Authorization': 'Bearer '+localStorage.getItem('token')});
        headers.append('Content-Type', 'application/json');
        return this.http.get(ApiService.serverUrl + "/restful/apartment/users"+id)
            .map(response => response.json());
    }

    isApartmentExist(apartmentNumber:number,houseId:number):boolean{

        return this.http.get(ApiService.serverUrl + "/restful/house/"+houseId+"/"+apartmentNumber)
            .map(response => response.json());
    }



    getAllHouses(osbbId:number):Observable<any>{
        return this.http.get(ApiService.serverUrl+'/restful/house/all/'+osbbId).
        map(res=>res.json());

    }

    getCurrentOsbbId(userId:number):Observable<any>{
        return this.http.get(ApiService.serverUrl +"/restful/user/userOsbb/"+userId).
        map(res=>res.json());
    }



    



    }
