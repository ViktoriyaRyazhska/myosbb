import {Component, OnInit, Output} from '@angular/core'
import {EventEmitter} from '@angular/core';
import {User} from "../../../shared/models/User";
import {Osbb, IOsbb} from "../../../shared/models/osbb";
import {RegisterService} from "./register.service";
import {ROUTER_DIRECTIVES, Router} from "@angular/router";
import MaskedInput from 'angular2-text-mask';
import emailMask from 'node_modules/text-mask-addons/dist/emailMask.js';
import {GoogleplaceDirective} from "./googleplace.directive";
import {TimerWrapper} from '@angular/core/src/facade/async';
import {JoinOsbbComponent} from '../join/join.osbb.component';
import {SELECT_DIRECTIVES} from "ng2-select";
import {IHouse,House} from "../../../shared/models/House";
import {IApartment} from "../../../shared/models/apartment.interface";


@Component({
    selector: 'app-register',
    templateUrl: 'src/app/registration/registration_user/registration.html',
    styleUrls: ['assets/css/registration/registration.css'],
    providers: [RegisterService],
    directives: [ROUTER_DIRECTIVES, MaskedInput, GoogleplaceDirective,SELECT_DIRECTIVES]
})
export class RegistrationComponent implements OnInit {
    options = ['Приєднатись до існуючого ОСББ', 'Створити нове ОСББ'];
    newUser: User = new User();
    newOsbb: Osbb = new Osbb();
    public emailMask = emailMask;
    public textmask = [/[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/];
    public phoneMask = ['(', /[0]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];
    confirmPassword: number = "";
    error: boolean = false;
    errorConfirm: boolean = false;
    errorMsg = "";
    private osbbList: IOsbb[] = [];
    private houseList: IHouse[] = [];
    private apartmentList: IApartment[] = [];
    private osbb: Array<string> = [];
    private houses: Array<string> = [];
    private apartment: Array<string> = [];
    private isSelectedOsbb: boolean = false;
    private isSelectedHouse: boolean = false;
    private isSelectedApartment: boolean = false;
    errorMessage: string;
    private IsRegistered: boolean;
    private IsRegisteredOsbb: boolean;
    private isJoinedOsbb: boolean;
    public address: Object;

    constructor(private registerService: RegisterService, private _router: Router) {
        this.newUser.password="";
         this.newUser.activated = true;
         this.newOsbb.creationDate = new Date;
         this.osbbList = [];
         this.houseList = [];
         this.apartmentList = [];
    }
    ngOnInit() {
        this.listAllOsbb();
        this.IsRegistered = true;

    }
    onSubmitUser(status){
         if (status == this.options[1]) {
            console.log('CreateNew');
           this.IsRegistered = false;
           this.IsRegisteredOsbb = true;
           this.isJoinedOsbb = false;
        }
        else if (status == this.options[0]) {
            console.log('JoinToExist');
            this.IsRegistered = false;
            this.isJoinedOsbb = true;
            this.IsRegisteredOsbb = false;
        }
    }

    onSubmitOsbb(){
        this.SenderOsbbAndUser();
        setTimeout(() => {
        this._router.navigate(['/login']);
        },2000);
    }
    onSubmitJoin(){
        this.SenderJoin();
        setTimeout(() => {
        this._router.navigate(['/login']);
        },2000);
    }
    SenderJoin(value: any){
        this.registerService.addUser(this.newUser).subscribe(
            data =>{
                this.newUser = data;
                console.log(data);
            }
        )
    }
    SenderOsbbAndUser (){
        this.registerService.addUser(this.newUser).subscribe(
                data => {
                    this.newUser=data;  
                    this.newOsbb.creator = this.newUser; 
                    this.registerService.addOSBB(this.newOsbb).subscribe(data=>{
                        console.log(data)
                    });
                    console.log(data);
                error => console.log("Error HTTP Post Service");
                () => console.log("Job Done Osbb!");
                });        
    }

    getAddress(place: Object) {
        this.newOsbb.address = place['formatted_address'];
        var location = place['geometry']['location'];
        var lat = location.lat();
        var lng = location.lng();
        console.log("Address Object", place);
    }
    confirmPass() {
        this.error = false;
        var password = this.confirmPassword;
        var password2 = this.newUser.password;
        if (password.length != 0) {
            if (password != password2) {
                this.errorMsg = "Passwords don't match. Please try again";
                this.errorConfirm = true;
                this.confirmPassword = "";
                return;
            }
        }
        if (password2.length < 4 || password2.length > 16) {
            this.errorMsg = "Password cannot be shorter than 4 and longer than 16 characters";
            this.error = true;
            this.errorConfirm = false;
        } else {
            this.errorConfirm = false;
        }
    }
    Back(){
        this.isJoinedOsbb = false;
        this.IsRegisteredOsbb = false;
        this.IsRegistered = true;
    }
    listAllOsbb(){
        this.registerService.getAllOsbb()
            .subscribe((data)=> {
                this.osbbList = data;
                this.osbb = this.fillOsbb();
                console.log('all osbb names', this.osbb);
            }, (error)=> {
                this.handleErrors(error)
            });
    }
    listAllHouses(){
       this.registerService.getAllHouses()
            .subscribe((data)=> {
                this.houseList = data;
                this.houses = this.fillHouses();
                console.log('all houses', this.houses);
            }, (error)=> {
                this.handleErrors(error)
            });
    }
    listAllApartments(){
       this.registerService.getAllApartments()
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
    fillOsbbById(): number[]{
        let tempArr: number[] = [];
        for (let osbbObject of this.osbbList) {
            tempArr.push(osbbObject.osbbId);
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
