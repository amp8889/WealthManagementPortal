import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { providePrimeNG } from "primeng/config";
import Aura from "@primeuix/themes/aura";
import Material from "@primeuix/themes/Material"
import Lara from "@primeuix/themes/Lara"
import Nora from "@primeuix/themes/Nora"



import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes), 
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
