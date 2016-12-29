import { Component, OnInit } from "@angular/core";
import { ROUTER_DIRECTIVES } from "@angular/router";
import moment from 'moment';

import { VoteComponent } from "../voting/vote.component";
import { IOsbb, Osbb } from "../../../shared/models/osbb";
import { OsbbDTO } from "../../../shared/models/osbbDTO";
import { OsbbService } from '../../admin/components/osbb/osbb.service';
import { UserCalendarComponent } from '../../user/calendar/user.calendar.component';
import { TranslatePipe } from "ng2-translate";
import { CapitalizeFirstLetterPipe } from "../../../shared/pipes/capitalize-first-letter";
import { CurrentUserService } from "../../../shared/services/current.user.service";
import { CreatorOsbbService } from "../../../shared/services/creatorOsbb.service";
import { User } from "../../../shared/models/user";
import { House } from "../../../shared/models/house";

@Component({
    selector: 'home-wall',
    templateUrl: 'src/app/home/home_wall/home.wall.html',
    styleUrls: ['src/app/home/home_wall/home.wall.css'],
    providers: [OsbbService, CreatorOsbbService],
    directives: [ROUTER_DIRECTIVES, VoteComponent, UserCalendarComponent],
    pipes:[CapitalizeFirstLetterPipe, TranslatePipe]
})
export class HomeWallComponent implements OnInit {
    
    private user: User;
    private info:string
    isLoggedIn:boolean;
    currentOsbb: OsbbDTO;
    private house:House;
    private address:string;

    constructor(private osbbService: OsbbService,
     private creatorOsbbService:CreatorOsbbService,
     private currentUserService:CurrentUserService)
     {
        this.currentOsbb = null;
    }

    ngOnInit() {
        this.getUser();
        this.getAddress();
        this.osbbService.getDTOOsbbById(this.currentUserService.getUser().osbbId)
            .then( osbb =>  {
                this.currentOsbb = osbb;
                 this.getCreatorInfo();
            })
    }

     getFormatDate():string {
      return moment(this.currentOsbb.creationDate).format("DD.MM.YYYY");
    }

    getLogoUrl(): string {
        if(this.currentOsbb.logo != null){
            return  this.currentOsbb.logo.url;
        }
        return 'assets/img/my_house.png';   
    }
    
    getCreatorInfo(){
        this.creatorOsbbService.getCreatorOsbb(this.currentOsbb.osbbId)
            .subscribe((data) => {
                let user:User = data;
                this.info = user.firstName+' '
                +user.lastName;
            }, (error)=> {

            });      
    }

    getAddress() {
        let houseNum:string =''+this.house.numberHouse;
        let street:string = this.house.street.name;
        let city:string = this.house.street.city.name;
        let region:string = this.house.street.city.region.name;
        this.address = region+' '+city+' '+street+' '+houseNum;
    }

    getUser() {
        this.user = this.currentUserService.getUser();
        this.house = this.user.house;
    }
}
