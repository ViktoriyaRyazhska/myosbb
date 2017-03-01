import {
  Component,
  OnInit,
  Input
} from '@angular/core';
import { LoginService } from '../../shared/login/login.service';

@Component({
    selector: 'breadcrumb',
    styleUrls: ['./breadcrumb.scss'],
    templateUrl: './breadcrumb.component.html',
    providers: [ LoginService ]
})

export class BreadcrumbComponent {
  public resData: any;
  @Input() public header: string;
}
