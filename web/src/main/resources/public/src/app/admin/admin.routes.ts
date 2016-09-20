import {AdminComponent} from "./admin.component";
import {RouterConfig} from "@angular/router";
import {OsbbComponent} from "./components/osbb/osbb.component";
import {HouseTableAdminComponent} from "./components/house/house_table.admin.component";
import {ApartmentTableAdminComponent} from "./components/apartment/apartment.table.admin.component";
import {UsersComponent} from "./components/users/users.component";
import {RoleComponent} from "./components/role/role.component";
import {HouseShowAdminComponent} from "./components/house/house_show.admin.component";

import {ApartmentProfileComponent} from "../../app/user/ApartmentProfile/apartment.profile"

export const adminRoutes: RouterConfig = [
    {
        path: 'admin',
        component: AdminComponent,

        children: [
            {path: 'osbb', component: OsbbComponent},
            {path: '', redirectTo: 'osbb', pathMatch: 'full'},
            {path: 'houses', component: HouseTableAdminComponent},
            {path: 'house/:id', component: HouseShowAdminComponent},
            {path: 'apartments', component: ApartmentTableAdminComponent},
            {path: 'users', component: UsersComponent},
            {path:'apartments/apartmentprofile/:id', component:ApartmentProfileComponent},
            {path: 'role', component: RoleComponent}
        ]
    },


];