// interceptors/auth.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError(error => {
      if (error.status === 401) {
        // redirect to Spring's OAuth login endpoint
        window.location.href = 'http://wealthmanagementportal.eastus.cloudapp.azure.com/oauth2/authorization/google';
      }
      return throwError(() => error);
    })
  );
};