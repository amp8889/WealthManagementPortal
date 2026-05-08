import { Injectable } from '@angular/core';
import { ClientRecord } from '../types/ClientRecord';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class ClientRecordsService {

  constructor(private api: ApiService) {}

  getAll(): Observable<ClientRecord[]> {
    return this.api.get<ClientRecord[]>('/api/clientrecords');
  }

  getById(id: string): Observable<ClientRecord> {
    return this.api.get<ClientRecord>(`/api/clientrecords/${id}`);
  }

  create(client: ClientRecord): Observable<ClientRecord> {
    return this.api.post<ClientRecord>('/api/clientrecords', client);
  }

  update(id: string, client: ClientRecord): Observable<ClientRecord> {
    return this.api.put<ClientRecord>(`/api/clientrecords/${id}`, client);
  }

  delete(id: string): Observable<void> {
    return this.api.delete<void>(`/api/clientrecords/${id}`);
  }
}