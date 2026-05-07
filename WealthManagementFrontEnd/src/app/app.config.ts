import { ApplicationConfig, inject, provideAppInitializer, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { providePrimeNG } from "primeng/config";
import Aura from "@primeuix/themes/aura";
import Material from "@primeuix/themes/Material"
import Lara from "@primeuix/themes/Lara"
import Nora from "@primeuix/themes/Nora"



import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { AuthService } from './services/AuthService';
import { credentialsInterceptor } from './interceptors/credentials-interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes), 
    provideHttpClient(),
    provideHttpClient(withInterceptors([credentialsInterceptor])),

    provideAppInitializer(() => {
      const auth = inject(AuthService);
      return auth.fetchCurrentUser();
    }),
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
