import { Component, OnInit, Input } from '@angular/core';
import { User } from '../../../../shared/models/User';
import { Role } from '../../../../shared/models/role';
import { Osbb, IOsbb } from "../../../../shared/models/osbb";
import { House } from "../../../../shared/models/house";
import { IApartment } from "../../../../shared/models/apartment.interface";
import { UserRegistration } from '../../../../shared/models/user_registration';
import { OsbbRegistration } from "../../../../shared/models/osbb_registration";
import { UsersService} from "./users.service";
import { RegisterService } from "../../../registration/registration_user/register.service";
import { RegistrationComponent } from "../../../registration/registration_user/registration.component";
import { TranslatePipe } from "ng2-translate";
import { CapitalizeFirstLetterPipe } from "../../../../shared/pipes/capitalize-first-letter";
import { Router } from '@angular/router';
import { REACTIVE_FORM_DIRECTIVES, FormBuilder, Validators, NgForm } from '@angular/forms';
import { SELECT_DIRECTIVES } from "ng2-select";
import { MailService } from "../../../../shared/services/mail.sender.service";
import { TranslateService } from 'ng2-translate';
import { AddressService } from "../../../../shared/services/address.service";
import { Mail } from "../../../../shared/models/mail";
import { Region } from "../../../../shared/models/addressDTO";
import {SelectItem} from "../../../../shared/models/ng2-select-item.interface";
import {Street} from "../../../../shared/models/addressDTO";
import {City} from "../../../../shared/models/addressDTO";
import { CreatorOsbbService } from "../../../../shared/services/creatorOsbb.service";

@Component({ 
    selector: 'my-users',
    templateUrl: 'src/app/admin/components/users/users.table.html',
    providers: [UsersService, RegisterService, MailService, AddressService, CreatorOsbbService],
    styleUrls: ['src/app/admin/components/users/users.css'],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    directives: [REACTIVE_FORM_DIRECTIVES, RegistrationComponent,  SELECT_DIRECTIVES]
})
export class UsersComponent implements OnInit {
    
    userList:User[];
    roles: Array<string> = [];
    userMy: UserRegistration = new UserRegistration();
    osbbMy: OsbbRegistration = new OsbbRegistration();
    private mail:Mail;
    private isSelectedOsbb: boolean = false;
    private isSelectedHouse: boolean = false;
    private isSelectedApartment: boolean = false;
    private IsRegistered: boolean;
    private IsRegisteredOsbb: boolean;
    private isJoinedOsbb: boolean;
    private roleList: Role[] = [];
    private osbbList: Osbb[] = [];
    private houseList: House[] = [];
    private apartmentList: IApartment[] = [];
    private osbb: Array<string> = [];
    private houses: Array<string> = [];
    private apartment: Array<string> = [];
    private regions: Array<string> = [];
    private regionList: Region[] = [];
    public itemRegion:SelectItem;
    public itemCity:SelectItem;
    public itemStreet:SelectItem;
    public itemHouse:SelectItem;
    private cityList: City[] = [];
    private cities: Array<string> = [];
    private streetList: Street[] = [];
    private streets: Array<string> = [];
    private streetId:number;
    private numberHouse:number;
    private mailCreator:string;
    private genders:string[];
    private isSelectGender:boolean = false;

    constructor(private _userService:UsersService, private router:Router, private formBuilder:FormBuilder,
        private registerService: RegisterService,
        private mailService:MailService,
        private translateService:TranslateService,
        private addressService:AddressService,
        private creatorOsbbService:CreatorOsbbService) {
        console.log('constructore');
        this.userMy.activated = true;
        this.IsRegistered = false;
        this.isJoinedOsbb = true;
        this.IsRegisteredOsbb = false;
        this.userList = [];
        this.mail = new Mail();
        this.itemCity = new SelectItem();
        this.itemHouse = new SelectItem();
        this.itemRegion = new SelectItem();
        this.itemStreet = new SelectItem();
    }

    ngOnInit():any {
        console.log('init');
        this.ListAllRegion();
        this._userService.getAllUsers().subscribe(data => this.userList = data, error=>console.error(error));
        this.listAllRoles();
        this.genders = [this.translate('gender_male'), this.translate('gender_female')];
    }

    updateUser(user:User) {
        this._userService.updateUser(user).subscribe(()=>this.router.navigate(['admin/users']));
    }

    deleteUser(user:User) {
        this._userService.deleteUser(user).subscribe(()=>this.userList.splice(this.userList.indexOf(user, 0), 1));
    }

    saveUser(form: NgForm ) {
        console.log(this.userMy);
        this._userService.saveUser(this.userMy)
        .subscribe((data)=>{
           this.sendEmailRandomPassword(data);
           this.userList.push(data);
        });
    }

    ListAllRegion() {
        this.addressService.getAllRegions()
                .subscribe((data)=> {
                this.regionList= data;             
                this.regions =  this.fillRegion();
            }, (error)=> {
            });
    }

    fillRegion(): string[] {
        let stri:string;
        let tempArr: string[] = [];
        for (let reg of this.regionList) {
            tempArr.push(reg.name);
        }
        return tempArr;
    }

    private translate(message: string): string {
        let translatation: string;
        this.translateService.get(message)
        .subscribe(
            data => translatation = data
        )
        return translatation;
    }

    public changeActivation(user:User) {
        this._userService.changeActivation(user).subscribe(
            data=> {
                user.activated?user.activated=false:user.activated=true
            },
            error=> {
            }
        )
    }

    selectedGender(value: any) {

    	console.log(value.text);
        let gender: string = value.text;
        if( gender == 'Female' || gender =='Жінка' ) {
            this.userMy.gender = 'Female';
        }
        else{
            this.userMy.gender = 'Male';
        }
        this.isSelectGender = true;
    }

    removedGender() {
        this.isSelectGender = false;
    }
    
    sendEmailActive(user:User) {
        this.mail.to = user.email;
        this.mail.text = this.translate('activated_account');
        this.mail.subject = this.translate('subject_activeted');
        this.mailService.sendEmail(this.mail)
        .subscribe((data)=> {
        });
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
        console.log(value.text);
         if(this.streets.length!=0){
            this.itemStreet.text = '';
            this.itemHouse.text = '';
            this.streets = [];
            this.houses = [];
            this.isSelectedHouse = false;
        }
                console.log(value);
                this.itemCity = value;
                let city: City = this.getCityByName(value.text);
                this.listAllStreetsByCity(city.id);
    }

    selectedStreet(value: any) {
        if(this.houses.length!=0) {
            this.itemHouse.text = '';
            this.houses = [];
            this.isSelectedHouse = false;
        }
        console.log(value);
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
                this.userMy.house = house.houseId;
                this.userMy.osbbId = house.osbb.osbbId;
                this.osbbMy.name = house.osbb.name;
            }, (error)=> {
            });
    }

    listAllHousesByStreet(id: number) {
        this.registerService.getAllHousesByStreet(id)
            .subscribe((data) => {
                    this.houseList = data;
                    this.houses = this.fillHouses();
                    console.log('House = '+this.houses);
                },
                (error) => {
                })
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

    listAllCitiesByRegion(id: number) {
        this.addressService.getAllCitiesOfRegion(id)
            .subscribe((data)=> {
                    this.cityList = data;
                    this.cities = this.fillCities();
                },
                (error)=> {
                })
    }

    listAllStreetsByCity(id: number) {
        this.addressService.getAllStreetsOfCity(id)
            .subscribe((data)=> {
                    this.streetList = data;
                    this.streets = this.fillStreet();
                },
                (error)=> {
                })
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

    sendEmailDeactive(user:User) {
        this.mail.to = user.email;
        this.mail.text = this.translate('deactivated_account');
        this.mail.subject = this.translate('subject_deactiveted');
        this.mailService.sendEmail(this.mail)
        .subscribe((data)=> {
        });
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

    sendEmailRandomPassword(user:UserRegistration) {
        console.log('User password '+user.password);
        this.mail.to = user.email;
        this.mail.text = this.translate('send_email_user_registration_by_chairman')+', '+this.translate('send_osbb')+' '+this.osbbMy.name+
        ', '+this.translate('send_email_login')+' '+user.email+', '+this.translate('send_email_password')+' '+user.password;
        this.mail.subject = this.translate('subject_activeted_by_chairman');
        this.mailService.sendEmail(this.mail)
        .subscribe((data)=> {
        });
    }

    sendEmailDelete(user:User) {
        this.mail.to = user.email;
        this.mail.text = this.translate('deleted_account');
        this.mail.subject = this.translate('subject_deleted');
        this.mailService.sendEmail(this.mail)
        .subscribe((data)=> {
        });
    }

    toUser(id:number){
        this.router.navigate(['admin/user', id]);
    }

    selectedRole(value: any) {
        let selectedRole: Role = this.getRoleByName(value.text);
        this.userMy.role = selectedRole.roleId;
    }

    listAllRoles() {
        this._userService.listAllRoles()
        .subscribe((data) => {
            this.roleList = data;
            this.roles = this.fillRoles();
        });
    }

    getRoleByName(name: string): Role {
        let selectedRole: Role = new Role();
        for (let role of this.roleList) {
            if (role.name.match(name)) {
                selectedRole = role;
                break;
            }
        }
        return selectedRole;
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

    fillHouses(): string[] {
        let tempArr: string[] = [];
        for (let houseObject of this.houseList) {
            console.log(houseObject.numberHouse);
            tempArr.push('' + houseObject.numberHouse);
        }
        return tempArr;
    }

    fillRoles(): string[] {
        let tempArr: string[] = [];
        for (let roleObject of this.roleList) {
            tempArr.push(roleObject.name);
        }
        return tempArr;
    }

}
