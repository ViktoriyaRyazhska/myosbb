import {Component, OnInit} from "@angular/core";
import {User} from "../../../shared/models/User";
import {Osbb, IOsbb} from "../../../shared/models/osbb";
import {RegisterService} from "./register.service";
import {ROUTER_DIRECTIVES, Router} from "@angular/router";
import MaskedInput from "angular2-text-mask";
import emailMask from 'node_modules/text-mask-addons/dist/emailMask.js';
import {GoogleplaceDirective} from "./googleplace.directive";
import {SELECT_DIRECTIVES} from "ng2-select";
import {IHouse} from "../../../shared/models/House";
import {IApartment} from "../../../shared/models/apartment.interface";
import {UserRegistration} from "../../../shared/models/user_registration";
import {ToasterContainerComponent, ToasterService, ToasterConfig} from "angular2-toaster/angular2-toaster";
import {
    onErrorServerNoResponseToastMsg,
    onErrorNewUserAlreadyExists
} from "../../../shared/error/error.handler.component";
import {OsbbRegistration} from "../../../shared/models/osbb_registration";
import {CapitalizeFirstLetterPipe} from "../../../shared/pipes/capitalize-first-letter";
import {TranslatePipe} from "ng2-translate";
// import {JoinOsbbComponent} from '../join/join.osbb.component';


@Component({ 
    selector: 'app-register',
    templateUrl: 'src/app/registration/registration_user/registration.html',
    styleUrls: ['assets/css/registration/registration.css'],
    providers: [RegisterService, ToasterService],
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
    public phoneMask = ['(', /[0]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];
    confirmPassword: string = "";
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

    constructor(private registerService: RegisterService,
                private _router: Router,
                private _toasterService: ToasterService) {
        this.newUser.password = "";
        this.newUser.activated = true;
        this.newOsbb.creationDate = new Date;
        this.osbbList = [];
        this.houseList = [];
        this.apartmentList = [];
        this.newUser.status = this.options[0];
    }

    ngOnInit() {
        this.listAllOsbb();
        this.IsRegistered = true;
 
    }

    onSubmitUser(status) {
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
                    this._toasterService.pop('success', '', 'Дякуємо за реєстрацію!')
                    this.toLoginPageRedirect();
                },
                error => {
                    isSuccessful = false;
                    this.handleErrors(error);

                }
            )
    }

    private toLoginPageRedirect() {
        setTimeout(() => {
            this._router.navigate(['/login']);
        }, 2000);
    }

    SenderOsbbAndUser() {
        this.newOsbb.creator = this.newUser;
        this.registerService.registerOsbb(this.newOsbb)
            .subscribe(
                data => {
                    console.log(data);
                    this._toasterService.pop('success', '', "Осбб " + this.newOsbb.name + " було успішно зареєстроване!");
                    this.toLoginPageRedirect();
                },
                error=> {
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

    confirmPass() {
        this.error = false;
        let password: string = this.confirmPassword;
        let password2: string = this.newUser.password;
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

    Back() {
        this.isJoinedOsbb = false;
        this.IsRegisteredOsbb = false;
        this.IsRegistered = true;
    }


    selectedOsbb(value: any) {
        this.isSelectedOsbb = true;
        console.log('select osbb: ', value);
        let selectedOsbb: Osbb = this.getOsbbByName(value.text);
        this.newUser.osbbId = selectedOsbb.osbbId;
        this.listAllHousesByOsbb(this.newUser.osbbId);
    }


    selectedHouse(value: any) {
        console.log('select house: ' + value);
        this.isSelectedHouse = true;
        let houseId = this.getHouseIdByName(value.text);
        console.log('select houseId: ' + houseId);
        this.listAllApartmentsByHouse(houseId);
    }


    selectedApartment(value: any) {
        this.isSelectedApartment = true;
        let selectedApartmentID: number = this.getApartmentByApartmentNumber(value.text);
        this.newUser.apartmentId = selectedApartmentID;
        console.log('select apartment: ' + selectedApartmentID);
        console.log(JSON.stringify(this.newUser));
    }

    listAllOsbb() {
        this.registerService.getAllOsbb()
            .subscribe((data)=> {
                this.osbbList = data;
                this.osbb = this.fillOsbb();
                console.log('all osbb names', this.osbb);
            }, (error)=> {
                this.handleErrors(error)
            });
    }

    // listAllHouses() {
    //     this.registerService.getAllHouses()
    //         .subscribe((data)=> {
    //             this.houseList = data;
    //             this.houses = this.fillHouses();
    //             console.log('all houses', this.houses);
    //         }, (error)=> {
    //             this.handleErrors(error)
    //         });
    // }


    listAllHousesByOsbb(id: number) {

        this.registerService.getAllHousesByOsbb(id)
            .subscribe((data)=> {
                    this.houseList = data;
                    this.houses = this.fillHouses();
                    console.log('all houses', this.houses);
                },

                (error)=> {
                    this.handleErrors(error)
                })

    }

    // listAllApartments() {
    //     this.registerService.getAllApartments()
    //         .subscribe((data)=> {
    //             this.apartmentList = data;
    //             this.apartment = this.fillApartment();
    //             console.log('all apartment', this.apartment);
    //         }, (error)=> {
    //             this.handleErrors(error)
    //         });
    // }


    listAllApartmentsByHouse(houseId: number) {
        this.registerService.getAllApartmentsByHouse(houseId)
            .subscribe((data)=> {
                this.apartmentList = data;
                this.apartment = this.fillApartment();
                console.log('all apartment', this.apartment);
            }, (error)=> {
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
