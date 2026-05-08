import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { ClientRecord } from '../types/ClientRecord';
import { Goal } from '../types/Goal';
import { GoalService } from '../services/GoalService';
import { ClientRecordsService } from '../services/ClientRecords';

import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ProgressBarModule } from 'primeng/progressbar';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-advisor-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DialogModule,
    InputTextModule,
    ButtonModule,
    TagModule,
    ToastModule,
    ConfirmDialogModule,
    ProgressBarModule
  ],
  providers: [ConfirmationService, MessageService],
  templateUrl: './advisor-dashboard.html',
  styleUrl: './advisor-dashboard.css'
})
export class AdvisorDashboard implements OnInit {
  clients = signal<ClientRecord[]>([]);
  goals = signal<Goal[]>([]);
  saving = signal(false);
  dialogVisible = false;

  clientForm: FormGroup;
  editingClient: ClientRecord | null = null;

  tiers = [
    { label: 'Standard', value: 'STANDARD' },
    { label: 'Premium', value: 'PREMIUM' },
    { label: 'Private Banking', value: 'PRIVATE_BANKING' }
  ];

  riskLevels = [
    { label: 'Conservative', value: 'CONSERVATIVE' },
    { label: 'Moderate', value: 'MODERATE' },
    { label: 'Aggressive', value: 'AGGRESSIVE' }
  ];

  objectives = [
    { label: 'Growth', value: 'GROWTH' },
    { label: 'Income', value: 'INCOME' },
    { label: 'Capital Preservation', value: 'CAPITAL_PRESERVATION' },
    { label: 'Balanced', value: 'BALANCED' }
  ];

  private clientService = inject(ClientRecordsService);
  private goalService = inject(GoalService);
  private fb = inject(FormBuilder);
  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);
  private router = inject(Router);

  constructor() {
    this.clientForm = this.fb.group({
      firstName: [''],
      lastName: [''],
      clientTier: ['STANDARD'],
      country: [''],
      riskTolerance: ['MODERATE'],
      primaryObjective: ['BALANCED']
    });
  }

  ngOnInit() {
    this.loadClients();
    this.loadGoals();
  }

  loadClients() {
    this.clientService.getAll().subscribe({
      next: (data) => this.clients.set(data),
      error: (err) => console.error(err)
    });
  }

  loadGoals() {
    this.goalService.getAll().subscribe({
      next: (data) => this.goals.set(data),
      error: (err) => console.error(err)
    });
  }

  getGoalCount(clientId: string): number {
    return this.getClientGoals(clientId).length;
  }

  getClientGoals(clientId: string): Goal[] {
    if (!clientId) {
      return [];
    }

    return this.goals().filter(goal =>
      Array.isArray(goal.clientId)
        ? goal.clientId.includes(clientId)
        : goal.clientId === clientId
    );
  }

  getGoalProgress(goal: Goal): number {
    return Math.min((goal.currentSavedAmount / (goal.targetAmount || 1)) * 100, 100);
  }

  getTierSeverity(tier: string): 'success' | 'info' | 'secondary' {
    switch (tier) {
      case 'PRIVATE_BANKING': return 'success';
      case 'PREMIUM': return 'info';
      default: return 'secondary';
    }
  }

  getRiskSeverity(risk: string): 'danger' | 'warn' | 'success' {
    switch (risk) {
      case 'AGGRESSIVE': return 'danger';
      case 'MODERATE': return 'warn';
      default: return 'success';
    }
  }

  showClientDialog() {
    this.editingClient = null;
    this.clientForm.reset({
      clientTier: 'STANDARD',
      riskTolerance: 'MODERATE',
      primaryObjective: 'BALANCED'
    });
    this.dialogVisible = true;
  }

  saveClient() {
    this.saving.set(true);
    const client = this.clientForm.value;

    this.clientService.create(client).subscribe({
      next: () => {
        this.loadClients();
        this.dialogVisible = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Client record created successfully',
          life: 3000
        });
        this.saving.set(false);
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to create client record',
          life: 3000
        });
        this.saving.set(false);
      }
    });
  }

  confirmDelete(client: ClientRecord) {
    this.confirmationService.confirm({
      message: `Delete ${client.firstName} ${client.lastName}? This action cannot be undone.`,
      accept: () => {
        this.clientService.delete(client.id!).subscribe({
          next: () => {
            this.loadClients();
            this.messageService.add({
              severity: 'success',
              summary: 'Deleted',
              detail: 'Client record removed',
              life: 3000
            });
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Failed to delete client',
              life: 3000
            });
          }
        });
      }
    });
  }

  viewClient(clientId: string) {
    this.router.navigate(['/client', clientId]);
  }
}