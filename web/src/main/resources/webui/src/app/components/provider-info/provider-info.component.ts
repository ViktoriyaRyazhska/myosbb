import {Component,OnInit} from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { LoginService } from '../../shared/login/login.service';
import { Subscription } from 'rxjs';
import { ProviderInfoService } from "./provider-info.service";

@Component({
    providers: [ProviderInfoService, LoginService],
    selector: 'provider-info',
    templateUrl: './provider-info.template.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss'],
})
export class ProviderAboutComponent implements OnInit {
    public provider:string[];
    public providerId: number;
    private sub: Subscription;
    constructor(
        public route: ActivatedRoute, public http: Http, public providerInfoService: ProviderInfoService
    ) {}
    public ngOnInit(): any {
        this.sub = this.route.params.subscribe((params) => {
            return this.providerId = +params['id'];
        });
        this.providerInfoService.getProvider(this.providerId)
            .subscribe((data) => {
                this.provider = Array.of(data);
            });
    }
};
