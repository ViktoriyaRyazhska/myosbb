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

  @ViewChild('createModal') public createModal: ModalDirective;

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


  public rowsOnPage = 10;
  public sortBy = "number";
  public sortOrder = "asc";


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
        console.log("data");
        if (this.uploader) this.onUpload();
        this.getApartments();
        this.uploader = null;
        this.toasterService.pop('success', '', 'Користувача і квартиру було успішно зареєстроване!');
        this.initForm();
      },
      (error) => {
        console.log(error.json());
        var errorJson = error.json();
        this.toasterService.pop('error', error.status, errorJson.message);
      });
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
  public getAllApartments() {
    this.apartment.getApartmentData().subscribe((data) => {
      this.resData = data;
    });
  }
  /**
   * getApartmentsForManager
   */
  public getApartmentsForManager(osbbId: number) {
    this.apartment.getApartmentDataForManager(osbbId).subscribe((data) => {
      this.resData = data;
    });
  }

  public getApartmentsForChosenHouse(houseId: number) {
    this.apartment.getApartmentsByHouse(houseId).subscribe((data) => {
      this.chosenHouseApartmentList = data;
    });
  }

  public openCreateModal() {
    this.createModal.show();
  };

  public initForm() {
    this.userApartment = new UserApartment();
    this.emailIsValid = true;
    this.nameIsValid = true;
    this.surnameIsValid = true;
    this.apartmentNumberIsValid = true;
    this.apartmentSquareIsValid = true;
    this.ownershipSelected = true;
    this.phoneIsValid = true;
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

  public listManagerHouses(osbbId: number) {
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


  public handleErrors(error) {
    var errorJson = error.json;
    if (error.status === 403) {
      this.toasterService.pop('error', '403', errorJson.message);
    }
    if (error.status === 202) {
      this.toasterService.pop('error', '202', errorJson.message);
    }
    if (error.status === 400) {
      this.toasterService.pop('error', '', errorJson.message);
    }
    if (error.status === 500) {
      this.toasterService.pop('error', '', 'Нажаль, сталася помилка під час реєстрації');
    }
  }

  public clearQueue() {
    this.uploader.clearQueue();
  }

  public onUpload() {
    this.uploader.queue.forEach((item) => {
      item.upload();
    });
  }

  public initUploader(email: string) {
    this.uploader = new FileUploader({
      url: API_URL + '/restful/house/upload/' + email,
      authToken: 'Bearer ' + localStorage.getItem('access_token'),
    });
  }



  public translate(message: string): string {
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
   this.userApartment.userRegitrationByAdminDTO.phoneNumber = this.tmpPhone.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, "").substr(0,12);
    this.validatePhoneNumber();
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
