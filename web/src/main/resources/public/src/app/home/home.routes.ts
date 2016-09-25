import {RouterConfig} from "@angular/router";
import {HomeComponent} from "./home.component";
import {OsbbComponent} from "../admin/components/osbb/osbb.component";
import {TicketAdminComponent} from "../user/ticket/ticket.component.admin";
import {HouseTableComponent} from "../house/house.table.component";
import {HouseShowComponent} from "../house/house.show.component";
import {TicketSingleAdminComponent} from "../user/ticket/single_ticket/single.ticket.component.admin";
import {HomeWallComponent} from "./home_wall/home.wall.component";
import {ProviderUserPageWrapperComponent} from "../user/provider/provider_home/provider-user-page.component.wrapper";
import {ProviderInfoPageWrapperComponent} from "../user/provider/provider-info-page-wrapper.component";
import {EventComponent} from "../event/event.component";
import {EventShowComponent} from "../event/event.show.component";
import {CalendarHomeComponent} from "./calendar/calendar.home.component";
import {UserProfileComponent} from "./user/user.profile.component";
import {HomeProfileUser} from "./user/home.profile.component";
export const homeRoutes: RouterConfig = [
    {
        path: 'home',
        component: HomeComponent,

        children: [
            // { path: ':id',  component: UserShowComponent },
            {path: 'wall', component: HomeWallComponent},
            {path: '', redirectTo: 'wall', pathMatch: 'full'},
            {path: 'events', component: EventComponent},
            {path: 'event/:id', component: EventShowComponent},
            {path: 'calendar', component: CalendarHomeComponent},
            {path: 'osbb', component: OsbbComponent},
            {path: 'provider/info', component: ProviderUserPageWrapperComponent},
            {path: 'provider/info/:id', component: ProviderInfoPageWrapperComponent},
            {path: 'houses', component: HouseTableComponent},
            {path: 'house/:id', component: HouseShowComponent},
            {path: 'ticket', component: TicketAdminComponent},
            {path: 'ticket/:id', component: TicketSingleAdminComponent},
            {path: 'user/:id', component: UserProfileComponent},
            {path: 'main', component: HomeProfileUser},


        ]
    }
];