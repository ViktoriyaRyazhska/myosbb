import {Component} from "@angular/core";
import {ProfileComponent} from "../../user/profile/profile.component";

@Component({
    selector: 'user-profile',
    template: `
    <div class="row sb-backdown">
                <my-user-profile>
                </my-user-profile>
    </div>
    `,
    styleUrls: [ 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    directives: [ProfileComponent]
})
export class HomeProfileUser {

}
