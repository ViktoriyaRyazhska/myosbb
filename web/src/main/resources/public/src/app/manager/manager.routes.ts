import {ManagerComponent} from "./manager.component";
import {RouterConfig} from "@angular/router";
import {TicketManagerComponent} from "./components/ticket/ticket.component.manager";
import {TicketSingleManagerComponent} from "./components/ticket/single.ticket.component.manager";
import {ManagerLoginGuard} from "../../shared/guard/manager.login.guard";

export const managerRoutes: RouterConfig = [
    {
        path: 'manager',
        component: ManagerComponent,
        canActivate: [ManagerLoginGuard],

        children: [
                 
                 {path: 'ticket', component: TicketManagerComponent},
                 {path: 'ticket/:id', component: TicketSingleManagerComponent},
                 {path: '', redirectTo: 'ticket', pathMatch: 'full'}
        ]
    },


];