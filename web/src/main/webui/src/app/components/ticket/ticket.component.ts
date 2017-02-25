import {Component, OnInit} from '@angular/core';
import {
  Http,
  Response
} from '@angular/http';
import {
  Router,
  ActivatedRoute
} from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/switchMap';
import { TicketService } from './ticket.service';
import { LoginService } from '../../shared/login/login.service';

@Component({
    selector: 'ticket',
    styleUrls: ['../../../assets/css/manager.page.layout.scss', './ticket.page.scss'],
    templateUrl: 'tickets.component.html',
  providers: [ TicketService, LoginService ]
})

export class TicketComponent implements OnInit {
  public resData: any;
  public title: string = 'Tickets';
  constructor(
    private http: Http,
    public login: LoginService,
    public contract: TicketService,
    public router: Router,
    public activeRoute: ActivatedRoute
  ) {  }
  public ngOnInit() {
    this.contract.getTicketData().subscribe(data => {
      this.resData = data;
    })
   };

  public subTicketNavigation(ticket: any) {
    this.router.navigate([`./manager/ticket/`, ticket])
  };
}
