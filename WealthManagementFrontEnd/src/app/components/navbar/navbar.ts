import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, MenubarModule, ButtonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {

  constructor(private router: Router) {}

  isLoggedIn(): boolean {
    return !!localStorage.getItem('auth'); // simple placeholder auth check
  }

  logout() {
    localStorage.removeItem('auth');
    this.router.navigate(['/login']);
  }

  login() {
    this.router.navigate(['/login']);
  }

  items = [
    {
      label: 'Home',
      icon: 'pi pi-home',
      routerLink: '/'
    },
    {
      label: 'Client Records',
      icon: 'pi pi-database',
      routerLink: '/clientrecords'
    },
    {
      label: 'Goals',
      icon: 'pi pi-chart-line',
      routerLink: '/goal'
    }
  ];
}