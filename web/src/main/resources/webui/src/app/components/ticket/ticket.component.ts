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
import { PageRequest } from '../../models/pageRequest.model';
import {User} from '../../models/user.model';
@Component({
    selector: 'ticket',
    styleUrls: [
      '../../../assets/css/page.layout.scss',
      './ticket.page.scss'
    ],
    templateUrl: 'tickets.component.html',
    providers: [
      TicketService
    ]
})

export class TicketComponent implements OnInit {
  public resData: any;
  public pageRequest = new PageRequest(1, 10, 'time', false);
  public title: string = 'Tickets';
  private str:string;
  private user: User;

  constructor(
    public ticketService: TicketService,
    public router: Router,
    public loginService:LoginService
  ) {
     this.user=loginService.currentUser;
   }

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
    console.log("subTicketNavigation start");
    switch (this.loginService.getRole()) {
      case 'ROLE_USER':
        this.router.navigate([`./user/ticket/`, ticket]);
        break;
      case 'ROLE_ADMIN':
       this.router.navigate([`./admin/ticket/`, ticket]);
        break;
       case 'ROLE_MANAGER':
        this.router.navigate([`./manager/ticket/`, ticket]);
         break;
      default :
        console.log(this.loginService.getRole.arguments);
         break;
     }
     console.log("subTicketNavigation end");
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
