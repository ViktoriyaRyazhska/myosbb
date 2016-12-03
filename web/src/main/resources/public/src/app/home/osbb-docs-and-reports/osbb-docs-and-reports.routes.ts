import { RouterConfig } from '@angular/router';
import { OsbbDocumentsAndReportsComponent } from './osbb-docs-and-reports.component';
import { RegulationsComponent} from './regulations/regulations.component';
import { FinancialStatementsComponent} from './financial-statements/financial-statements.component';
import { MeetingsMinutesComponent } from './meetings-minutes/meetings-minutes.component';

export const osbbDocsAndReportsRouter: RouterConfig = [
    {
        path: 'home/osbb/documents-and-reports', component: OsbbDocumentsAndReportsComponent,

        children: [
            { path: '', component: OsbbDocumentsAndReportsComponent},
            { path: 'regulations', component: RegulationsComponent},
            { path: 'financial-statements', component: FinancialStatementsComponent},
            { path: 'meetings-minutes', component: MeetingsMinutesComponent}
        ]
    }
];