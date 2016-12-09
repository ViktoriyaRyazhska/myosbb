import {Component} from "@angular/core";
import {ProviderComponent} from "../../../user/provider/provider.component";

@Component({
    selector: 'provider-manager',
    templateUrl:'src/app/manager/components/provider/provider-table.html',
    styleUrls: ['src/app/user/bills/bill.css', 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    directives: [ProviderComponent]
})
export class ProviderManagerComponent {

}