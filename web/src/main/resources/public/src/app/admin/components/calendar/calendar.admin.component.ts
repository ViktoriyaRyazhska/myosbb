import { Component } from "@angular/core";
import { UserCalendarComponent } from "../../../user/calendar/user.calendar.component";
import { ROUTER_DIRECTIVES } from "@angular/router";

@Component(
    {
        selector: 'admin-calendar',
        templateUrl: 'src/app/admin/components/calendar/calendar.admin.html',
        directives: [UserCalendarComponent, ROUTER_DIRECTIVES]
    }
)

export class CalendarAdminComponent {

}