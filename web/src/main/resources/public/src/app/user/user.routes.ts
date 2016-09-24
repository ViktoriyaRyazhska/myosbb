import {RouterConfig} from "@angular/router";
import {UserComponent} from "../user/user.component";
import {UserApartmentComponent} from "./apartment/user.apartment.component";
import {UserBillComponent} from "./bills/user.bill.component";
import {TicketComponent} from "./ticket/ticket.component";
import {MessageComponent} from "./ticket/single_ticket/single.ticket.component";
import {UserCalendarComponent} from "./calendar/user.calendar.component";
import {UserReportComponent} from "./report/user.report.component";
import {ProviderComponent} from "./provider/provider.component";
import {ContractComponent} from "./contract/contract.component";
import {ApartmentProfileComponent} from "./ApartmentProfile/apartment.profile";
import {ProfileComponent} from "./profile/profile.component";
import {UserProfileComponent} from "./user/user.profile.component";
import {SettingsComponent} from "./settings/settings.component";


 
export const userRoutes: RouterConfig = [
    {
        path: 'home/user',
        children: [
            {path: 'main', component: ProfileComponent},
            {path: '', redirectTo: 'friend', pathMatch: 'full'},
            {path: 'apartment', component: UserApartmentComponent},
            {path: 'calendar', component: UserCalendarComponent},
            {path: 'bill', component: UserBillComponent},
            {path: 'report', component: UserReportComponent},
            {path: 'profile', component: ProfileComponent},
            {path: 'main/settings', component: SettingsComponent},
            {path: 'friend/:id', component: UserProfileComponent}
            

        ],
        component: UserComponent,
    }
];