import { ManagerComponent } from "./manager.component";
import { RouterConfig } from "@angular/router";
import { TicketManagerComponent } from "./components/ticket/ticket.component.manager";
import { TicketSingleManagerComponent } from "./components/ticket/single.ticket.component.manager";
import { ManagerLoginGuard } from "../../shared/guard/manager.login.guard";
import { OsbbBillComponent } from "./../user/bills/osbb/osbb.bill.component";
import { ProviderInfoComponent } from "./../user/provider/provider-info";
import { EventComponent } from "../event/event.component";
import { EventShowComponent } from "../event/event.show.component";
import { HouseTableManagerComponent } from "./components/house/house-table.manager.component";
import { HouseShowManagerComponent } from "./components/house/house-show.manager.component";
import { HomeWallComponent } from "./../home/home_wall/home.wall.component";
import { ContractComponent } from "./../user/contract/contract.component";
import { CalendarHomeComponent } from "../home/calendar/calendar.home.component";
import { UserApartmentComponent } from "../home/components/apartment/user.apartment.component";
import { ApartmentProfileComponent } from "../home/components/ApartmentProfile/apartment.profile";
import { UserProfileManagerComponent } from "./components/user/user.profile.manager.component";
import { OsbbContactsComponent } from "../home/osbb-contacts/osbb-contacts.component";
import { OsbbDocumentsAndReportsComponent } from "../home/osbb-docs-and-reports/osbb-docs-and-reports.component";
import {ProviderManagerComponent} from "./components/provider/provider.component.manager";

export const managerRoutes:RouterConfig = [
    {
        path: 'manager',
        component: ManagerComponent,
        canActivate: [ManagerLoginGuard],

        children: [ 
            { path: 'wall', component: HomeWallComponent },
            { path: '', redirectTo: 'wall', pathMatch: 'full' },
            { path: 'apartment', component: UserApartmentComponent },
            { path: 'apartment/apartmentprofile/:id', component: ApartmentProfileComponent },
            { path: 'houses', component: HouseTableManagerComponent },
            { path: 'houses/:id', component: HouseTableManagerComponent },
            { path: 'house/:id', component: HouseShowManagerComponent },
            { path: 'ticket', component: TicketManagerComponent },
            { path: 'ticket/:id', component: TicketSingleManagerComponent },
            { path: 'osbb/bill', component: OsbbBillComponent },
            { path: 'provider', component: ProviderManagerComponent },
            { path: 'provider/info/:id', component: ProviderInfoComponent },
            { path: 'events', component: EventComponent },
            { path: 'event/:id', component: EventShowComponent },
            { path: 'calendar', component: CalendarHomeComponent },
            { path: 'contract', component: ContractComponent },
            { path: 'friend/:id', component: UserProfileManagerComponent },
            { path: 'osbb/contacts', component: OsbbContactsComponent },
            { path: 'osbb/documents-and-reports', redirectTo: 'osbb/documents-and-reports/1', pathMatch: 'full' },
            { path: 'osbb/documents-and-reports/:id', component: OsbbDocumentsAndReportsComponent }
        ]
    },
];
