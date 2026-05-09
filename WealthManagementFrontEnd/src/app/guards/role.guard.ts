import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { MsalBroadcastService } from '@azure/msal-angular';
import { InteractionStatus } from '@azure/msal-browser';
import { filter, map, take } from 'rxjs/operators';

export const adminGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const broadcastService = inject(MsalBroadcastService);

  return broadcastService.inProgress$.pipe(
    filter(status => status === InteractionStatus.None),
    take(1),
    map(() => {
      const role = authService.getRole();
      if (role === 'ADMIN') return true;
      router.navigate(['/home']);
      return false;
    })
  );
};