import {Component, OnInit, Output} from '@angular/core'
import {EventEmitter} from '@angular/core';
import {IOsbb,Osbb} from "../../../shared/models/osbb";
import {IHouse,House} from "../../../shared/models/House";
import {IApartment} from "../../../shared/models/apartment.interface";
import {JoinOsbbService} from "./join.osbb.service";
import {ROUTER_DIRECTIVES} from "@angular/router";

@Component({
    selector: 'app-register',
    templateUrl: 'src/app/registration/join/join.osbb.html',
    styleUrls: ['assets/css/registration/registration.css'],
    providers: [JoinOsbbService],
    directives: [ROUTER_DIRECTIVES]
})
export class JoinOsbbComponent {
    osbbArr:IOsbb[];
    numbersOfHouses:IHouse[];
    numbersOfAppartments: IApartment[];
    joinedOsbb:boolean;
    errorMessage: string;
  

    constructor( private joinOsbbService: JoinOsbbService) { 
        this.osbbArr = [];
        this.numbersOfHouses = [];
        this.numbersOfAppartments = [];
    }
    ngOnInit() {
        this.getAllOsbb();
        this.listAllHouses();
        this.getAllAppartments();
    }
    onSubmit(){
        this.joinedOsbb=true;
    }
    listAllHouses(){
        this.joinOsbbService.listAllHouses().then(numbersOfHouses=> this.numbersOfHouses = numbersOfHouses, error =>  this.errorMessage = <any>error);
    }
    getAllOsbb(){
        this.joinOsbbService.getAllOsbb().then(osbbArr => this.osbbArr = osbbArr);
    }
    getAllAppartments(){
        this.joinOsbbService.getAllAppartments().then(numbersOfAppartments => this.numbersOfAppartments = numbersOfAppartments);
    }




}