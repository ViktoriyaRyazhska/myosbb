import {Component} from "@angular/core";
import {UserProfileComponent} from "../../../home/user/user.profile.component";

@Component({
    selector: 'ticket-admin',
    template: `
                <friend>
                </friend>
    `,
    styleUrls: [ 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    directives: [UserProfileComponent]
})
export class UserProfileAdminComponent {

}
