import { Routes } from '@angular/router';
import { ClientRecords } from './client-records/client-records';
import { Goal } from './goal/goal';
import {Home} from './home/home'
import { ClientDashboard } from './client-dashboard/client-dashboard';

export const routes: Routes = [

    { path: "client-records", component: ClientRecords },
    {path: "goal", component: Goal},
    {path: "home", component: Home},
    {path: 'client-records/:id', component: ClientDashboard}


];
