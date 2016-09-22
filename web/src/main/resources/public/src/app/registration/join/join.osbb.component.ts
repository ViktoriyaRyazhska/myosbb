import {Component, OnInit, Output} from '@angular/core'
import {EventEmitter} from '@angular/core';
import {IOsbb,Osbb} from "../../../shared/models/osbb";
import {IHouse,House} from "../../../shared/models/House";
import {IApartment} from "../../../shared/models/apartment.interface";
import {JoinOsbbService} from "./join.osbb.service";
import {ROUTER_DIRECTIVES, Router} from "@angular/router";
import {SELECT_DIRECTIVES} from "ng2-select";

@Component({
    selector: 'app-register',
    templateUrl: 'src/app/registration/join/join.osbb.html',
    styleUrls: ['assets/css/registration/registration.css'],
    providers: [JoinOsbbService],
    directives: [ROUTER_DIRECTIVES,SELECT_DIRECTIVES],

})
export class JoinOsbbComponent {
    private osbbList: IOsbb[] = [];
    private houseList: IHouse[] = [];
    private apartmentList: IApartment[] = [];
    private osbb: Array<string> = [];
    private houses: Array<string> = [];
    private apartment: Array<string> = [];
    private isSelectedOsbb: boolean = false;
    private isSelectedHouse: boolean = false;
    private isSelectedApartment: boolean = false;
    joinedOsbb:boolean;
    errorMessage: string;
  

    constructor( private joinOsbbService: JoinOsbbService,private _router: Router) { 
         this.osbbList = [];
         this.houseList = [];
         this.apartmentList = [];
         
    }
    ngOnInit() {
        this.listAllOsbb();

    }
    onSubmitOsbb(){
        setTimeout(() => {
        this._router.navigate(['/login']);
        },2000);
    }
    listAllOsbb(){
        this.joinOsbbService.getAllOsbb()
            .subscribe((data)=> {
                this.osbbList = data;
                this.osbb = this.fillOsbb();
                console.log('all osbb names', this.osbb);
            }, (error)=> {
                this.handleErrors(error)
            });
    }
    listAllHouses(){
       this.joinOsbbService.getAllHouses()
            .subscribe((data)=> {
                this.houseList = data;
                this.houses = this.fillHouses();
                console.log('all houses', this.houses);
            }, (error)=> {
                this.handleErrors(error)
            });
    }
    listAllApartments(){
       this.joinOsbbService.getAllApartments()
            .subscribe((data)=> {
                this.apartmentList = data;
                this.apartment = this.fillApartment();
                console.log('all apartment', this.apartment);
            }, (error)=> {
                this.handleErrors(error)
            });
    }

    selectedOsbb(value: any) {
        this.listAllHouses();
        this.isSelectedOsbb = true;
        console.log('select osbb: ', value);
    }
    handleErrors(error){
        return error;
    }

    selectedHouse(value: any) {
        this.listAllApartments();
         this.isSelectedHouse = true;
        console.log('select house: ', value);
    }
    selectedApartment(value: any) {
        this.isSelectedApartment = true;
        console.log('select apartment: ', value);
    }

    fillOsbb(): string[]{
        let tempArr: string[] = [];
        for (let osbbObject of this.osbbList) {
            tempArr.push(osbbObject.name);
        }
         console.log(tempArr)
        return tempArr;
    }
    fillHouses(): string[]{
        let tempArr: string[] = [];
        for (let houseObject of this.houseList) {
            tempArr.push(''+houseObject.houseId);
        }
        console.log(tempArr)
        return tempArr;
    }
    fillApartment(): string[] {
        let tempArr: string[] = [];
        for (let apartmentObject of this.apartmentList){
            tempArr.push(''+apartmentObject.number)   
        }
        console.log(tempArr)
        return tempArr;
    }
}
