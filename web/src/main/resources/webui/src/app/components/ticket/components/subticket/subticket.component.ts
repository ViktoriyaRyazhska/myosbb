import {
  Component,
  OnInit
} from '@angular/core';
import {
  Http,
} from '@angular/http';
import {
  Router,
  ActivatedRoute
} from '@angular/router';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { SubTicketService } from './subticket.service';
import { LoginService } from '../../../../shared/login/login.service';
import {Ticket, ITicket,TicketState} from '../../../../models/ticket.model';
import {User} from "../../../../models/user.model";
@Component({
  selector: 'ticket',
  styleUrls: [
    '../../../../../assets/css/page.layout.scss',
    './subticket.style.scss'
  ],
  templateUrl: './subticket.component.html',
  providers: [ SubTicketService ]
})

export class SubTicketComponent implements OnInit {
  public ticket: any;
  public title: string = 'Subticket';
  public ticketState:string;
  public currentUser:User;

  constructor(
    public ticketService: SubTicketService,
    public router: Router,
    public activeRoute: ActivatedRoute,
    public subTicketService:SubTicketService,
    public loginService:LoginService
  ) { 
    this.currentUser=this.loginService.getUser();
    console.log(this.currentUser);
  }

  public ngOnInit() {
    this.activeRoute.params.subscribe((params) => {
      this.ticketService.getTicketData(params['id'])
        .subscribe((response) => {
          this.ticket = response;
          this.ticketState=this.ticket.state;
        });
    });
  };

  editState(state:string) {
      switch (state) {
      case 'IN_PROGRESS':
            this.ticket.state = TicketState.IN_PROGRESS;
            this.ticketState = 'IN_PROGRESS';
        break;
      case 'DONE':
            this.ticket.state = TicketState.DONE;
            this.ticketState = 'DONE';
        break;
       case 'NEW':
            this.ticket.state = TicketState.NEW;
            this.ticketState = 'NEW';
         break;
      default :
         break;
     }
        this.subTicketService.editState(this.ticket);
    }

}
