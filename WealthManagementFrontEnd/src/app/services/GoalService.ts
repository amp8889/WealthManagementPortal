import { Injectable } from '@angular/core';
import { Goal } from '../types/Goal';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class GoalService {

  constructor(private api: ApiService) {}

  getAll(): Observable<Goal[]> {
    return this.api.get<Goal[]>('/api/goal');
  }

  getById(id: string): Observable<Goal> {
    return this.api.get<Goal>(`/api/goal/${id}`);
  }

  create(goal: Goal): Observable<Goal> {
    return this.api.post<Goal>('/api/goal', goal);
  }

  update(id: string, goal: Goal): Observable<Goal> {
    return this.api.put<Goal>(`/api/goal/${id}`, goal);
  }

  delete(id: string): Observable<void> {
    return this.api.delete<void>(`/api/goal/${id}`);
  }
}