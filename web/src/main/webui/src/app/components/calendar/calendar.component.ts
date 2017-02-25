import {Component} from '@angular/core';

@Component({
    selector: 'calendar',
    templateUrl:'calendar.component.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss']
})

export class CalendarComponent {
  public title: string = `Calendar`;
}
