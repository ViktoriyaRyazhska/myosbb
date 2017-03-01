import {
    Component,
    OnInit
} from '@angular/core';
import {
    Http,
    Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { HouseService } from './house.service';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from '../../shared/login/login.service';

@Component({
    selector: 'houses',
    templateUrl: 'house.component.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss', './house.scss'],
    providers: [HouseService, LoginService]
})

export class HouseComponent implements OnInit {
    public title: string = `Houses`;
    public resData: any;
    private admin: boolean;
    private authRole: string;

    constructor(
        public http: Http,
        public house: HouseService,
        public LoginService: LoginService,
        private router: Router
    ) {}
    public onNavigate(id: number) {
        if (this.authRole === 'ROLE_ADMIN') {
            this.router.navigate(['admin/house', id]);
            return;
        }
        this.router.navigate(['manager/house', id]);
    }
    public ngOnInit() {
        this.house.getHouseData().subscribe((data) => {
            this.resData = data;
        });
        this.LoginService.setRole();
        this.authRole = this.LoginService.getRole();
    }
}
