import {Component} from "@angular/core";
import {ROUTER_DIRECTIVES} from "@angular/router";
import {ManagerSidebarMenuComponent} from "./sidebar_menu/sidebar_menu.manager.component";
@Component({
    selector: 'my-manager',
    templateUrl: 'src/app/manager/manager.html',
    directives: [ROUTER_DIRECTIVES, ManagerSidebarMenuComponent]
})
export class ManagerComponent {

}