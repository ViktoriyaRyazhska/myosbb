import { Component, OnInit } from '@angular/core';
import { ROUTER_DIRECTIVES } from "@angular/router";

import { TranslatePipe } from 'ng2-translate';
import { CapitalizeFirstLetterPipe } from '../../../shared/pipes/capitalize-first-letter';

@Component({
    selector: 'osbb-docs-and-reports',
    templateUrl: 'src/app/home/osbb-docs-and-reports/osbb-docs-and-reports.html',
    styleUrls: ['src/app/home/osbb-contacts/osbb-contacts.css'],
    directives: [ROUTER_DIRECTIVES]
    pipes:[CapitalizeFirstLetterPipe, TranslatePipe]
})
export class OsbbDocumentsAndReportsComponent implements OnInit {

    ngOnInit(): any {
        console.log('Initializing OSBB Docs and Reports...');
    }
}
