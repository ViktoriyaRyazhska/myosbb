import {Component, OnInit} from '@angular/core'
import {User} from '../../../../shared/models/User';
import {UsersService} from "./users.service";
import {TranslatePipe} from "ng2-translate";
import {CapitalizeFirstLetterPipe} from "../../../../shared/pipes/capitalize-first-letter";
import {Router} from '@angular/router';
import {REACTIVE_FORM_DIRECTIVES, FormBuilder, Validators} from '@angular/forms';


@Component({
    selector: 'my-users',
    templateUrl: 'src/app/admin/components/users/users.table.html',
    providers: [UsersService],
    styleUrls: ['src/app/admin/components/users/users.css'],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    directives: [REACTIVE_FORM_DIRECTIVES]
})
export class UsersComponent implements OnInit {
    userList:User[];
    userForm:any;

    constructor(private _userService:UsersService, private router:Router, private formBuilder:FormBuilder) {
        console.log('constructore');
        this.userList = [];
        this.userForm = this.formBuilder.group({
            'firstName': ['', Validators.required],
            'lastName': ['', Validators.required],
            'email': ['', [Validators.required,]],
            'phoneNumber': ['', Validators.required],
            'gender': ['', Validators.required],
            'birthDate': ['', Validators.required],
            'password': ['', Validators.required],
        });
    }

    ngOnInit():any {
        console.log('init');
        this._userService.getAllUsers().subscribe(data => this.userList = data, error=>console.error(error));
        console.log('get out of service');
    }

    updateUser(user:User) {
        this._userService.updateUser(user).subscribe(()=>this.router.navigate(['admin/users']));
    }

    deleteUser(user:User) {
        this._userService.deleteUser(user).subscribe(()=>this.userList.splice(this.userList.indexOf(user, 0), 1));
    }

    saveUser() {
        let user:User = this.userForm;
        this._userService.saveUser(user).subscribe((data)=>this.userList.push(data));
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

}