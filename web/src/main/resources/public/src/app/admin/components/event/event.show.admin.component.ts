import {Component} from "@angular/core";
import {EventShowComponent} from "../../../event/event.show.component";
@Component(
    {
        selector: 'admin-event-show',
        templateUrl: 'src/app/admin/components/event/event.show.admin.html',
        directives:[EventShowComponent]
    }
)
export class EventShowAdminComponent {

}