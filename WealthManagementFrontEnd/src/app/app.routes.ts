import { Routes } from '@angular/router';
import { ClientRecords } from './client-records/client-records';
import { Goal } from './goal/goal';
import { Home } from './home/home';
import { ClientDashboard } from './client-dashboard/client-dashboard';
import { Login } from './login/login';
import { MsalGuard, MsalRedirectComponent } from '@azure/msal-angular';

export const routes: Routes = [
  // Public routes — no guard
  { path: 'login', component: Login },
  { path: 'auth', component: MsalRedirectComponent },

  // Protected routes — require login
  { path: 'home', component: Home, canActivate: [MsalGuard] },
  { path: 'clientrecords', component: ClientRecords, canActivate: [MsalGuard] },
  { path: 'clientrecords/:id', component: ClientDashboard, canActivate: [MsalGuard] },
  { path: 'goal', component: Goal, canActivate: [MsalGuard] },
  {
    path: 'dashboard/:clientId',
    loadComponent: () => import('./client-dashboard/client-dashboard').then(m => m.ClientDashboard),
    canActivate: [MsalGuard]
  },

  // Default
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];