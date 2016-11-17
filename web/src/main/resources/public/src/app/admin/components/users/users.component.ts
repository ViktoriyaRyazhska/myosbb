import { Component, OnInit, Input } from '@angular/core';
import { User } from '../../../../shared/models/User';
import { Role } from '../../../../shared/models/role';
import { Osbb, IOsbb } from "../../../../shared/models/osbb";
import { IHouse } from "../../../../shared/models/House";
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


@Component({ 
    selector: 'my-users',
    templateUrl: 'src/app/admin/components/users/users.table.html',
    providers: [UsersService, RegisterService],
    styleUrls: ['src/app/admin/components/users/users.css'],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    directives: [REACTIVE_FORM_DIRECTIVES, RegistrationComponent,  SELECT_DIRECTIVES]
})
export class UsersComponent implements OnInit {
    
    userList:User[];
    roles: Array<string> = [];
    userMy: UserRegistration = new UserRegistration();
    osbbMy: OsbbRegistration = new OsbbRegistration();
    private isSelectedOsbb: boolean = false;
    private isSelectedHouse: boolean = false;
    private isSelectedApartment: boolean = false;
    private IsRegistered: boolean;
    private IsRegisteredOsbb: boolean;
    private isJoinedOsbb: boolean;
    private roleList: Role[] = [];
    private osbbList: IOsbb[] = [];
    private houseList: IHouse[] = [];
    private apartmentList: IApartment[] = [];
    private osbb: Array<string> = [];
    private houses: Array<string> = [];
    private apartment: Array<string> = [];
    genders = [
        'male',
        'female'
    ];    

    constructor(private _userService:UsersService, private router:Router, private formBuilder:FormBuilder,
        private registerService: RegisterService) {
        console.log('constructore');
        this.userMy.activated = true;
        this.IsRegistered = false;
        this.isJoinedOsbb = true;
        this.IsRegisteredOsbb = false;
        this.userList = [];
    }

    ngOnInit():any {
        console.log('init');
        this._userService.getAllUsers().subscribe(data => this.userList = data, error=>console.error(error));
         this.listAllOsbb();
         this.listAllRoles();
        console.log('get out of service');
    }

    updateUser(user:User) {
        this._userService.updateUser(user).subscribe(()=>this.router.navigate(['admin/users']));
    }

    deleteUser(user:User) {
        this._userService.deleteUser(user).subscribe(()=>this.userList.splice(this.userList.indexOf(user, 0), 1));
    }

    saveUser(form: NgForm ) {
        console.log(this.userMy);
        this._userService.saveUser(this.userMy).subscribe((data)=>this.userList.push(data));
    }

    public changeActivation(user:User) {
        this._userService.changeActivation(user).subscribe(
            data=> {
                user.activated?user.activated=false:user.activated=true
            },
            error=> {
                console.log(error);
            }
        )
    }

    toUser(id:number){
        this.router.navigate(['admin/user', id]);
    }

    selectedOsbb(value: any) {
        this.isSelectedOsbb = true;
        console.log('select osbb: ', value);
        let selectedOsbb: Osbb = this.getOsbbByName(value.text);
        this.userMy.osbbId = selectedOsbb.osbbId;
        this.listAllHousesByOsbb(this.userMy.osbbId);
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
        this.userMy.apartmentId = selectedApartmentID;
        console.log('select apartment: ' + selectedApartmentID);
        console.log(JSON.stringify(this.userMy));
    }

    selectedRole(value: any) {
        let selectedRole: Role = this.getRoleByName(value.text);
        this.userMy.role = selectedRole.roleId;
    }

    listAllOsbb() {
        this.registerService.getAllOsbb()
            .subscribe((data)=> {
                this.osbbList = data;
                this.osbb = this.fillOsbb();
                console.log('all osbb names', this.osbb);
            });
    }

    listAllRoles() {
        this._userService.listAllRoles()
        .subscribe((data) => {
            this.roleList = data;
            this.roles = this.fillRoles();
            console.log('all role names', this.roles);
        });
    }

    listAllHousesByOsbb(id: number) {

        this.registerService.getAllHousesByOsbb(id)
            .subscribe((data)=> {
                    this.houseList = data;
                    this.houses = this.fillHouses();
                    console.log('all houses', this.houses);
                })
    }

    listAllApartmentsByHouse(houseId: number) {
        this.registerService.getAllApartmentsByHouse(houseId)
            .subscribe((data)=> {
                this.apartmentList = data;
                this.apartment = this.fillApartment();
                console.log('all apartment', this.apartment);
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

    fillRoles(): string[] {
        let tempArr: string[] = [];
        for (let roleObject of this.roleList) {
            tempArr.push(roleObject.name);
        }
        console.log(tempArr)
        return tempArr;
    }

}