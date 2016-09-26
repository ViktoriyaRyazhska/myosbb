import {Component,ViewChild} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {HTTP_PROVIDERS,Http,Headers,RequestOptions} from '@angular/http';
import{toPromise} from "rxjs/operator/toPromise";
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {apartmentProfileService} from './apartment.profile.service';
import {DatePipe} from '@angular/common';
import {User} from "../../../../shared/models/User";
import{CurrentUserService} from "../../../../shared/services/current.user.service";
import{Apartment} from "../../../../shared/models/apartment.interface";
import {TranslatePipe} from "ng2-translate/ng2-translate";

@Component({
    selector:'apartment-profile',
    templateUrl:'src/app/home/components/ApartmentProfile/apartment.profile.html',
    providers: [ apartmentProfileService],
    directives:[MODAL_DIRECTIVES],
    viewProviders: [BS_VIEW_PROVIDERS],
    styleUrls: ['src/app/home/components/ApartmentProfile/apartmentStyle.css'],
    pipes: [TranslatePipe]
})



export class ApartmentProfileComponent {
    @ViewChild('userListModal')
    public userListModal:ModalDirective;
    id:any;
    paramsSub:any;
    isUser:boolean = false;
    private currentUser:User;
    private currentApartment:Apartment={apartmentId:0,square:0,number:0,owner:0,house:0,bills:[]};
   private usersInApartment:User[];
    private countOfUsers:number=2;
    private owner:User={userId:0,phoneNumber:'відсутній',email:''};
    active:boolean = true;


    constructor(private activatedRoute:ActivatedRoute, private apartmentService:apartmentProfileService, private currentUserService:CurrentUserService) {
        this.paramsSub = this.activatedRoute.params.subscribe(params => this.id = parseInt(params['id'], 10));
        console.log("constructor");



    }


    ngOnInit() {
        this.currentUser = this.currentUserService.getUser();
        this.apartmentService.getCurrentApartment(this.id)
            .subscribe(res=>{this.currentApartment=res});
        this.apartmentService.getUsersInApartment(this.id)
            .subscribe(res=>{
                this.usersInApartment=res;
                this.countOfUsers=this.usersInApartment.length;
                });

        this.apartmentService.getOwnerInApartment(this.id)
            .subscribe(res=>{this.owner=res;console.log("user OWNER: "+res)});
        this.usersInApartment=[];

        this.apartmentService.getAllBillsByApartment(this.id)
           .subscribe(res=>{this.billsOfApartment=res.rows;});

    }

   
    openUserListModal(){
       
        this.userListModal.show();
        
    }
    onModalSubmit(){
        this.active=false;
        this.userListModal.hide();

    }
        
    
    ngOnDestroy(){
            this.paramsSub.unsubscribe();
        }


}
