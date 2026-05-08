import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { MsalService } from '@azure/msal-angular';
import { from, Observable } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private msalService: MsalService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const account = this.msalService.instance.getActiveAccount()
      ?? this.msalService.instance.getAllAccounts()[0];

    if (!account) {
      console.warn('No active account found, request will proceed without auth header');
      return next.handle(req);
    }

    return from(
      this.msalService.instance.acquireTokenSilent({
        scopes: ['api://79658250-737d-4b88-b080-a7c8bf2a1d5e/access_as_user'],
        account
      })
    ).pipe(
      switchMap(result => {
        console.log('Token acquired, adding to request:', req.url);
        const cloned = req.clone({
          setHeaders: {
            Authorization: `Bearer ${result.accessToken}`
          }
        });
        return next.handle(cloned);
      }),
      catchError(error => {
        console.error('Error acquiring token:', error);
        return next.handle(req);
      })
    );
  }
}