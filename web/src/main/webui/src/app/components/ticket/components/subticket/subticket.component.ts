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

@Component({
  selector: 'ticket',
  styleUrls: [
    '../../../../../assets/style/page.layout.scss',
    './subticket.style.scss'
  ],
  templateUrl: './subticket.component.html',
  providers: [ SubTicketService, LoginService ]
})

export class SubTicketComponent implements OnInit {
  public ticket: any;
  public title: string = 'Subticket';

  constructor(
    public ticketService: SubTicketService,
    public router: Router,
    public activeRoute: ActivatedRoute
  ) { }

  public ngOnInit() {
    this.activeRoute.params.subscribe((params) => {
      this.ticketService.getTicketData(params['id'])
        .subscribe((response) => {
          this.ticket = response;
        });
    });
  };
}
