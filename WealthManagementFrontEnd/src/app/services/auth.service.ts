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
        'openid',
        'profile',
        'email',
        `api://${BACKEND_CLIENT_ID}/access_as_user`  // ← backend scope added
      ]
    });
  }

  logout() {
    this.msalService.logoutRedirect();
    this.userSubject.next(null);
  }

  setUser(user: any) {
    this.userSubject.next(user);
  }

  getUser() {
    return this.userSubject.value;
  }

  getAccessToken(): Promise<string | null> {
    const account = this.msalService.instance.getAllAccounts()[0];
    if (!account) {
      return Promise.resolve(null);
    }
    return this.msalService.instance.acquireTokenSilent({
      scopes: ['api://79658250-737d-4b88-b080-a7c8bf2a1d5e/access_as_user'],
      account
    }).then(result => result.accessToken).catch(() => null);
  }
}