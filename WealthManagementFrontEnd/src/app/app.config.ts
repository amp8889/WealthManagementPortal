import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { providePrimeNG } from "primeng/config";
import Nora from "@primeuix/themes/Nora";

import { routes } from './app.routes';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

import { AuthInterceptor } from './services/auth.interceptor';

import {
  MsalService,
  MsalGuard,
  MsalBroadcastService,
  MSAL_INSTANCE,
  MSAL_GUARD_CONFIG,
  MSAL_INTERCEPTOR_CONFIG,
  MsalGuardConfiguration,
  MsalInterceptorConfiguration
} from '@azure/msal-angular';

import { PublicClientApplication, InteractionType } from '@azure/msal-browser';

const TENANT_ID = 'f947665a-e85c-43ab-8198-2b8c8cbeb643';
const FRONTEND_CLIENT_ID = '1ae58dcb-e998-4c0f-9761-e186cacf4c35';
const BACKEND_CLIENT_ID = '79658250-737d-4b88-b080-a7c8bf2a1d5e'; // ← replace with your backend app registration client ID

export function MSALInstanceFactory(): PublicClientApplication {
  const msalInstance = new PublicClientApplication({
    auth: {
      clientId: FRONTEND_CLIENT_ID,
      authority: `https://login.microsoftonline.com/${TENANT_ID}`,
      redirectUri: 'http://localhost:4200/auth',
      postLogoutRedirectUri: 'http://localhost:4200/login'
    },
    cache: {
      cacheLocation: 'localStorage'
    }
  });

  // Don't call initialize() here — MSAL Angular handles this internally
  // when you provide the instance via MSAL_INSTANCE
  return msalInstance;
}

export function MSALGuardConfigFactory(): MsalGuardConfiguration {
  return {
    interactionType: InteractionType.Redirect,
    authRequest: {
      scopes: [`api://${BACKEND_CLIENT_ID}/access_as_user`]
    },
  };
}

export function MSALInterceptorConfigFactory(): MsalInterceptorConfiguration {
  return {
    interactionType: InteractionType.Redirect,
    protectedResourceMap: new Map([
      ['http://localhost:8080/api', [`api://${BACKEND_CLIENT_ID}/access_as_user`]],
      ['http://wealthmanagementportal.eastus.cloudapp.azure.com/api', [`api://${BACKEND_CLIENT_ID}/access_as_user`]]
    ])
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

    {
      provide: MSAL_INSTANCE,
      useFactory: MSALInstanceFactory
    },
    {
      provide: MSAL_GUARD_CONFIG,
      useFactory: MSALGuardConfigFactory
    },
    {
      provide: MSAL_INTERCEPTOR_CONFIG,
      useFactory: MSALInterceptorConfigFactory
    },
    MsalService,
    MsalGuard,
    MsalBroadcastService,

    // ← removed provideClientHydration, not needed without SSR

    providePrimeNG({
      theme: {
        preset: Nora,
        options: {}
      },
      ripple: true
    })
  ]
};