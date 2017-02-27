import { AdminComponent } from './admin.component';
import { Routes } from '@angular/router';
import { WallComponent } from '../components/wall';
import { HouseComponent } from '../components/house';
import { EventsComponent } from '../components/events';
import { OsbbBillsComponent } from '../components/osbbBils';
import { ContractsComponent } from '../components/contracts';
import { TicketComponent  } from '../components/ticket';
import { ProviderComponent } from '../components/provider';
import { ApartmentComponent } from '../components/apartment';
import { CalendarComponent } from '../components/calendar';
import { SubTicketComponent } from '../components/ticket/components/subticket';
import { LogedInGuard } from '../../assets/services/loged-in-guard.service';

export const AdminRoutes: Routes = [
  { path: 'admin',  component: AdminComponent,
    canActivate: [LogedInGuard],
    data: { role: 'ROLE_ADMIN'},
    children: [
      { path: '', redirectTo: 'wall', pathMatch: 'full' },
      { path: 'wall', component: WallComponent },
      { path: 'houses', component: HouseComponent },
      { path: 'events', component: EventsComponent },
      { path: 'osbb', component: OsbbBillsComponent },
      { path: 'contract', component: ContractsComponent },
      { path: 'ticket', component: TicketComponent  },
      { path: 'ticket/:id', component: SubTicketComponent },
      { path: 'provider', component: ProviderComponent },
      { path: 'apartment', component: ApartmentComponent },
      { path: 'calendar', component: CalendarComponent },
    ]},
];
