import { Component, OnInit, Input} from "@angular/core";
import { Http, Response } from '@angular/http';


@Component(
  {
    selector: 'breadcrumb',
    styleUrls: ['./breadcrumb.scss'],
    templateUrl: './breadcrumb.component.html'
  }
)

export class BreadcrumbComponent {
  public resData: any;
  constructor() {}
  @Input() header: string;
}
