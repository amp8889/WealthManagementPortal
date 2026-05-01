import { Routes } from '@angular/router';
import { ClientRecords } from './client-records/client-records';
import { Goal } from './goal/goal';
import {Home} from './home/home'

export const routes: Routes = [

    { path: "client-records", component: ClientRecords },
    {path: "goal", component: Goal},
    {path: "home", component: Home},
    {path: "", redirectTo: "movies", pathMatch: 'full'}


];
