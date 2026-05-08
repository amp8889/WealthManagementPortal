import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { providePrimeNG } from "primeng/config";
import Nora from "@primeuix/themes/Nora";

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

import { AuthInterceptor } from './services/auth.interceptor';

// 🔥 MSAL imports
import { MsalService, MsalGuard, MsalBroadcastService, MSAL_INSTANCE, MSAL_GUARD_CONFIG, MsalGuardConfiguration} from '@azure/msal-angular';
import { PublicClientApplication } from '@azure/msal-browser';
import { InteractionType } from '@azure/msal-browser'; // ✅ CORRECT
// 🔥 Create MSAL instance
export function MSALInstanceFactory() {
  return new PublicClientApplication({
    auth: {
      clientId: 'YOUR_FRONTEND_CLIENT_ID',
      authority: 'https://login.microsoftonline.com/YOUR_TENANT_ID',
      redirectUri: window.location.origin
    },
    cache: {
      cacheLocation: 'localStorage'
    }
  });
}

// 🔥 Guard config (used for route protection)
export function MSALGuardConfigFactory(): MsalGuardConfiguration {
  return {
    interactionType: InteractionType.Redirect,
    authRequest: {
      scopes: ['api://YOUR_BACKEND_CLIENT_ID/access_as_user']
    }
  };
}

export const appConfig: ApplicationConfig = {
  providers: [

    provideRouter(routes),

    provideHttpClient(withInterceptorsFromDi()),

    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },

    // 🔥 MSAL providers
    {
      provide: MSAL_INSTANCE,
      useFactory: MSALInstanceFactory
    },
    {
      provide: MSAL_GUARD_CONFIG,
      useFactory: MSALGuardConfigFactory
    },
    MsalService,
    MsalGuard,
    MsalBroadcastService,

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