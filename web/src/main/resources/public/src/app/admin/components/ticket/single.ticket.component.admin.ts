import { Component } from "@angular/core";
import { MessageComponent } from "./../../../user/ticket/single_ticket/single.ticket.component";

@Component({
    selector: 'ticket-admin',
    template: `
    <div class="row sb-backdown">
                <ticket>
                </ticket>       
    </div>
    `,
    styleUrls: [ 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    directives: [MessageComponent]
})
export class TicketSingleAdminComponent {

}
