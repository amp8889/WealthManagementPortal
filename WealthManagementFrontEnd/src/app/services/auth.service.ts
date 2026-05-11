import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MsalService } from '@azure/msal-angular';

const BACKEND_CLIENT_ID = '79658250-737d-4b88-b080-a7c8bf2a1d5e';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private userSubject = new BehaviorSubject<any>(null);
  user$ = this.userSubject.asObservable();

  constructor(private msalService: MsalService) {}

  login() {
    this.msalService.loginRedirect({
      scopes: [
        'openid', 'profile', 'email',
        `api://${BACKEND_CLIENT_ID}/access_as_user`
      ]
    });
  }

  logout() {
    this.msalService.logoutRedirect();
    this.userSubject.next(null);
  }

  loadUserFromToken() {
    const account = this.msalService.instance.getActiveAccount()
      ?? this.msalService.instance.getAllAccounts()[0];

    if (!account) return;

    this.msalService.instance.acquireTokenSilent({
      scopes: [`api://${BACKEND_CLIENT_ID}/access_as_user`],
      account
    }).then(result => {
      
      const payload = JSON.parse(atob(result.accessToken.split('.')[1]));
      const roles: string[] = payload.roles ?? [];
      const role = roles[0] ?? null;  

      this.userSubject.next({
        name: account.name,
        username: account.username,
        role: role,  
      });
    }).catch(err => console.error('Failed to load user from token:', err));
  }

  getUser() {
    return this.userSubject.value;
  }

  isAuthenticated(): boolean {
    return this.msalService.instance.getAllAccounts().length > 0;
  }

  getRole(): string | null {
    return this.userSubject.value?.role ?? null;
  }
}