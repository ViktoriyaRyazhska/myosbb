import {Component, OnInit} from "@angular/core";
import {User} from "../../../shared/models/User";
import {Osbb, IOsbb} from "../../../shared/models/osbb";
import {RegisterService} from "./register.service";
import {ROUTER_DIRECTIVES, Router} from "@angular/router";
import MaskedInput from "angular2-text-mask";
import emailMask from "node_modules/text-mask-addons/dist/emailMask.js";
import {GoogleplaceDirective} from "./googleplace.directive";
import {SELECT_DIRECTIVES} from "ng2-select";
import {IHouse} from "../../../shared/models/House";
import {IApartment} from "../../../shared/models/apartment.interface";
import {UserRegistration} from "../../../shared/models/user_registration";
import {Street} from "../../../shared/models/addressDTO";
import {City} from "../../../shared/models/addressDTO";
import {SelectItem} from "../../../shared/models/ng2-select-item.interface";
import {Region} from "../../../shared/models/addressDTO";
import {Mail} from "../../../shared/models/mail";
import {AddressService} from "../../../shared/services/address.service"
import {ToasterContainerComponent, ToasterService, ToasterConfig} from "angular2-toaster/angular2-toaster";
import {
    onErrorServerNoResponseToastMsg,
    onErrorNewUserAlreadyExists
} from "../../../shared/error/error.handler.component";
import {OsbbRegistration} from "../../../shared/models/osbb_registration";
import {CapitalizeFirstLetterPipe} from "../../../shared/pipes/capitalize-first-letter";
import {TranslatePipe} from "ng2-translate";
import { MailService } from "../../../shared/services/mail.sender.service";


@Component({ 
    selector: 'app-register',
    templateUrl: 'src/app/registration/registration_user/registration.html',
    styleUrls: ['assets/css/registration/registration.css'],
    providers: [RegisterService, ToasterService, MailService, AddressService],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    directives: [ROUTER_DIRECTIVES, MaskedInput, GoogleplaceDirective, SELECT_DIRECTIVES,
        ToasterContainerComponent]
})
export class RegistrationComponent implements OnInit {
    options = ['Приєднатись до існуючого ОСББ', 'Створити нове ОСББ'];
    newUser: UserRegistration = new UserRegistration();
    newOsbb: OsbbRegistration = new OsbbRegistration();
    public toasterconfig: ToasterConfig = new ToasterConfig({showCloseButton: true, tapToDismiss: true, timeout: 5000});
    public emailMask = emailMask;
    public textmask = [/[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/];
    public phoneMask = ['+','3','8','(', /[0]/, /\d/, /\d/, ')',/\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];
    public mail:Mail;
    public itemRegion:SelectItem;
    public itemCity:SelectItem;
    public itemStreet:SelectItem;
    public genders: string[] = ['Чоловік','Жінка'];
    confirmPassword: string = "";
    birthDateError: boolean = false;
    matchError: boolean = false;
    lengthError: boolean = false;
    checkOnUserPassword: boolean = false;
    error: boolean = false;
    errorConfirm: boolean = false;
    errorMsg = "";
    errorBirthDateMsg = "";
    private osbbList: IOsbb[] = [];
    private regionList: Region[] = [];
    private cityList: City[] = [];
    private streetList: Street[] = [];
    private regions: Array<string> = [];
    private cities: Array<string> = [];
    private streets: Array<string> = [];
    private houseList: IHouse[] = [];
    private apartmentList: IApartment[] = [];
    private osbb: Array<string> = [];
    private houses: Array<string> = [];
    private apartment: Array<string> = [];
    private isSelectedOsbb: boolean = false;
    private isSelectedHouse: boolean = false;
    private isSelectedApartment: boolean = false;
    private isSelectedStreet: boolean = false;
    private isSelectGender:boolean = false;
    errorMessage: string;
    private IsRegistered: boolean;
    private IsRegisteredOsbb: boolean;
    private isJoinedOsbb: boolean;

    constructor(private registerService: RegisterService,
                private _router: Router,
                private _toasterService: ToasterService,
                private mailService:MailService,
                private addressServise:AddressService) {
        this.newUser.password = "";
        this.newUser.activated = false;
        this.newOsbb.creationDate = new Date;
        this.osbbList = [];
        this.houseList = [];
        this.apartmentList = [];
        this.newUser.status = this.options[0];
        this.mail = new Mail();
    }

    ngOnInit() {
        this.listAllOsbb();
        this.ListAllRegion();
        this.IsRegistered = true;
    }
   
    onSubmitUser(status) {
        if (status == this.options[1]) {
            this.IsRegistered = false;
            this.IsRegisteredOsbb = true;
            this.isJoinedOsbb = false;
        }
        else if (status == this.options[0]) {
            this.IsRegistered = false;
            this.isJoinedOsbb = true;
            this.IsRegisteredOsbb = false;
        }
    }

    onSubmitOsbb() {
        this.SenderOsbbAndUser();
    }

    onSubmitJoin() {
        this.SenderJoin();
    }

    SenderJoin(): any {
        let isSuccessful: boolean = false;
        this.registerService.registerUser(this.newUser)
            .subscribe(
                data => {                
                    isSuccessful = true;
                    this.newUser = data;
                    console.log(data);
                    this._router.navigate(['/registration/success']);
                },
                error => {
                    isSuccessful = false;
                    this.handleErrors(error);

                }
            )
    }

    SenderOsbbAndUser() {
        this.newOsbb.creator = this.newUser;
        this.registerService.registerOsbb(this.newOsbb)
            .subscribe(
                data => {
                    console.log(data);
                    this._toasterService.pop('success', '', "Осбб " + this.newOsbb.name + " було успішно зареєстроване!");
                },
                error => {
                    this.handleErrors(error);
                }
            )

    }

    getAddress(place: Object) {
        this.newOsbb.address = place['formatted_address'];
        var location = place['geometry']['location'];
        var lat = location.lat();
        var lng = location.lng();
        console.log("Address Object", place);
    }

    matchCheck() {
        this.checkOnUserPassword = false;
        let passwordConfirm: string = this.confirmPassword;
        let userPassword: string = this.newUser.password;
        if (passwordConfirm.length != 0) {
            this.matchError = passwordConfirm != userPassword;
        }
    }

    confirmPassLength() {
        let userPassword: string = this.newUser.password;
        this.lengthError = userPassword.length < 4 || userPassword.length > 16;
        this.matchCheck();
        if (this.matchError) {
            this.checkOnUserPassword = true;
        }
    }

    castBirthDateStringToDate(): Date {
        return moment(this.newUser.birthDate).toDate();
    }

    checkDate() {
        let date = new Date();
        let res = this.castBirthDateStringToDate().valueOf() - date.valueOf();
        if (res >= 0) {
            this.birthDateError = true;
        }
        else this.birthDateError = false;
    }

    Back() {
        this.isJoinedOsbb = false;
        this.IsRegisteredOsbb = false;
        this.IsRegistered = true;
    }
    
    selectedGender(value: any) {
    	console.log(value.text);
        this.newUser.gender = value.text;
        this.isSelectGender = true;
    }

    removedGender(value:any) {
        this.isSelectGender = false;
    }
    
    selectedRegion(value: any) {
        if(this.cities.length!=0) {
           this.itemCity.text = '';
           this.itemStreet.text = '';
           this.cities = [];
           this.streets = [];
        }
                this.itemRegion = value;
                let region: Region = this.getRegionByName(value.text);
                this.listAllCitiesByRegion(region.id);
                this.isSelectedStreet = false;  

    }

    selectedCity(value: any) {
        console.log(value.text);
         if(this.streets.length!=0){
            this.itemStreet.text = '';
            this.streets = [];
        }
                this.itemCity = value;
                let city: City = this.getCityByName(value.text);
                this.listAllStreetsByCity(city.id);
                this.isSelectedStreet = false;
    }

    selectedOsbb(value: any) {
        this.isSelectedOsbb = true;
        let selectedOsbb: Osbb = this.getOsbbByName(value.text);
        this.newUser.osbbId = selectedOsbb.osbbId;
        this.listAllHousesByOsbb(this.newUser.osbbId);
        this.isSelectedStreet = false;
    }

    selectedStreet(value: any) {
        this.itemStreet = value;
        let street: Street = this.getStreetByName(value.text);
        this.newUser.street = street.id;
        this.isSelectedStreet = true;
    }

    selectedHouse(value: any) {
        this.isSelectedHouse = true;
        let houseId = this.getHouseIdByName(value.text);
        this.listAllApartmentsByHouse(houseId);
    }

    selectedApartment(value: any) {
        this.isSelectedApartment = true;
        let selectedApartmentID: number = this.getApartmentByApartmentNumber(value.text);
        this.newUser.apartmentId = selectedApartmentID;
    }

    ListAllRegion() {
        this.addressServise.getAllRegions()
                .subscribe((data)=> {
                this.regionList= data;
                
                this.regions =  this.fillRegion();
            }, (error)=> {
                this.handleErrors(error)
            });
    }

    listAllOsbb() {
        this.registerService.getAllOsbb()
            .subscribe((data) => {
                this.osbbList = data;
                this.osbb = this.fillOsbb();
                console.log('all osbb names', this.osbb);
            }, (error)=> {
                this.handleErrors(error)
            });
    }

    listAllStreetsByCity(id: number) {
        this.addressServise.getAllStreetsOfCity(id)
            .subscribe((data)=> {
                    this.streetList = data;
                    this.streets = this.fillStreet();
                },

                (error)=> {
                    this.handleErrors(error)
                })

    }

    listAllCitiesByRegion(id: number) {
        this.addressServise.getAllCitiesOfRegion(id)
            .subscribe((data)=> {
                    this.cityList = data;
                    this.cities = this.fillCities();
                },

                (error)=> {
                    this.handleErrors(error)
                })
    }

    listAllHousesByOsbb(id: number) {
        this.registerService.getAllHousesByOsbb(id)
            .subscribe((data) => {
                    this.houseList = data;
                    this.houses = this.fillHouses();
                },

                (error) => {
                    this.handleErrors(error)
                })

    }

    listAllApartmentsByHouse(houseId: number) {
        this.registerService.getAllApartmentsByHouse(houseId)
            .subscribe((data) => {
                this.apartmentList = data;
                this.apartment = this.fillApartment();
                console.log('all apartment', this.apartment);
            }, (error) => {
                this.handleErrors(error)
            });
    }

    getOsbbByName(name: string): Osbb {
        let selectedOsbb: Osbb = new Osbb();
        for (let osbb of this.osbbList) {
            if (osbb.name.match(name)) {
                selectedOsbb = osbb;
                break;
            }
        }
        return selectedOsbb;
    }

     getRegionByName(name: string): Region {
        let region: Region = new Region();
        for (let reg of this.regionList) {
            if (reg.name.match(name)) {
                region = reg;
                break;
            }
        }
        return region;
    }

      getCityByName(name: string): City {
        let city: City = new City();
        for (let ci of this.cityList) {
            if (ci.name.match(name)) {
                city = ci;
                break;
            }
        }
        return city;
    }

    getStreetByName(name: string): Street {
        let street: Street = new Street();
        for (let str of this.streetList) {
            if (str.name.match(name)) {
                street = str;
                break;
            }
        }
        return street;
    }
    
    getHouseIdByName(name: string): number {
        let houseId = 0;
        for (let house of this.houseList) {
            if (house.street.match(name)) {
                houseId = house.houseId;
                break;
            }
        }
        return houseId;
    }

    getApartmentByApartmentNumber(apartmentNumber: string): number {
        let apartmentID: number = 0;
        let apNumber = +apartmentNumber;
        for (let ap of this.apartmentList) {
            if (ap.number === apNumber) {
                apartmentID = ap.apartmentId
                break;
            }
        }

        return apartmentID;
    }

    fillOsbb(): string[] {
        let tempArr: string[] = [];
        for (let osbbObject of this.osbbList) {
            tempArr.push(osbbObject.name);
        }
        console.log(tempArr)
        return tempArr;
    }

    fillRegion(): string[] {
        let stri:string;
        let tempArr: string[] = [];
        for (let reg of this.regionList) {
            tempArr.push(reg.name);
        }

        return tempArr;
    }

     fillCities(): string[] {
        let tempArr: string[] = [];
        for (let city of this.cityList) {
            tempArr.push(city.name);
        }
        return tempArr;
    }

    fillStreet(): string[] {
        let tempArr: string[] = [];
        for (let street of this.streetList) {
            tempArr.push(street.name);
        }
        return tempArr;
    }

    fillOsbbById(): number[] {
        let tempArr: number[] = [];
        for (let osbbObject of this.osbbList) {
            tempArr.push(osbbObject.osbbId);
        }
        console.log(tempArr)
        return tempArr;
    }

    fillHouses(): string[] {
        let tempArr: string[] = [];
        for (let houseObject of this.houseList) {
            tempArr.push('' + houseObject.street);
        }
        console.log(tempArr)
        return tempArr;
    }

    fillApartment(): string[] {
        let tempArr: string[] = [];
        for (let apartmentObject of this.apartmentList) {
            tempArr.push('' + apartmentObject.number)
        }
        console.log(tempArr)
        return tempArr;
    }

    handleErrors(error) {
        if (error.status === 403) {
            this._toasterService.pop('error', "Такий користувач уже зареєстрований в системі");
        }
        if (error.status === 500) {
            this._toasterService.pop('error', "Нажаль, сталася помилка під час реєстрації");
        }
        console.log('error msg' + error)
    }
    
}
