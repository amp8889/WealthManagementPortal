import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {
private baseUrl = `${environment.apiUrl}/api/user`;
private meUrl = `${environment.apiUrl}/api/user/me`;

  private userSubject = new BehaviorSubject<any>(this.loadUser());
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {}


login(email: string, password: string): Observable<any> {
  const headers = new HttpHeaders({
    Authorization: 'Basic ' + btoa(`${email}:${password}`)
  });

  return this.http.get(this.meUrl, {
    headers,
    withCredentials: true
  }).pipe(
    tap(user => {
  this.userSubject.next({ ...user, email, password });
})
  );
}

  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }


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