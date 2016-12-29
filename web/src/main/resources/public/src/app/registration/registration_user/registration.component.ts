import {Component, OnInit} from "@angular/core";
import {Osbb, IOsbb} from "../../../shared/models/osbb";
import {RegisterService} from "./register.service";
import {ROUTER_DIRECTIVES, Router} from "@angular/router";
import MaskedInput from "angular2-text-mask";
import emailMask from "node_modules/text-mask-addons/dist/emailMask.js";
import {GoogleplaceDirective} from "./googleplace.directive";
import {SELECT_DIRECTIVES} from "ng2-select";
import {House} from "../../../shared/models/House";
import {IApartment} from "../../../shared/models/apartment.interface";
import {UserRegistration} from "../../../shared/models/user_registration";
import {Street} from "../../../shared/models/addressDTO";
import {City} from "../../../shared/models/addressDTO";
import {SelectItem} from "../../../shared/models/ng2-select-item.interface";
import {Region} from "../../../shared/models/addressDTO";
import {Mail} from "../../../shared/models/mail";
import {AddressService} from "../../../shared/services/address.service";
import {ToasterContainerComponent, ToasterService, ToasterConfig} from "angular2-toaster/angular2-toaster";
import {
    onErrorServerNoResponseToastMsg,
    onErrorNewUserAlreadyExists
} from "../../../shared/error/error.handler.component";
import {OsbbRegistration} from "../../../shared/models/osbb_registration";
import {CapitalizeFirstLetterPipe} from "../../../shared/pipes/capitalize-first-letter";
import {TranslatePipe} from "ng2-translate";
import { MailService } from "../../../shared/services/mail.sender.service";
import { TranslateService } from 'ng2-translate';
import { CreatorOsbbService } from "../../../shared/services/creatorOsbb.service";

@Component({ 
    selector: 'app-register',
    templateUrl: 'src/app/registration/registration_user/registration.html',
    styleUrls: ['assets/css/registration/registration.css'],
    providers: [RegisterService, ToasterService, MailService, AddressService, CreatorOsbbService],
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
    public itemRegion:SelectItem;
    public itemCity:SelectItem;
    public itemStreet:SelectItem;
    public itemHouse:SelectItem;
    public genders: string[];
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
    private houseList:House[] = [];
    private regionList: Region[] = [];
    private cityList: City[] = [];
    private streetList: Street[] = [];
    private regions: Array<string> = [];
    private cities: Array<string> = [];
    private streets: Array<string> = [];
    private apartmentList: IApartment[] = [];
    private osbb: Array<string> = [];
    private houses: Array<string> = [];
    private apartment: Array<string> = [];
    private isSelectedOsbb: boolean = false;
    private isSelectedHouse: boolean = false;
    private isSelectedApartment: boolean = false;
    private isSelectGender:boolean = false;
    errorMessage: string;
    private mailCreator:string;
    private numberHouse:number;
    private streetId:number;
    private IsRegistered: boolean;
    private IsRegisteredOsbb: boolean;
    private isJoinedOsbb: boolean;
    public alphabet:string[] = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];
    public captcha:string;
    public captchaUser:string;

    constructor(private registerService: RegisterService,
                private _router: Router,
                private _toasterService: ToasterService,
                private mailService:MailService,
                private addressServise:AddressService,
                private translateService: TranslateService,
                private creatorOsbbService: CreatorOsbbService) {
        this.newUser.password = "";
        this.newUser.activated = false;
        this.newOsbb.creationDate = new Date;
        this.newUser.status = this.options[0];
        this.itemCity = new SelectItem();
        this.itemHouse = new SelectItem();
        this.itemRegion = new SelectItem();
        this.itemStreet = new SelectItem();
    }

    ngOnInit() {
        this.listAllOsbb();
        this.ListAllRegion();
        this.IsRegistered = true;
        this.autoGeneratePassword();
        this.genders = [this.translate('gender_male'), this.translate('gender_female')];
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
    	if( this.captchaUser == null || this.captchaUser == '' ) {
            this._toasterService.pop('error', this.translate('you_have_not_filled_captcha'));
            return;
        }
        if( this.captcha != this.captchaUser ) {
            this._toasterService.pop('error', this.translate('you_incorrectly_filled_captcha'));
            return;
        }
        this.SenderJoin();
    }

    SenderJoin(): any {
        let isSuccessful: boolean = false;
        this.registerService.registerUser(this.newUser)
            .subscribe(
                data => {
                	this.sendEmails();
                    isSuccessful = true;
                    this.newUser = data;
                    this._router.navigate(['/registration/success']);
                },
                error => {
                    isSuccessful = false;
                    this.handleErrors(error);
                }
            )
    }

    SenderOsbbAndUser() {
        this.registerService.registerOsbb(this.newOsbb)
            .subscribe(
                data => {
                    this._toasterService.pop('success', '', "Осбб " + this.newOsbb.name + " було успішно зареєстроване!");
                },
                error => {
                    this.handleErrors(error);
                }
            )
    }

    private translate(message: string): string {
        let translatation: string;
        this.translateService.get(message)
        .subscribe(
            data => translatation = data
        )
        return translatation;
    }

    getAddress(place: Object) {
        this.newOsbb.address = place['formatted_address'];
        var location = place['geometry']['location'];
        var lat = location.lat();
        var lng = location.lng();
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
        let gender: string = value.text;
        if( gender == 'Female' || gender =='Жінка' ) {
            this.newUser.gender = 'Female';
        }
        else{
            this.newUser.gender = 'Male';
        }
        this.isSelectGender = true;
    }

    removedGender() {
        this.isSelectGender = false;
    }
    
    selectedRegion(value: any) {
        if(this.cities.length!=0) {
           this.itemCity.text = '';
           this.itemStreet.text = '';
           this.itemHouse.text = '';
           this.houses = [];
           this.cities = [];
           this.streets = [];
           this.isSelectedHouse = false;
        }
           this.itemRegion = value;
           let region: Region = this.getRegionByName(value.text);
           this.listAllCitiesByRegion(region.id); 
    }

    selectedCity(value: any) {
         if(this.streets.length!=0){
            this.itemStreet.text = '';
            this.itemHouse.text = '';
            this.streets = [];
            this.houses = [];
            this.isSelectedHouse = false;
        }
                this.itemCity = value;
                let city: City = this.getCityByName(value.text);
                this.listAllStreetsByCity(city.id);
    }

    selectedOsbb(value: any) {
        this.isSelectedOsbb = true;
        let selectedOsbb: Osbb = this.getOsbbByName(value.text);
        this.newUser.osbbId = selectedOsbb.osbbId;
        this.newOsbb.name = value.text;
        this.isSelectedHouse = false;
    }

    selectedStreet(value: any) {
        if(this.houses.length!=0) {
            this.itemHouse.text = '';
            this.houses = [];
            this.isSelectedHouse = false;
        }
        this.itemStreet = value;
        let street: Street = this.getStreetByName(value.text);
        this.streetId = street.id;
        this.listAllHousesByStreet(street.id);
    }

    selectedHouse(value: any) {
        this.itemHouse = value;
        this.isSelectedHouse = true; 
        this.numberHouse = value.text;
        this.findHouseAndOsbbId();
    }

    findHouseAndOsbbId() {
        this.registerService.getHouseByNumberHouseAndStreetId(this.numberHouse, this.streetId).
        subscribe((data)=> {
                let house:House =  data;             
                this.newUser.house = house.houseId;
                this.newUser.osbbId = house.osbb.osbbId;
                this.newOsbb.name = house.osbb.name;
                this.findCreatorOsbb(house.osbb.osbbId);
                            }, (error)=> {
                this.handleErrors(error)
            });
    }

    findCreatorOsbb(osbbId:number) {
                this.creatorOsbbService.getCreatorOsbb(osbbId).
                subscribe((data)=> {
                    let user:UserRegistration = data;
                    this.mailCreator = user.email;
                
            }, (error)=> {
                this.handleErrors(error)
            });
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

    listAllHousesByStreet(id: number) {
        this.registerService.getAllHousesByStreet(id)
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
        return tempArr;
    }

    fillHouses(): string[] {
        let tempArr: string[] = [];
        for (let houseObject of this.houseList) {
            tempArr.push('' + houseObject.numberHouse);
        }
        return tempArr;
    }

    fillApartment(): string[] {
        let tempArr: string[] = [];
        for (let apartmentObject of this.apartmentList) {
            tempArr.push('' + apartmentObject.number)
        }
        return tempArr;
    }

    handleErrors(error) {
        if (error.status === 403) {
            this._toasterService.pop('error', "Такий користувач уже зареєстрований в системі");
        }
        if (error.status === 500) {
            this._toasterService.pop('error', "Нажаль, сталася помилка під час реєстрації");
        }
    }
    
    sendEmails() {
        let mail:Mail = new Mail();
        mail.to = this.newUser.email;
        mail.text = this.translate('send_email_user')+', '+this.translate('send_osbb')+' '+this.newOsbb.name+', '+this.translate('send_email_user2');
        mail.subject = this.translate('subject_registration');

        this.mailService.sendEmail(mail).subscribe(
                (data)=> {},         
                (error)=> {
                this.handleErrors(error)
        });

        mail.to = this.mailCreator;
        mail.text = this.translate('send_email_chairman');
        mail.subject = this.translate('subject_registration');

        this.mailService.sendEmail(mail).subscribe(
                (data)=> {},         
                (error)=> {
                this.handleErrors(error)
        });
    }

    autoGeneratePassword() {
        const minLength:number = 4;
        const maxLength:number = 5;
        const maxThreshold:number = 3;
        const minThreshold:number = 1;
        const indexOFLowercaseLetter:number = 1;
        const indexOFUppercaseLetter:number = 2;
        const divider:number = 3;
        const randomArea:number = 10;
        let password:string = '';       
        const lenght = Math.floor(Math.random() * (maxLength) + minLength);
        let ind:number = 0;

        while(ind < lenght) {
            const rand:number = Math.floor(Math.random() * (maxThreshold) + minThreshold);
            if(rand % divider == indexOFLowercaseLetter) {
                password += this.alphabet[Math.floor(Math.random()*this.alphabet.length)];
            }
            else if(rand % divider == indexOFUppercaseLetter) {
                password += this.alphabet[Math.floor(Math.random()*this.alphabet.length)].toUpperCase();
            }
            else{
                password += Math.floor(Math.random() * randomArea).toString();
            }
            ind++;
        }
        this.captcha = password;
    }

    initTextUser(text:any) {
        this.captchaUser = text.target.value;
    } 
}
