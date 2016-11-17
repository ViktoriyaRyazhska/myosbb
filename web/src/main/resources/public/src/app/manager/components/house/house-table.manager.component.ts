import { Component } from "@angular/core";
import { HouseTableComponent } from "../../../house/house.table.component";
import { ROUTER_DIRECTIVES } from "@angular/router";
@Component(
    {
        selector: 'manager-house-table',
        templateUrl: 'src/app/manager/components/house/house-table.manager.html',
        directives: [HouseTableComponent, ROUTER_DIRECTIVES]
    }
)
export class HouseTableManagerComponent {

}