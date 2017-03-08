import {
  Component,
  OnInit,
  ViewChild
} from '@angular/core';
import {
  Http,
  Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { ApartmentService } from './apartment.service';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from '../../shared/login/login.service';
import { UserApartment } from '../../models/userWithApartment.model';
import { ToasterService } from 'angular2-toaster';
import { ModalDirective } from 'ng2-bootstrap/modal';
import { House } from '../../models/house.model';
import { User } from '../../models/user.model';
import { RegistrationConstants } from '../../registration/registration.constant';
import * as _ from 'lodash';

@Component({
  selector: 'apartments',
  templateUrl: 'apartment.component.html',
  styleUrls: ['../../../assets/css/manager.page.layout.scss', './apartment.scss'],
  providers: [ApartmentService]
})

export class ApartmentComponent implements OnInit {

  @ViewChild('createModal') public createModal: ModalDirective;

  public title: string = `Apartments`;
  public resData: any;
  private admin: boolean;
  private currentUser: User;
  private authRole: string;
  public userApartment: UserApartment = new UserApartment();
  public phoneMask = RegistrationConstants.Masks.phoneMask;
  public houseNumber: number;
  public house: House;
  private houseList: House[] = [];

  

  constructor(
    public http: Http,
    public apartment: ApartmentService,
    public loginService: LoginService,
    private toasterService: ToasterService,
  ) { 
    this.currentUser = loginService.getUser();
  }

 
 public onSubmitUserApartment(){
  this.apartment.registerApartmentWithUser(this.userApartment, this.house.houseId)
  .subscribe(
        (data) => {
          this.toasterService.pop('success', '', 'Користувача і квартиру було успішно зареєстроване!');
        },
        (error) => {
          this.handleErrors(error);
        });  
}

   public  onChangeHouse(newObj) {
    this.house = newObj;
    this.houseNumber = this.house.houseId;
    // ... do other stuff here ...
  }

  public ngOnInit() {
      // this.getCurrentUser();
    this.apartment.getApartmentData().subscribe((data) => {
      this.resData = data;
    });
     this.ListHouses();

  }

  public ListHouses(){
    
    console.log(this.loginService.currentUser);
     console.log(this.loginService.getRole());
     console.log(this.currentUser);

      switch (this.loginService.getRole()) {
      case 'ROLE_ADMIN':
       this.ListAllHouses();
        break;
       case 'ROLE_MANAGER':
        this.listManagerHouses(this.currentUser.osbbId);
         break;
      default :
        console.log(this.loginService.getRole());
         break;
     }
  }
  

  public openCreateModal() {
    this.createModal.show();
  };

  public ListAllHouses() {
    this.apartment.getAllHouses()
      .subscribe((data) => {
        this.houseList = data;
      },
      (error) => {
        this.handleErrors(error);
      });
  }

  public listManagerHouses(osbbId: number){
      this.apartment.getAllHousesByOsbb(osbbId)
      .subscribe((data) => {
        this.houseList = data;
      },
      (error) => {
        this.handleErrors(error);
      });
  }

  public fillHouse(): number[] {
    let stri: string;
    let tempArr: number[] = [];
    _.map(this.houseList, (hs) => { tempArr.push(hs.houseId); });
    return tempArr;
  }

  public selectedHouse(value: any) {
    this.houseNumber = value.text;
  }

  public getCurrentUser(){
    this.apartment.getUser().subscribe((response) => {
      this.currentUser = response;
    });
  }

  public handleErrors(error) {
    if (error.status === 403) {
      this.toasterService.pop('error', 'user or house already exist');
    }
    if (error.status === 202) {
      this.toasterService.pop('error', 'something wrong with internet connection. cannot send mail');
    }
    if (error.status === 400) {
      this.toasterService.pop('error', 'wrong credentials');
    }
    if (error.status === 500) {
      this.toasterService.pop('error', 'Нажаль, сталася помилка під час реєстрації');
    }
  }
}
