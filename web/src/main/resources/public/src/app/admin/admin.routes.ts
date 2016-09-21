import {AdminComponent} from "./admin.component";
import {RouterConfig} from "@angular/router";
import {OsbbComponent} from "./components/osbb/osbb.component";
import {HouseTableAdminComponent} from "./components/house/house_table.admin.component";
import {ApartmentTableAdminComponent} from "./components/apartment/apartment.table.admin.component";
import {UsersComponent} from "./components/users/users.component";
import {RoleComponent} from "./components/role/role.component";
import {HouseShowAdminComponent} from "./components/house/house_show.admin.component";

import {ApartmentProfileComponent} from "../../app/user/ApartmentProfile/apartment.profile"
import {AdminLoginGuard} from "../../shared/guard/admin.login.guard";
import {ApartmentProfileComponent} from "../../app/user/ApartmentProfile/apartment.profile";
import {EventAdminComponent} from "./components/event/event.component";
import {EventShowAdminComponent} from "./components/event/event.show.component";
import {AttachmentAdminComponent} from "./components/attachment/attachment.component";

export const adminRoutes: RouterConfig = [
    {
        path: 'admin',
        component: AdminComponent,
        canActivate: [AdminLoginGuard],

        children: [
            {path: 'osbb', component: OsbbComponent},
            {path: '', redirectTo: 'osbb', pathMatch: 'full'},
            {path: 'houses', component: HouseTableAdminComponent},
            {path: 'house/:id', component: HouseShowAdminComponent},
            {path: 'apartments', component: ApartmentTableAdminComponent},
            {path: 'events', component: EventAdminComponent},
            {path: 'event/:id', component: EventShowAdminComponent},
            {path: 'attachments', component: AttachmentAdminComponent},
            {path: 'users', component: UsersComponent},
            {path: 'apartments/apartmentprofile/:id', component: ApartmentProfileComponent},
            {path: 'role', component: RoleComponent}
        ]
    },


];