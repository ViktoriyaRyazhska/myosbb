import {Component} from "@angular/core";
import {ROUTER_DIRECTIVES} from "@angular/router";
import {CapitalizeFirstLetterPipe} from "../../../shared/pipes/capitalize-first-letter";
import {TranslatePipe} from "ng2-translate";
@Component({
    selector: 'manager-sidebar-menu',
    templateUrl: 'src/app/manager/sidebar_menu/sidebar_menu.manager.html',
    directives: [ROUTER_DIRECTIVES],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe]
})
export class ManagerSidebarMenuComponent {

}