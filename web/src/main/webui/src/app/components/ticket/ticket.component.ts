import {
  Component,
  OnInit
} from '@angular/core';
import {
  Router,
  ActivatedRoute
} from '@angular/router';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/switchMap';
import { TicketService } from './ticket.service';
import { LoginService } from '../../shared/login/login.service';
import { PageRequest } from '../../../assets/models/pageRequest.interface';

@Component({
    selector: 'ticket',
    styleUrls: [
      '../../../assets/style/page.layout.scss',
      './ticket.page.scss'
    ],
    templateUrl: 'tickets.component.html',
    providers: [
      TicketService,
      LoginService
    ]
})

export class TicketComponent implements OnInit {
  public resData: any;
  public pageRequest = new PageRequest(1, 10, 'time', false);
  public title: string = 'Tickets';

  constructor(
    public ticketService: TicketService,
    public router: Router
  ) { }

  public ngOnInit() {
    this.ticketService.getTicketData()
      .subscribe((response) => {
        this.resData = response.rows;
      });
  };

  public findTicketByState(state: string) {
    this.ticketService.findByState(state)
      .subscribe((response) => {
        this.resData = response.rows;
      });
  };

  public subTicketNavigation(ticket: any) {
    this.router.navigate([`./manager/ticket/`, ticket]);
  };

  public findMyAssigned() {
    this.ticketService.findByAssigned(this.pageRequest,
      localStorage.getItem('email'), status)
      .subscribe((response) => {
        this.resData = response.rows;
      });
  };

  public findMyTickets() {
    this.ticketService.getTicketData()
      .subscribe((response) => {
        this.resData = response.rows;
      });
  };

  public getTicketsByPageNum() {
    this.ticketService.getTicketData()
      .subscribe((response) => {
        this.resData = response.rows;
      });
  };
}
