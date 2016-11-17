import { Component } from "@angular/core";
import { HouseShowComponent } from "../../../house/house.show.component";
@Component(
    {
        selector: 'manager-house-show',
        templateUrl: 'src/app/manager/components/house/house-show.manager.html',
        directives:[HouseShowComponent]
    }
)
export class HouseShowManagerComponent {

}