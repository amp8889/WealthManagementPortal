import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, FormArray, FormControl } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ClientRecord } from '../types/ClientRecord';
import { Goal } from '../types/Goal';
import { GoalService } from '../services/GoalService';
import { ClientRecordsService } from '../services/ClientRecords';

import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ProgressBarModule } from 'primeng/progressbar';

@Component({
  selector: 'app-client-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    ButtonModule,
    ProgressBarModule,

  ],
  templateUrl: './client-dashboard.html',
  styleUrl: './client-dashboard.css',
})
export class ClientDashboard implements OnInit {

  client = signal<ClientRecord | null>(null);
  goals = signal<Goal[]>([]);

  clientId = "fa26bfc7-76fc-49d0-b99d-681f64a3438b";

  form!: FormGroup;

  constructor(
    private goalService: GoalService,
    private clientService: ClientRecordsService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      goals: this.formBuilder.array([])
    });

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


console.log("ALL GOALS FROM BACKEND:", goals);

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

  updateGoal(index: number) {
    const goal = this.goals()[index];
    const control = this.formArray.at(index);

    const updatedGoal: Goal = {
      ...goal,
      currentSavedAmount: control.value.currentSavedAmount
    };

    this.goalService.update(goal.id!, updatedGoal).subscribe({
      next: (res) => {
        this.goals.update(list =>
          list.map(g => g.id === res.id ? res : g)
        );
      }
    });
  }
}