import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { ButtonModule } from "primeng/button";
import { MenubarModule } from "primeng/menubar";
import { Navbar } from './components/navbar/navbar';
import { MsalService, MsalBroadcastService } from '@azure/msal-angular';
import { InteractionStatus } from '@azure/msal-browser';
import { filter, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ButtonModule, MenubarModule, Navbar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit, OnDestroy {

  private readonly destroying$ = new Subject<void>();

  constructor(
    private router: Router,
    private msalService: MsalService,
    private msalBroadcastService: MsalBroadcastService
  ) {}

  ngOnInit(): void {
    // Processes the #code= in the URL after Azure redirects back
    this.msalService.handleRedirectObservable().subscribe({
      next: (result) => {
        if (result) {
          this.msalService.instance.setActiveAccount(result.account);
          this.router.navigate(['/home']);  // ← redirect to home after successful login
        }
      },
      error: (error) => console.error('Redirect error:', error)
    });

    // Set active account once MSAL is done with any in-progress interaction
    this.msalBroadcastService.inProgress$
      .pipe(
        filter((status: InteractionStatus) => status === InteractionStatus.None),
        takeUntil(this.destroying$)
      )
      .subscribe(() => {
        const accounts = this.msalService.instance.getAllAccounts();
        if (accounts.length > 0) {
          this.msalService.instance.setActiveAccount(accounts[0]);
        }
      });
  }

  ngOnDestroy(): void {
    this.destroying$.next(undefined);
    this.destroying$.complete();
  }
}