import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { MsalService } from '@azure/msal-angular';
import { from } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private msalService: MsalService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const account = this.msalService.instance.getAllAccounts()[0];

    if (!account) {
      return next.handle(req);
    }

    return from(
      this.msalService.instance.acquireTokenSilent({
        scopes: ['api://79658250-737d-4b88-b080-a7c8bf2a1d5e/access_as_user'],
        account
      })
    ).pipe(
      switchMap(result => {
        const cloned = req.clone({
          setHeaders: {
            Authorization: `Bearer ${result.accessToken}`
          }
        });
        return next.handle(cloned);
      })
    );
  }
}