import { Component, OnInit } from "@angular/core";
import { UserApartmentComponent } from "../../../home/components/apartment/user.apartment.component";
import { ROUTER_DIRECTIVES } from "@angular/router";
@Component(
    {
        selector: 'admin-apartment-table',
        templateUrl: 'src/app/admin/components/apartment/apartment_table.admin.html',
        directives:[UserApartmentComponent,ROUTER_DIRECTIVES]
    }
)
export class ApartmentTableAdminComponent {

}