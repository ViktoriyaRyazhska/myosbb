import {
  Component,
  OnInit
} from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { WallService } from './wall.service';
import { LoginService } from '../../shared/login/login.service';

@Component({
  selector: 'wall',
  providers: [WallService, LoginService],
  styleUrls: ['../../../assets/css/manager.page.layout.scss'],
  templateUrl: './wall.template.html'
})

export class WallComponent implements OnInit {
  public resData: any;
  constructor(
    public http: Http,
    public wall: WallService
  ) {}
  public ngOnInit() {
    this.wall.getWallData().subscribe(data => {
      this.resData = data;
    });
  }
}
