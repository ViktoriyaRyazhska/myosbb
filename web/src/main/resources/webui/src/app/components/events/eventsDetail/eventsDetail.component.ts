import {
    Component,
    OnInit
} from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Http, Response } from '@angular/http';
import { EventsDetailService } from './eventsDetail.service';
import { Observable } from 'rxjs/Observable';
import { Subscription } from 'rxjs';
import { LoginService } from '../../../shared/login/login.service';

@Component({
    providers: [EventsDetailService, LoginService],
    selector: 'eventsDetail',
    templateUrl: './eventsDetail.html',
    styleUrls: ['../../../../assets/css/manager.page.layout.scss'],
})
export class EventsDetailComponent implements OnInit {
    public localState: any;
    public eventsDetail: string[];
    public eventId: any;
    private sub: Subscription;
    constructor(
        public route: ActivatedRoute,
        public http: Http,
        public EventsDetailService: EventsDetailService
    ) {}

    public ngOnInit(): any {
        this.sub = this.route.params.subscribe((params) => {
            this.eventId = +params['id'];
            this.EventsDetailService.getEventsDetail(this.eventId)
                .subscribe((data) => {
                    this.eventsDetail = Array.of(data);
                });
        });
    }
}
