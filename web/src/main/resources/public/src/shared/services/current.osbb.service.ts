/**
 * Created by Oleg on 19.09.2016.
 */
import ApiService = require("./api.service");
import {Injectable} from "@angular/core";
import {Http,Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {User} from "../models/User.ts"
import {CurrentUserService} from "./current.user.service"
import {HeaderComponent} from "../../app/header/header.component";

@Injectable()
export class CurrentOsbbService{

    private currentUser:User;
    private currentOsbbId:number=0;
    constructor(private http:Http,private currentUserService:CurrentUserService){

        this.currentUserService=HeaderComponent.currentUserService;
        this.currentUser = this.currentUserService.getUser();

     // console.log("osbb id ffrom service="+JSON.stringify(this.currentUser.osbbId));
        this.setOsbbId(this.currentUser.osbbId);
          }

    setOsbbId(id:number){
        this.currentOsbbId=id;
    }

     getCurrentOsbbId():number{
         return this.currentOsbbId;
        //return this.currentUser.osbb.osbbId;
    }
}
