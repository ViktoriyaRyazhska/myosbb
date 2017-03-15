import {
    Component,
    OnInit
} from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Http, Response } from '@angular/http';
import { ApartmentService } from './apartment.service';
import { Observable } from 'rxjs/Observable';
import { LoginService } from '../../shared/login/login.service';
import { Subscription } from 'rxjs';
import { API_URL } from '../../../shared/models/localhost.config';

@Component({
    providers: [ApartmentService, LoginService],
    selector: 'apartment',
    templateUrl: './apartment.template.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss', './apartment.scss'],
})
export class ApartmentAboutComponent implements OnInit {
    public apartment: string[];
    public apartmentId: number;
    private sub: Subscription;
    public users: string[];
    public owner: any;
    public imgUrl: string;

    constructor(
        public route: ActivatedRoute,
        public http: Http,
        public ApartmentService: ApartmentService
    ) { }

    public ngOnInit(): any {
        this.sub = this.route.params.subscribe((params) => {
            return this.apartmentId = +params['id'];
        });
        this.initApartment();
        this.initUsers();
        this.initOwner();
    }

    public initApartment() {
        this.ApartmentService.getApartment(this.apartmentId)
            .subscribe((data) => {
                this.apartment = Array.of(data);
            });
    }

    public initUsers() {
        this.ApartmentService.getUsers(this.apartmentId).subscribe((data) => {
            this.users = data;
        });
    }

    public initOwner() {
        this.ApartmentService.getOwner(this.apartmentId).subscribe((data) => {
            this.owner = data;
            this.initImgUrl();
        },
            (error) => {
                console.log(error);
            });
    }

    public initImgUrl() {
        if (this.owner != null) {
            this.imgUrl = API_URL + "/restful/house/image/" + this.owner.photoId;
        }
    }
}