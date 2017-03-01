import {
    Component,
    OnInit
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Http, Response } from '@angular/http';
import { OsbbService } from './osbb.service';
import { Observable } from 'rxjs/Observable';

@Component({
    providers: [OsbbService],
    selector: 'osbb',
    templateUrl: './osbb.template.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss'],
})
export class OSBBComponent implements OnInit {
    public OSBB: string[];
    public localState: any;
    constructor(
        public route: ActivatedRoute, public http: Http, public osbbService: OsbbService
    ) {}

    public ngOnInit() {
        this.osbbService.getOSBB().subscribe((data) => {
            this.OSBB = data;
        });
    }
}
