import { UserComponent } from './user.component';
import { Routes } from '@angular/router';
import { WallComponent } from '../components/wall';
import { HouseComponent } from '../components/houses';
import { EventsComponent } from '../components/events';
import { OsbbBillsComponent } from '../components/osbbBils';
import { ContractsComponent } from '../components/contracts';
import { TicketComponent  } from '../components/ticket';
import { ProviderComponent } from '../components/provider';
import { ApartmentComponent } from '../components/apartment';
import { CalendarComponent } from '../components/calendar';
import { SubTicketComponent } from '../components/ticket/components/subticket';
import { OsbbDocumentsAndReportsComponent } from '../components/osbb-docs-and-reports';
import { OsbbContactsComponent } from '../components/osbb-contacts';
import { LogedInGuard } from '../services/loged-in-guard.service';

export const UserRoutes: Routes = [
  { path: 'user',  component: UserComponent,
    canActivate: [LogedInGuard],
    data: { role: 'ROLE_USER'},
    children: [
      { path: '', redirectTo: 'wall', pathMatch: 'full' },
      { path: 'wall', component: WallComponent },
      { path: 'documents-and-reports', component: OsbbDocumentsAndReportsComponent },
      { path: 'houses', component: HouseComponent },
      { path: 'events', component: EventsComponent },
      { path: 'osbb', component: OsbbBillsComponent },
      { path: 'contract', component: ContractsComponent },
      { path: 'ticket', component: TicketComponent  },
      { path: 'ticket/:id', component: SubTicketComponent },
      { path: 'provider', component: ProviderComponent },
      { path: 'apartment', component: ApartmentComponent },
      { path: 'calendar', component: CalendarComponent },
      { path: 'contacts', component: OsbbContactsComponent }
    ]},
];
