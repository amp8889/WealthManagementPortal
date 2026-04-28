import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ClientRecord } from '../types/ClientRecord';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientRecordsService {

  private baseUrl = 'http://localhost:8080/api/clientrecords';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ClientRecord[]> {
    return this.http.get<ClientRecord[]>(this.baseUrl);
  }

  getById(id: string): Observable<ClientRecord> {
    return this.http.get<ClientRecord>(`${this.baseUrl}/${id}`);
  }

  create(client: ClientRecord): Observable<ClientRecord> {
    return this.http.post<ClientRecord>(this.baseUrl, client);
  }

  update(id: string, client: ClientRecord): Observable<ClientRecord> {
    return this.http.put<ClientRecord>(`${this.baseUrl}/${id}`, client);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}