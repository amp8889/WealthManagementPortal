import { Injectable, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../environments/environment';

export type UserRole = 'ADMIN' | 'ADVISOR' | 'CLIENT';

export interface AuthUser {
  _id: string;
  role: UserRole;
  relatedId: string;
  firstName: string;
  lastName: string;
  email: string;
  avatarUrl: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API = environment.apiUrl;
  currentUser = signal<AuthUser | null>(null);

  constructor(private http: HttpClient) { }

  fetchCurrentUser() {
    return this.http.get<AuthUser>(`${this.API}/api/users/me`).pipe(
      tap(user => this.currentUser.set(user))
    );
  }

  getRole(): UserRole | null {
    return this.currentUser()?.role ?? null;
  }

  isAdmin(): boolean {
    return this.currentUser()?.role === 'ADMIN';
  }

  isAdvisor(): boolean {
    return this.currentUser()?.role === 'ADVISOR';
  }

  isClient(): boolean {
    return this.currentUser()?.role === 'CLIENT';
  }

  getRelatedId(): string | null {
    return this.currentUser()?.relatedId ?? null;
  }

  register(role: string, registrationCode: string) {
    const params = new HttpParams()
      .set('role', role)
      .set('relatedId', registrationCode.toLowerCase());

    return this.http.post(`${this.API}/api/register`, null, {
      params: params,
      responseType: 'text'
    });
  }
}