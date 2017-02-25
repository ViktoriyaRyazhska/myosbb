import {
  Component,
  OnInit
} from '@angular/core';
import { Http,
  Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { HouseService } from './house.service';

@Component(
  {
    selector: 'houses',
    templateUrl: 'house.component.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss', './house.scss'],
    providers: [ HouseService ]
  }
)

export class HouseComponent implements OnInit{
  public title: string = `Houses`;
  public resData: any;

  constructor(
    public http: Http,
    public house: HouseService
  ) { }

  public ngOnInit() {
    this.house.getHouseData().subscribe(data => {
      this.resData = data;
    });
  }
}
