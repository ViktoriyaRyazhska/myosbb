import { Component ,onInit} from '@angular/core';
import {ROUTER_DIRECTIVES, Router} from "@angular/router";
import {TranslatePipe} from "ng2-translate/ng2-translate";

@Component({
  selector: 'electricity',
  templateUrl: 'src/app/home/components/apartment/submenu/electricity/electricity.component.html',
  styleUrls: ['src/app/home/components/apartment/styles.css'],
  directives: [ROUTER_DIRECTIVES],
  pipes: [TranslatePipe]

})
export class ElectricityComponen{
    
    constructor(private _router: Router) {

    }

}
