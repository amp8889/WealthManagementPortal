import { ApplicationConfig, inject, provideAppInitializer, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { providePrimeNG } from "primeng/config";
import Aura from "@primeuix/themes/aura";
import Material from "@primeuix/themes/Material"
import Lara from "@primeuix/themes/Lara"
import Nora from "@primeuix/themes/Nora"



import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { catchError, of } from 'rxjs';
import { AuthInterceptor } from './services/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },

    provideClientHydration(withEventReplay()),
    providePrimeNG({
      theme: {
        preset: Nora,
        options: {}
      },
      ripple: true
    })
  ]
};