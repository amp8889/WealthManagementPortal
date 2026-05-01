import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Goal } from '../types/Goal';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GoalService {

  private baseUrl = 'http://localhost:8080/api/goal';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Goal[]> {
    return this.http.get<Goal[]>(this.baseUrl);
  }

  getById(id: string): Observable<Goal> {
    return this.http.get<Goal>(`${this.baseUrl}/${id}`);
  }

  create(client: Goal): Observable<Goal> {
    return this.http.post<Goal>(this.baseUrl, client);
  }

  update(id: string, client: Goal): Observable<Goal> {
    return this.http.put<Goal>(`${this.baseUrl}/${id}`, client);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}