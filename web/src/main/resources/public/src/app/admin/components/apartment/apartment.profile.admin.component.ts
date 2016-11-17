/**
 * Created by Oleg on 24.09.2016.
 */
import { Component, OnInit } from "@angular/core";
import { ApartmentProfileComponent } from "../../../home/components/ApartmentProfile/apartment.profile";
import { ROUTER_DIRECTIVES } from "@angular/router";
@Component(
    {
        selector: 'admin-apartment-table',
        templateUrl: 'src/app/admin/components/apartment/apartment_profile.html',
        styleUrls: ['src/app/home/components/apartment/styles.css'],
        directives:[ApartmentProfileComponent, ROUTER_DIRECTIVES],
    }
)
export class ApartmentProfileAdminComponent {

}