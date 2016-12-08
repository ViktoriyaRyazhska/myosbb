import { RouterConfig } from "@angular/router";
import { HomeComponent } from "./home.component";
import { OsbbComponent } from "../admin/components/osbb/osbb.component";
import { TicketAdminComponent } from "../user/ticket/ticket.component.admin";
import { HouseTableComponent } from "../house/house.table.component";
import { HouseShowComponent } from "../house/house.show.component";
import { TicketSingleAdminComponent } from "../user/ticket/single_ticket/single.ticket.component.admin";
import { HomeWallComponent } from "./home_wall/home.wall.component";
import { ProviderUserPageWrapperComponent } from "../user/provider/provider_home/provider-user-page.component.wrapper";
import { EventComponent } from "../event/event.component";
import { EventShowComponent } from "../event/event.show.component";
import { CalendarHomeComponent } from "./calendar/calendar.home.component";
import { UserProfileComponent } from "./user/user.profile.component";
import { ApartmentProfileComponent } from "../home/components/ApartmentProfile/apartment.profile";
import { UserApartmentComponent } from "../home/components/apartment/user.apartment.component";
import { ProviderInfoComponent } from "../user/provider/provider-info";
import { UserReportComponent } from "../user/report/user.report.component";
import { ElectricityComponen } from "../home/components/apartment/submenu/electricity/electricity.component";
import { GasComponen } from "../home/components/apartment/submenu/gas/gas.component";
import { WaterComponen } from "../home/components/apartment/submenu/water/water.component";
import { CustomserviceComponent } from "../home/components/apartment/submenu/customservice/customservice.component";
import { SubbillComponent } from "../home/components/apartment/submenu/customservice/subbill/subbill.component";
import { OsbbContactsComponent } from "./osbb-contacts/osbb-contacts.component";
import { OsbbDocumentsAndReportsComponent } from "./osbb-docs-and-reports/osbb-docs-and-reports.component";

export const homeRoutes: RouterConfig = [
    {
        path: 'home', component: HomeComponent,

        children: [
            { path: '', redirectTo: 'wall', pathMatch: 'full' },
            { path: 'wall', component: HomeWallComponent },
            { path: 'events', component: EventComponent },
            { path: 'event/:id', component: EventShowComponent },
            { path: 'calendar', component: CalendarHomeComponent },
            { path: 'osbb', component: OsbbComponent },
            { path: 'provider/info', component: ProviderUserPageWrapperComponent },
            { path: 'provider/info/:id', component: ProviderInfoComponent },
            { path: 'houses', component: HouseTableComponent },
            { path: 'house/:id', component: HouseShowComponent },
            { path: 'ticket', component: TicketAdminComponent },
            { path: 'ticket/:id', component: TicketSingleAdminComponent },
            { path: 'friend/:id', component: UserProfileComponent },
            { path: 'report', component: UserReportComponent },
            { path: 'apartment', component: UserApartmentComponent },
            { path: 'rents/electricity', component: GasComponen },
            { path: 'rents/water', component: WaterComponen },
            { path: 'rent/gas' , component: WaterComponen },
            { path: 'apartment/apartmentprofile/:id', component: ApartmentProfileComponent },          
            { path: 'osbb/contacts', component: OsbbContactsComponent },
            { path: 'osbb/documents-and-reports', redirectTo: 'osbb/documents-and-reports/1', pathMatch: 'full' },
            { path: 'osbb/documents-and-reports/:id', component: OsbbDocumentsAndReportsComponent },              
			{ path: 'bill/parentbillid', component: CustomserviceComponent},
            { path: 'bill/parentbillid/subbill/:id', component: SubbillComponent }
        ]
    }
];
