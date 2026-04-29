import { Routes } from '@angular/router';
import { ClientRecords } from './client-records/client-records';

export const routes: Routes = [

    { path: "client-records", component: ClientRecords },
    {path: "", redirectTo: "movies", pathMatch: 'full'}


];
