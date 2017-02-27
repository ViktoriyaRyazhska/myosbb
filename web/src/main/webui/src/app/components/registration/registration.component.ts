import { Component, OnInit } from '@angular/core';
import { AppState } from '../app.service';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import { Router } from '@angular/router';
import { RegisterService } from './register.service';
import { AddressService } from './address.service';
import { UserRegistration } from './models/user_registration';
import { OsbbRegistration } from './models/osbb_registration';
import { SelectItem } from './models/ng2-select-item.model';
import { Osbb, IOsbb } from './models/osbb.model';
import { IApartment } from './models/apartment.model';
import { Region, City, Street } from './models/addressDTO.model';
import { House } from './models/house.model';
import { ToasterService } from 'angular2-toaster';
import { RegistrationConstants } from './registration.constant';
import * as moment from 'moment';
import * as _ from 'lodash';

@Component({
  selector: 'app-registration',
  providers: [ RegisterService, AddressService, ToasterService ],
  styleUrls: [ './registration.scss' ],
  templateUrl: './registration.template.html'
})
export class RegistrationComponent implements OnInit {
  public options = ['Приєднатись до існуючого ОСББ', 'Створити нове ОСББ'];
  public newUser: UserRegistration = new UserRegistration();
  public newOsbb: OsbbRegistration = new OsbbRegistration();
  public textmask = RegistrationConstants.Masks.textMask;
  public phoneMask = RegistrationConstants.Masks.phoneMask;
  public alphabet: string[] = RegistrationConstants.Masks.alphabet;
  public genders: string[];
  public itemRegion: SelectItem;
  public itemCity: SelectItem;
  public itemStreet: SelectItem;
  public itemHouse: SelectItem;
  public confirmPassword: string = '';
  public checkOnUserPassword: boolean = false;
  public birthDateError: boolean = false;
  public lengthError: boolean = false;
  public matchError: boolean = false;
  public captchaUser: string;
  public captcha: string;
  public isSelectGender: boolean = false;
  public IsRegisteredOsbb: boolean;
  public IsRegistered: boolean;
  public isJoinedOsbb: boolean;
  public isSelectedHouse: boolean = false;
  public numberHouse: number;
  public streetId: number;
  public isSelectedOsbb: boolean = false;
  public isSelectedApartment: boolean = false;
  public regions: string[] = [];
  public cities: string[] = [];
  public houses: string[] = [];
  public streets: string[] = [];
  public osbb: string[] = [];
  private houseList: House[] = [];
  private regionList: Region[] = [];
  private cityList: City[] = [];
  private streetList: Street[] = [];
  private osbbList: IOsbb[] = [];
  private apartmentList: IApartment[] = [];

  constructor(private http: Http,
              private router: Router,
              private registerService: RegisterService,
              private toasterService: ToasterService,
              private addressServise: AddressService) {
    this.newUser.password = '';
    this.newUser.activated = false;
    this.newOsbb.creationDate = new Date();
    this.newUser.status = this.options[0];
    this.itemHouse = new SelectItem();
    this.itemRegion = new SelectItem();
    this.itemCity = new SelectItem();
    this.itemStreet = new SelectItem();
  }
  public ngOnInit() {
    this.listAllOsbb();
    this.ListAllRegion();
    this.autoGeneratePassword();
    this.IsRegistered = true;
    this.genders = ['Female', 'Male'];
  }
  public onSubmitOsbb() {
    this.SenderOsbbAndUser();
  }
  public SenderJoin(): any {
    let isSuccessful: boolean = false;
    this.toasterService.pop('success', 'created', this.newUser.firstName);
    this.registerService.registerUser(this.newUser)
      .subscribe(
        (data) => {
          isSuccessful = true;
          this.newUser = data;
          this.router.navigate(['/registration/success']);
        },
        (error) => {
          isSuccessful = false;
          this.handleErrors(error);
        });
  }
  public handleErrors(error) {
    if (error.status === 403) {
      this.toasterService.pop('error', 'Такий користувач уже зареєстрований в системі');
    }
    if (error.status === 500) {
      this.toasterService.pop('error', 'Нажаль, сталася помилка під час реєстрації');
    }
  }
  public fillOsbb(): string[] {
    let tempArr: string[] = [];
    _.map(this.osbbList, (osbbObject) => { tempArr.push(osbbObject.name); });
    return tempArr;
  }
  public fillRegion(): string[] {
    let stri: string;
    let tempArr: string[] = [];
    _.map(this.regionList, (reg) => { tempArr.push(reg.name); });
    return tempArr;
  }
  public fillCities(): string[] {
    let tempArr: string[] = [];
    _.map(this.cityList, (city) => { tempArr.push(city.name); });
    return tempArr;
  }
  public fillStreet(): string[] {
    let tempArr: string[] = [];
    _.map(this.streetList, (street) => { tempArr.push(street.name); });
    return tempArr;
  }
  public fillOsbbById(): number[] {
    let tempArr: number[] = [];
    _.map(this.osbbList, (osbbObject) => { tempArr.push(osbbObject.osbbId); });
    return tempArr;
  }
  public fillHouses(): string[] {
    let tempArr: string[] = [];
    _.map(this.houseList, (houseObject) => { tempArr.push('' + houseObject.numberHouse); });
    return tempArr;
  }
  public fillApartment(): string[] {
    let tempArr: string[] = [];
    _.map(this.apartmentList, (apartmentObject) => { tempArr.push('' + apartmentObject.number); });
    return tempArr;
  }
  public selectedOsbb(value: any) {
    this.isSelectedOsbb = true;
    let selectedOsbb: Osbb = this.getOsbbByName(value.text);
    this.newUser.osbbId = selectedOsbb.osbbId;
    this.newOsbb.name = value.text;
    this.isSelectedHouse = false;
  }
  public getOsbbByName(name: string): Osbb {
    let selectedOsbb: Osbb = new Osbb();
    _.map(this.osbbList, (osbb) => {
      if (osbb.name.match(name)) {
        selectedOsbb = osbb;
        return false;
      }
    });
    return selectedOsbb;
  }
  public ListAllRegion() {
    this.addressServise.getAllRegions()
      .subscribe((data) => {
        this.regionList = data;
        this.regions =  this.fillRegion();
      },
      (error) => {
        this.handleErrors(error);
      });
  }
  public listAllOsbb() {
    this.registerService.getAllOsbb()
      .subscribe((data) => {
        this.osbbList = data;
        this.osbb = this.fillOsbb();
      },
      (error) => {
        this.handleErrors(error);
      });
  }
  public getCityByName(name: string): City {
    let city: City = new City();
    _.map(this.cityList, (ci) => {
      if ( ci.name.match(name)) {
        city = ci;
        return false;
      }
    });
    return city;
  }
  public getStreetByName(name: string): Street {
    let street: Street = new Street();
    _.map(this.streetList, (str) => {
      if ( str.name.match(name)) {
        street = str;
        return false;
      }
    });
    return street;
  }
  public selectedHouse(value: any) {
    this.itemHouse = value;
    this.isSelectedHouse = true;
    this.numberHouse = value.text;
    this.findHouseAndOsbbId();
  }
  public getApartmentByApartmentNumber(apartmentNumber: string): number {
    let apartmentID: number = 0;
    let apNumber = +apartmentNumber;
    _.map(this.apartmentList, (ap) => {
      if ( ap.number === apNumber ) {
        apartmentID = ap.apartmentId;
        return false;
      }
    });
    return apartmentID;
  }
  public findHouseAndOsbbId() {
    this.registerService.getHouseByNumberHouseAndStreetId(this.numberHouse, this.streetId).
    subscribe((data) => {
        let house: House =  data;
        this.newUser.house = house.houseId;
        this.newUser.osbbId = house.osbb.osbbId;
        this.newOsbb.name = house.osbb.name;
        this.findCreatorOsbb(house.osbb.osbbId);
      },
      (error) => {
      this.handleErrors(error);
    });
  }
  public findCreatorOsbb(osbbId: number) {
    this.registerService.getCreatorOsbb(osbbId).
    subscribe((data) => {
      let user: UserRegistration = data;
    },
    (error) => {
      this.handleErrors(error);
    });
  }
  public onSubmitUser(status) {
    if (status === this.options[1]) {
      this.IsRegistered = false;
      this.IsRegisteredOsbb = true;
      this.isJoinedOsbb = false;
    } else if (status === this.options[0]) {
      this.IsRegistered = false;
      this.isJoinedOsbb = true;
      this.IsRegisteredOsbb = false;
    }
  }
  public onSubmitJoin() {
    if ( this.captchaUser === null || this.captchaUser === '' ) {
      this.toasterService.pop('error', 'you_have_not_filled_captcha');
      return;
    }
    if ( this.captcha !== this.captchaUser ) {
      this.toasterService.pop('error', 'you_incorrectly_filled_captcha');
      return;
    }
    this.SenderJoin();
  }
  public selectedGender(value: any) {
    let gender: string = value.text;
    this.newUser.gender = ( gender === 'Female' || gender === 'Жінка' ) ? 'Female' : 'Male';
    this.isSelectGender = true;
  }
  public SenderOsbbAndUser() {
    this.registerService.registerOsbb(this.newOsbb)
      .subscribe(
        (data) => {
          this.toasterService.pop('success', '', 'Осбб ' + this.newOsbb.name +
          ' було успішно зареєстроване!');
        },
        (error) => {
          this.handleErrors(error);
        });
  }
  public matchCheck() {
    this.checkOnUserPassword = false;
    let passwordConfirm: string = this.confirmPassword;
    let userPassword: string = this.newUser.password;
    if (passwordConfirm) { this.matchError = passwordConfirm !== userPassword; }
  }
  public confirmPassLength() {
    let userPassword: string = this.newUser.password;
    this.lengthError = userPassword.length < 4 || userPassword.length > 16;
    this.matchCheck();
    if (this.matchError) { this.checkOnUserPassword = true; }
  }
  public removedGender() {
    this.isSelectGender = false;
  }
  public castBirthDateStringToDate(): Date {
    return moment(this.newUser.birthDate).toDate();
  }
  public checkDate() {
    let date = new Date();
    let res = this.castBirthDateStringToDate().valueOf() - date.valueOf();
    this.birthDateError = (res >= 0) ? true : false;
  }
  public Back() {
    this.isJoinedOsbb = false;
    this.IsRegisteredOsbb = false;
    this.IsRegistered = true;
  }
  public getRegionByName(name: string): Region {
    let region: Region = new Region();
    _.map(this.regionList, (reg) => {
      if (reg.name.match(name)) {
        region = reg;
        return false;
      }
    });
    return region;
  }
  public listAllCitiesByRegion(id: number) {
    this.addressServise.getAllCitiesOfRegion(id)
      .subscribe((data) => {
        this.cityList = data;
        this.cities = this.fillCities();
      },
      (error) => {
          this.handleErrors(error);
      });
  }
  public selectedRegion(value: any) {
    if (this.cities.length) {
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
  public listAllStreetsByCity(id: number) {
    this.addressServise.getAllStreetsOfCity(id)
      .subscribe((data) => {
        this.streetList = data;
        this.streets = this.fillStreet();
      },
      (error) => {
          this.handleErrors(error);
      });
  }
  public listAllHousesByStreet(id: number) {
    this.registerService.getAllHousesByStreet(id)
      .subscribe((data) => {
      this.houseList = data;
      this.houses = this.fillHouses();
      },
      (error) => {
          this.handleErrors(error);
      });
  }
  public selectedCity(value: any) {
    if (this.streets.length) {
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
  public selectedStreet(value: any) {
    if (this.houses.length) {
      this.itemHouse.text = '';
      this.houses = [];
      this.isSelectedHouse = false;
    }
    this.itemStreet = value;
    let street: Street = this.getStreetByName(value.text);
    this.streetId = street.id;
    this.listAllHousesByStreet(street.id);
  }
  public getAddress(place: Object) {
    this.newOsbb.address = place['formatted_address'];
    let location = place['geometry']['location'];
    let lat = location.lat();
    let lng = location.lng();
  }
  public autoGeneratePassword() {
    const minLength: number = 4;
    const maxLength: number = 5;
    const maxThreshold: number = 3;
    const minThreshold: number = 1;
    const indexOFLowercaseLetter: number = 1;
    const indexOFUppercaseLetter: number = 2;
    const divider: number = 3;
    const randomArea: number = 10;
    let password: string = '';
    const lenght = Math.floor(Math.random() * (maxLength) + minLength);
    let ind: number = 0;
    while (ind < lenght) {
        const rand: number = Math.floor(Math.random() * (maxThreshold) + minThreshold);
        if (rand % divider === indexOFLowercaseLetter) {
          password += this.alphabet[Math.floor(Math.random() * this.alphabet.length)];
        } else if (rand % divider === indexOFUppercaseLetter) {
          password += this.alphabet[Math.floor(Math.random() * this.alphabet.length)].toUpperCase();
        } else {
          password += Math.floor(Math.random() * randomArea).toString();
        }
        ind++;
    }
    this.captcha = password;
  }
  public initTextUser(text: any) {
    this.captchaUser = text.target.value;
  }
}
