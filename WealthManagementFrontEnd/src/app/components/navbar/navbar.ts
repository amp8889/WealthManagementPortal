import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { map } from 'rxjs';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule, MenubarModule, CommonModule],
  templateUrl: './navbar.html',
})
export class Navbar {

  user$;
  items$;

  constructor(
    private auth: AuthService,
    private router: Router
  ) {
    // ✅ safe initialization (after DI is ready)
    this.user$ = this.auth.user$;

this.auth.user$.subscribe(user => {
  console.log("NAVBAR USER:", user);
});


this.items$ = this.auth.user$.pipe(
  map(user => {
    const role = user?.role;

    const items = [
      { label: 'Home', icon: 'pi pi-home', routerLink: '/' }
    ];

    if (role === 'ADMIN' || role === 'ADVISOR') {
      items.push({ label: 'Client Records', icon: 'pi pi-database', routerLink: '/clientrecords' });
    }

    if (role === 'ADMIN') {
      items.push({ label: 'Goals', icon: 'pi pi-chart-line', routerLink: '/goal' });
    }

    if (role === 'CLIENT') {
      items.push({ label: 'My Dashboard', icon: 'pi pi-user', routerLink: '/client-dashboard' });
    }

    return items;
  })
);
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}