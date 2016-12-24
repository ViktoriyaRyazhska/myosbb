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

@Component({
    selector: 'home-wall',
    templateUrl: 'src/app/home/home_wall/home.wall.html',
    styleUrls: ['src/app/home/home_wall/home.wall.css'],
    providers: [OsbbService, CreatorOsbbService],
    directives: [ROUTER_DIRECTIVES, VoteComponent, UserCalendarComponent],
    pipes:[CapitalizeFirstLetterPipe, TranslatePipe]
})
export class HomeWallComponent implements OnInit {

    isLoggedIn:boolean;
    currentOsbb: OsbbDTO;

    constructor(private osbbService: OsbbService,
     private creatorOsbbService:CreatorOsbbService,
     private currentUserService:CurrentUserService)
     {
        this.currentOsbb = null;
    }

    ngOnInit() {
        this.osbbService.getDTOOsbbById(this.currentUserService.getUser().osbbId)
            .then( osbb =>  {
                this.currentOsbb = osbb;
                 console.log(this.currentOsbb.name) 
            });
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
    
    getCreatorInfo():string {
        let info:string
        this.creatorOsbbService.getCreatorOsbb(this.currentOsbb.osbbId)
            .subscribe((data) => {
                let user:User = data;
                info = user.firstName+' '
                +user.lastName+' '
                +user.email;
            }, (error)=> {
                return '';
            });

            return info;  
    }
}
