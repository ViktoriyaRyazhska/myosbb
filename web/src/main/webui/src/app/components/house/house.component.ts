import {
    Component,
    OnInit
} from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Http, Response } from '@angular/http';
import { HouseService } from './house.service';
import { Observable } from 'rxjs/Observable';
import { LoginService } from '../../shared/login/login.service';
import { Subscription } from 'rxjs';

@Component({
    providers: [HouseService, LoginService],
    selector: 'house',
    templateUrl: './house.template.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss'],
})
export class HouseAboutComponent implements OnInit {
    public house: string[];
    public houseId: number;
    private sub: Subscription;
    constructor(
        public route: ActivatedRoute, public http: Http, public HouseService: HouseService
    ) {}
    public ngOnInit(): any {
        this.sub = this.route.params.subscribe((params) => {
            return this.houseId = +params['id'];
        });
        this.HouseService.getHouse(this.houseId)
            .subscribe((data) => {
                this.house = Array.of(data);
            });
    }
};
