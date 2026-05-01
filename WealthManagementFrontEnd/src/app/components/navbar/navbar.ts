import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule, MenubarModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {


  items = [
    {
      label: 'Home',
      icon: 'pi pi-home',
      routerLink: '/'
    },
    {
      label: 'Client Records',
      icon: 'pi-database',
      routerLink: '/client-records'
    },
    {
      label: 'Goals',
      icon: 'pi pi-chart-line',
      routerLink: '/goal'
    }
  ];



}
