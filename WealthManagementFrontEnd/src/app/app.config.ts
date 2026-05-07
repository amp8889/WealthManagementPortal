import { APP_INITIALIZER, ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { providePrimeNG } from "primeng/config";
import Aura from "@primeuix/themes/aura";
import Material from "@primeuix/themes/Material"
import Lara from "@primeuix/themes/Lara"
import Nora from "@primeuix/themes/Nora"



import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { AuthService } from './services/AuthService';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes), 
    provideHttpClient(),
    {
      provide: APP_INITIALIZER,
      useFactory: (auth: AuthService) => () => auth.fetchCurrentUser(),
      deps: [AuthService],
      multi: true
    },
    provideClientHydration(withEventReplay()),
    providePrimeNG({
      theme:{
        preset: Nora ,
                options: {
           
        }
        

      },
      ripple: true
    })
  ]
};
