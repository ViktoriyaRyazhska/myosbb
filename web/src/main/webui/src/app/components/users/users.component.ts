import {
    Component,
    OnInit
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Http, Response } from '@angular/http';
import { UsersService } from './users.service';
import { Observable } from 'rxjs/Observable';
import { LoginService } from '../../shared/login/login.service';

@Component({
    providers: [UsersService, LoginService],
    selector: 'users',
    templateUrl: './users.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss'],
})
export class UsersComponent implements OnInit {
    public localState: any;
    public users: string[];

    constructor(
        public route: ActivatedRoute, public http: Http, public usersService: UsersService
    ) {}

    public ngOnInit() {
        this.usersService.getUsers().subscribe((data) => {
            this.users = data;
        });
    }
};
