import { Routes } from '@angular/router';
import { ClientRecords } from './client-records/client-records';
import { Goal } from './goal/goal';
import { Home } from './home/home'
import { ClientDashboard } from './client-dashboard/client-dashboard';
import { Register } from './register/register';

export const routes: Routes = [

    { path: "clientrecords", component: ClientRecords },
    { path: "goal", component: Goal },
    { path: "home", component: Home },
    { path: 'clientrecords/:id', component: ClientDashboard },
    { path: "register", component: Register },
    {
        path: 'dashboard/:clientId',
        loadComponent: () => import('./client-dashboard/client-dashboard').then(m => m.ClientDashboard),
    },


];
