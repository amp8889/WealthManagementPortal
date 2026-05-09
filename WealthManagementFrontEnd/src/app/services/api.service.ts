// src/app/services/api.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MsalService } from '@azure/msal-angular';
import { from, switchMap, of } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({ providedIn: 'root' })
export class ApiService {

  private baseUrl = environment.apiUrl;
  private scope = 'api://79658250-737d-4b88-b080-a7c8bf2a1d5e/access_as_user';

  constructor(private http: HttpClient, private msalService: MsalService) {}

  private getHeaders() {
    const account = this.msalService.instance.getActiveAccount()
      ?? this.msalService.instance.getAllAccounts()[0];

        console.log('Account:', account);  // ← add this

    return from(
      this.msalService.instance.acquireTokenSilent({
        scopes: [this.scope],
        account
      })
    ).pipe(
      switchMap(result => {
        const headers = new HttpHeaders({
          Authorization: `Bearer ${result.accessToken}`
        });
        return of(headers);
      })
    );
  }

  get<T>(path: string) {
    return this.getHeaders().pipe(
      switchMap(headers => this.http.get<T>(`${this.baseUrl}${path}`, { headers }))
    );
  }

  post<T>(path: string, body: any) {
    return this.getHeaders().pipe(
      switchMap(headers => this.http.post<T>(`${this.baseUrl}${path}`, body, { headers }))
    );
  }

  put<T>(path: string, body: any) {
    return this.getHeaders().pipe(
      switchMap(headers => this.http.put<T>(`${this.baseUrl}${path}`, body, { headers }))
    );
  }

  delete<T>(path: string) {
    return this.getHeaders().pipe(
      switchMap(headers => this.http.delete<T>(`${this.baseUrl}${path}`, { headers }))
    );
  }
}