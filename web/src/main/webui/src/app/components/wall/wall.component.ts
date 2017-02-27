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
import { API_URL } from '../../../assets/models/localhost.config';

@Component({
  selector: 'wall',
  providers: [
    WallService,
    LoginService
  ],
  styleUrls: [
    '../../../assets/style/page.layout.scss',
    './wall.style.scss'
  ],
  templateUrl: './wall.template.html',
})

export class WallComponent implements OnInit {
  public resData: any;
  public imageLink: string = API_URL;

  constructor(
    public http: Http,
    public wall: WallService
  ) { }

  public ngOnInit() {
    this.wall.getWallData().subscribe((response) => {
      this.resData = response;
    });
  };
}
