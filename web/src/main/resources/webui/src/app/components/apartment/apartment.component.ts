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

@Component({
    providers: [ApartmentService, LoginService],
    selector: 'apartment',
    templateUrl: './apartment.template.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss'],
})
export class ApartmentAboutComponent implements OnInit {
    public apartment: string[];
    public apartmentId: number;
    private sub: Subscription;
    constructor(
        public route: ActivatedRoute, public http: Http, public ApartmentService: ApartmentService
    ) {}
    public ngOnInit(): any {
        this.sub = this.route.params.subscribe((params) => {
            return this.apartmentId = +params['id'];
        });
        this.ApartmentService.getApartment(this.apartmentId)
            .subscribe((data) => {
                this.apartment = Array.of(data);
            });
    }
};