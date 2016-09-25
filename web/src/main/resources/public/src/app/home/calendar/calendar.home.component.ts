import {Component} from "@angular/core";
import {UserCalendarComponent} from "./../../user/calendar/user.calendar.component";

@Component({
    selector: 'calendar-home',
    template: `
    <div class="row sb-backdown">
                <my-calendar>
                </my-calendar>
    </div>
    `,
    styleUrls: [ 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    directives: [UserCalendarComponent]
})
export class CalendarHomeComponent {

}
