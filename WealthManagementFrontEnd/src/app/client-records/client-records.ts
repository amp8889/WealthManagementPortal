import { Component, OnInit, signal } from '@angular/core';
import { ClientRecordsService } from '../services/ClientRecords';
import { ClientRecord } from '../types/ClientRecord';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { table } from 'console';

@Component({
  selector: 'app-client-records',
  imports: [TableModule, ButtonModule, DialogModule, InputTextModule, ReactiveFormsModule, FormsModule],
  templateUrl: './client-records.html',
  styleUrl: './client-records.css',
})
export class ClientRecords implements OnInit {





  clients = signal<ClientRecord[]>([]);
  selectedClient: ClientRecord = this.emptyClient();
  dialogVisible = false;
  constructor(private clientService: ClientRecordsService) {}

  ngOnInit(): void {
    this.loadClients();
  }



  emptyClient(): ClientRecord {
    return {
      firstName: '',
      lastName: '',
      clientTier: '',
      country: '',
      riskTolerance: '',
      primaryObjective: '',
      goalIds: []
    };
  }


  loadClients() {
    this.clientService.getAll().subscribe({
      next:(data) => {
        this.clients.set(data);
      },

      error:(err) => {
        console.error(err)
      }
      
    });
  }



  openNew() {
    this.selectedClient = this.emptyClient();
    this.dialogVisible = true;
  }

  editClient(client: ClientRecord) {
    this.selectedClient = { ...client };
    this.dialogVisible = true;
  }

  saveClient() {
    if (this.selectedClient.clientRecordsId) {
      this.clientService.update(this.selectedClient.clientRecordsId, this.selectedClient)
        .subscribe(() => this.loadClients());
    } else {
      this.clientService.create(this.selectedClient)
        .subscribe(() => this.loadClients());
    }
    this.dialogVisible = false;
  }

  deleteClient(id: string) {
    this.clientService.delete(id).subscribe(() => this.loadClients());
  }



}


