import {Component} from "@angular/core";
import {TicketComponent} from "./../../../user/ticket/ticket.component";

@Component({
    selector: 'ticket-manager',
    template: `
    <div class="row sb-backdown">
                <ticket>
                </ticket>       
    </div>
    `,
    styleUrls: ['src/app/house/house.css', 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    directives: [TicketComponent]
})
export class TicketManagerComponent {

}