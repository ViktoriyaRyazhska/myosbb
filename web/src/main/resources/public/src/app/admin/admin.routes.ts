import { AdminComponent } from "./admin.component";
import { RouterConfig } from "@angular/router";
import { OsbbComponent } from "./components/osbb/osbb.component";
import { HouseTableAdminComponent } from "./components/house/house_table.admin.component";
import { ApartmentTableAdminComponent } from "./components/apartment/apartment.table.admin.component";
import { UsersComponent } from "./components/users/users.component";
import { RoleComponent } from "./components/role/role.component";
import { HouseShowAdminComponent } from "./components/house/house_show.admin.component";
import { TicketAdminComponent } from "./components/ticket/ticket.component.admin";
import { TicketSingleAdminComponent } from "./components/ticket/single.ticket.component.admin";
import { ApartmentProfileAdminComponent } from  "./components/apartment/apartment.profile.admin.component";
import { AdminLoginGuard } from "../../shared/guard/admin.login.guard";
import { EventAdminComponent } from "./components/event/event.admin.component";
import { EventShowAdminComponent } from "./components/event/event.show.admin.component";
import { AttachmentAdminComponent } from "./components/attachment/attachment.component";
import { UserProfileAdminComponent } from "./components/user/user.profile.admin.component";
import { UserProfileComponent } from "./../home/user/user.profile.component";
import { CalendarAdminComponent } from "./components/calendar/calendar.admin.component";

export const adminRoutes:RouterConfig = [
    {
        path: 'admin',
        component: AdminComponent,
        canActivate: [AdminLoginGuard],

        children: [
            { path: 'osbb', component: OsbbComponent },
            { path: '', redirectTo: 'osbb', pathMatch: 'full' },
            { path: 'houses', component: HouseTableAdminComponent },
            { path: 'houses/:id', component: HouseTableAdminComponent },
            { path: 'house/:id', component: HouseShowAdminComponent },
            { path: 'apartments', component: ApartmentTableAdminComponent },
            { path: 'events', component: EventAdminComponent },
            { path: 'event/:id', component: EventShowAdminComponent },
            { path: 'calendar', component: CalendarAdminComponent },
            { path: 'attachments', component: AttachmentAdminComponent },
            { path: 'users', component: UsersComponent },
            { path: 'ticket', component: TicketAdminComponent },
            { path: 'ticket/:id', component: TicketSingleAdminComponent },
            { path: 'role', component: RoleComponent },
            { path: 'friend/:id', component: UserProfileComponent },
            { path: 'apartments/apartmentprofile/:id', component: ApartmentProfileAdminComponent },
            { path: 'role', component: RoleComponent },
            { path: 'friend/:id', component: UserProfileAdminComponent }

]
    },

];