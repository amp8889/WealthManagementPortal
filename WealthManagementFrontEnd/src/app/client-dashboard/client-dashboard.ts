import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, FormArray } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ClientRecord } from '../types/ClientRecord';
import { Goal } from '../types/Goal';
import { GoalService } from '../services/GoalService';
import { ClientRecordsService } from '../services/ClientRecords';
import { AuthService } from '../services/AuthService';

import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ProgressBarModule } from 'primeng/progressbar';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-client-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    ButtonModule,
    ProgressBarModule,
    CardModule,
    TagModule,
    ToastModule,
  ],
  providers: [MessageService, Router],
  templateUrl: './client-dashboard.html',
  styleUrl: './client-dashboard.css',
})
export class ClientDashboard implements OnInit {

  private route = inject(ActivatedRoute);  // move out of constructor



  client = signal<ClientRecord | null>(null);
  goals = signal<Goal[]>([]);
  savingIndex = signal<number | null>(null);

  clientId = "fa26bfc7-76fc-49d0-b99d-681f64a3438b";

  form!: FormGroup;

  constructor(
    private goalService: GoalService,
    private clientService: ClientRecordsService,
    private formBuilder: FormBuilder,
    private messageService: MessageService,

  ) {}

ngOnInit(): void {
  this.form = this.formBuilder.group({
    goals: this.formBuilder.array([])
  });

  this.clientId = this.route.snapshot.paramMap.get('clientId') ?? '';
  this.loadClient();
  this.loadGoals();
}

  loadClient() {
    this.clientService.getById(this.clientId).subscribe({
      next: (data) => this.client.set(data),
      error: (err) => console.error(err)
    });
  }

  getGoalGroup(index: number): FormGroup {
    return this.formArray.at(index) as FormGroup;
  }

  loadGoals() {
    this.goalService.getAll().subscribe(goals => {
      const filtered = goals.filter(g =>
        Array.isArray(g.clientId)
          ? g.clientId.includes(this.clientId)
          : g.clientId === this.clientId
      );

      this.goals.set(filtered);
      const formArray = this.formArray;
      formArray.clear();

      filtered.forEach(goal => {
        formArray.push(
          this.formBuilder.group({
            id: [goal.id],
            currentSavedAmount: [goal.currentSavedAmount]
          })
        );
      });
    });
  }

  get formArray(): FormArray {
    return this.form.get('goals') as FormArray;
  }

  getProgress(index: number, targetAmount: number): number {
    const saved = this.formArray.at(index)?.value?.currentSavedAmount || 0;
    return Math.min((saved / (targetAmount || 1)) * 100, 100);
  }

  getProgressSeverity(progress: number): string {
    if (progress >= 100) return 'success';
    if (progress >= 50) return 'info';
    if (progress >= 25) return 'warn';
    return 'danger';
  }

  updateGoal(index: number) {
    const goal = this.goals()[index];
    const control = this.formArray.at(index);
    this.savingIndex.set(index);

    const updatedGoal: Goal = {
      ...goal,
      currentSavedAmount: control.value.currentSavedAmount
    };

    this.goalService.update(goal.id!, updatedGoal).subscribe({
      next: (res) => {
        this.goals.update(list =>
          list.map(g => g.id === res.id ? res : g)
        );
        this.savingIndex.set(null);
        this.messageService.add({
          severity: 'success',
          summary: 'Saved',
          detail: `${goal.goalName} updated successfully.`,
          life: 3000
        });
      },
      error: () => {
        this.savingIndex.set(null);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to update goal. Please try again.',
          life: 3000
        });
      }
    });
  }
}