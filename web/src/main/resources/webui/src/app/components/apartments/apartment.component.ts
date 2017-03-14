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
import { FileSelectDirective, FileUploader } from 'ng2-file-upload';
import { UserApartment } from '../../models/userWithApartment.model';
import { ToasterService } from 'angular2-toaster';
import { ModalDirective } from 'ng2-bootstrap/modal';
import { House } from '../../models/house.model';
import { TranslateService } from 'ng2-translate';
import { User } from '../../models/user.model';
import { RegistrationConstants } from '../../registration/registration.constant';
import * as _ from 'lodash';
import { DriveFile } from '../osbb-docs-and-reports/google-drive-service/drive-file.model';
import { OsbbDocumentsAndReportsConstants } from '../osbb-docs-and-reports/osbb-docs-and-reports.constants';
import { API_URL } from '../../../shared/models/localhost.config';

@Component({
  selector: 'apartments',
  templateUrl: 'apartment.component.html',
  styleUrls: ['../../../assets/css/manager.page.layout.scss', './apartment.scss'],
  providers: [ApartmentService, OsbbDocumentsAndReportsConstants]
})

export class ApartmentComponent implements OnInit {

  @ViewChild('createModal')  createModal: ModalDirective;
   @ViewChild('delModal') delModal:ModalDirective;

  public EMAIL_REGEXP = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
  public uploader: FileUploader;
  public title: string = `Apartments`;
  public resData: any;
  private admin: boolean;
  private currentUser: User;
  private authRole: string;
  public userApartment: UserApartment;
  public phoneMask = RegistrationConstants.Masks.phoneMask;
  public houseNumber: number;
  public house: House;
  public chosenHouseApartmentList: any;
  private houseList: House[] = [];
  public files: DriveFile[];
  public tmpPhone: string;


  public apartmentNumberIsValid: boolean;
  public apartmentSquareIsValid: boolean;
  public emailIsValid: boolean;
  public nameIsValid: boolean;
  public surnameIsValid: boolean;
  public ownershipSelected: boolean;
  public phoneIsValid: boolean;
  public itemTypeValid: boolean;
  public itemSizeValid: boolean;

public apartmentDelId: number;

  public rowsOnPage = 10;
  public sortBy = "number";
  public sortOrder = "asc";

  public size: any;


  constructor(
    public http: Http,
    public apartment: ApartmentService,
    public loginService: LoginService,
    public toasterService: ToasterService,
    public translateService: TranslateService,
    private router: Router,
    public docsConsts: OsbbDocumentsAndReportsConstants
  ) {
    this.currentUser = loginService.getUser();
  }


  public ngOnInit() {
    this.getApartments();
    this.ListHouses();
    this.initForm();
  }

  public onSubmitUserApartment() {
    this.createModal.hide();
    this.apartment.registerApartmentWithUser(this.userApartment, this.house.houseId)
      .subscribe(
      (data) => {
        if (this.uploader && this.uploader.queue.length > 0) this.onUpload();
        this.getApartments();
        this.uploader = null;
        this.toasterService.pop('success', '', this.translate('user_appartment_success'));
        // this.initForm();
      },
      (error) => {
        var errorJson = error.json();
        this.handleErrors(errorJson);
      });
  }

  public handleErrors(errorJson) {
    switch (errorJson.message) {
      case "user already exists":
        this.toasterService.pop('error', '', this.translate('user').toUpperCase() + " " + this.translate('exist').toUpperCase());
        break;
      case "apartment with this number in this house already exists":
        this.toasterService.pop('error', '', this.translate('apartment_exist').toUpperCase());
        break;
      case "wrong e-mail":
        this.toasterService.pop('error', '', this.translate('wrong_email').toUpperCase());
        break;
      case "something wrong with internet connection. cannot send mail":
        this.toasterService.pop('error', '', this.translate('server_disconect').toUpperCase());
        break;
      case "Incoming dto is null":
        this.toasterService.pop('error', '', this.translate('null_input').toUpperCase());
        break;
      case "incoming data contains null element on place of required":
        this.toasterService.pop('error', '', this.translate('some_data_null').toUpperCase());
        break;
      case "incoming data contains null where e-mail address supposed to be":
        this.toasterService.pop('error', '', this.translate('email_null').toUpperCase());
        break;
      default:
        this.toasterService.pop('error', '', errorJson.message);
    }
  }

  public onNavigate(id: number) {
    if (this.loginService.getRole() === 'ROLE_ADMIN') {
      this.router.navigate(['admin/apartment', id]);
      return;
    }
    this.router.navigate(['manager/apartment', id]);
  }

  public onChangeHouse(newObj) {
    this.house = newObj;
    this.houseNumber = this.house.houseId;
    this.getApartmentsForChosenHouse(this.houseNumber);
  }



  public ListHouses() {
    switch (this.loginService.getRole()) {
      case 'ROLE_ADMIN':
        this.ListAllHouses();
        break;
      case 'ROLE_MANAGER':
        this.listManagerHouses(this.currentUser.osbbId);
        break;
      default:
        console.log(this.loginService.getRole());
        break;
    }
  }

  public getApartments() {
    switch (this.loginService.getRole()) {
      case 'ROLE_ADMIN':
        this.getAllApartments();
        break;
      case 'ROLE_MANAGER':
        this.getApartmentsForManager(this.currentUser.osbbId);
        break;
      default:
        console.log(this.loginService.getRole());
        break;
    }
  }

  /**
   * getApartmentsForAdmin
   */
  private getAllApartments() {
    this.apartment.getApartmentData().subscribe((data) => {
      this.resData = data;
    });
  }
  /**
   * getApartmentsForManager
   */
  private getApartmentsForManager(osbbId: number) {
    this.apartment.getApartmentDataForManager(osbbId).subscribe((data) => {
      this.resData = data;
    });
  }

  public getApartmentsForChosenHouse(houseId: number) {
    this.apartment.getApartmentsByHouse(houseId).subscribe((data) => {
      this.chosenHouseApartmentList = data;
    });
  }

  delApartment(){
    this.apartment.deleteApartment(this.apartmentDelId).subscribe((data)=>{
      this.toasterService.pop('success', '', this.translate('deleted')); 
      this.getApartments();   
    }) 
  }

  public openCreateModal() {
    this.createModal.show();
  };

   public openDelModal(apartmentId: number):void {
    this.apartmentDelId = apartmentId;
    this.delModal.show();
  }

  public closeDelModal(){
    this.apartmentDelId = null;
    this.delModal.hide();
  }

  public initForm() {
    this.userApartment = new UserApartment();
    this.emailIsValid = true;
    this.nameIsValid = true;
    this.surnameIsValid = true;
    this.apartmentNumberIsValid = true;
    this.apartmentSquareIsValid = true;
    this.ownershipSelected = true;
    this.phoneIsValid = true;
    this.itemTypeValid = true;
    this.itemSizeValid = true;
  };

  private ListAllHouses() {
    this.apartment.getAllHouses()
      .subscribe((data) => {
        this.houseList = data;
      },
      (error) => {
        this.handleErrors(error);
      });
  }

  private listManagerHouses(osbbId: number) {
    this.apartment.getAllHousesByOsbb(osbbId)
      .subscribe((data) => {
        this.houseList = data;
      },
      (error) => {
        this.handleErrors(error);
      });
  }



  public clearQueue() {
    this.uploader.clearQueue();
  }

  private onUpload() {
    if (this.itemTypeValid && this.itemSizeValid) {
      this.uploader.queue.forEach((item) => {
        item.upload();
      });
    }
  }

  public initUploader(email: string) {
    this.uploader = new FileUploader({
      url: API_URL + '/restful/house/upload/' + email,
      authToken: 'Bearer ' + localStorage.getItem('access_token'),
    });
  }


  private translate(message: string): string {
    let translation: string;
    this.translateService.get(message).subscribe(
      (data) => translation = data
    );
    return translation;
  }

  // Validation

  public isValidMailFormat() {
    if (this.userApartment.userRegitrationByAdminDTO.email === "" || (this.userApartment.userRegitrationByAdminDTO.email.length <= 5 || !this.EMAIL_REGEXP.test(this.userApartment.userRegitrationByAdminDTO.email))) {
      this.emailIsValid = false;
      this.uploader = null;
      return false;
    }
    this.emailIsValid = true;
    this.uploader = null;
    return true;
  }

  public clearPhoneMask() {
    this.userApartment.userRegitrationByAdminDTO.phoneNumber = this.tmpPhone.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, "").substr(0, 12);
    this.validatePhoneNumber();
  }

  public validateItemType() {
    this.uploader.queue.forEach((item) => {
      if (item.file.type.match('image.*')) {
        this.itemTypeValid = true;
      }
      else {
        this.itemTypeValid = false;
      }
    });
  }

  public validateItemSize() {
    this.uploader.queue.forEach((item) => {
      if (item.file.size < 131072) {
        this.itemSizeValid = true;
      }
      else {
        this.itemSizeValid = false;
      }
    });
  }

  public validatePhoneNumber() {
    if (this.userApartment.userRegitrationByAdminDTO.phoneNumber && this.userApartment.userRegitrationByAdminDTO.phoneNumber.length == 12) {
      this.phoneIsValid = true;
    }
    else this.phoneIsValid = false;
  }

  public validateName() {
    if (this.userApartment.userRegitrationByAdminDTO.firstName && this.userApartment.userRegitrationByAdminDTO.firstName.length > 1) {
      this.nameIsValid = true;
    }
    else this.nameIsValid = false;
  }

  public validateLastName() {
    if (this.userApartment.userRegitrationByAdminDTO.lastName && this.userApartment.userRegitrationByAdminDTO.lastName.length > 1) {
      this.surnameIsValid = true;
    }
    else this.surnameIsValid = false;
  }

  public validateApartmentNumber() {
    if (this.userApartment.apartment.number && this.userApartment.apartment.number > 0) {
      this.apartmentNumberIsValid = true;
    }
    else this.apartmentNumberIsValid = false;
  }

  public validateApartmentSquare() {
    if (this.userApartment.apartment.square && this.userApartment.apartment.square > 10) {
      this.apartmentSquareIsValid = true;
    }
    else this.apartmentSquareIsValid = false;
  }

  public isFormValid() {
    if (this.house && this.userApartment.userRegitrationByAdminDTO.ownership) {
      return (this.phoneIsValid && this.emailIsValid && this.apartmentNumberIsValid && this.surnameIsValid && this.nameIsValid && this.apartmentSquareIsValid);
    }
    return false;
  }

  }
