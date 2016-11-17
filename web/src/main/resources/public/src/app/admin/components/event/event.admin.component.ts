import { Component } from "@angular/core";
import { EventComponent } from "../../../event/event.component";
import { ROUTER_DIRECTIVES } from "@angular/router";

@Component(
    {
        selector: 'admin-event',
        templateUrl: 'src/app/admin/components/event/event.admin.html',
        directives: [EventComponent, ROUTER_DIRECTIVES]
    }
)

export class EventAdminComponent {

}