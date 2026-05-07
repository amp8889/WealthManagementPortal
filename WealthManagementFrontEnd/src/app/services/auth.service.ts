import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
private baseUrl = 'http://localhost:8080/api/user';
private meUrl = 'http://localhost:8080/api/user/me';
  private userSubject = new BehaviorSubject<any>(this.loadUser());
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {}

  // -------------------------
  // LOGIN
  // -------------------------
login(email: string, password: string): Observable<any> {
  const headers = new HttpHeaders({
    Authorization: 'Basic ' + btoa(`${email}:${password}`)
  });

  return this.http.get(this.meUrl, {
    headers,
    withCredentials: true
  }).pipe(
    tap(user => this.userSubject.next(user))
  );
}
  // -------------------------
  // REGISTER
  // -------------------------
  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  // -------------------------
  // USER STATE
  // -------------------------
  setUser(user: any) {
    this.userSubject.next(user);
  }

  getUser() {
    return this.userSubject.value;
  }

  isAuthenticated(): boolean {
    return !!this.getUser();
  }

  logout() {
    this.userSubject.next(null);
  }

  private loadUser() {
    return null;
  }
}