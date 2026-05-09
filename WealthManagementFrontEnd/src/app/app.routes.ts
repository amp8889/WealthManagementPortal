import { Routes } from '@angular/router';
import { ClientRecords } from './client-records/client-records';
import { Goal } from './goal/goal';
import { Home } from './home/home';
import { ClientDashboard } from './client-dashboard/client-dashboard';
import { Login } from './login/login';
import { MsalGuard, MsalRedirectComponent } from '@azure/msal-angular';
import { adminGuard } from './guards/role.guard';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'auth', component: MsalRedirectComponent },

  // Both roles can access
  { path: 'home', component: Home, canActivate: [MsalGuard] },
  {
    path: 'dashboard/:clientId',
    loadComponent: () => import('./client-dashboard/client-dashboard').then(m => m.ClientDashboard),
    canActivate: [MsalGuard]
  },

  // Admin only
  { path: 'clientrecords', component: ClientRecords, canActivate: [MsalGuard, adminGuard] },
  { path: 'clientrecords/:id', component: ClientDashboard, canActivate: [MsalGuard, adminGuard] },
  { path: 'goal', component: Goal, canActivate: [MsalGuard, adminGuard] },

  { path: '', redirectTo: '/login', pathMatch: 'full' }
];