import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ClientRecord } from '../types/ClientRecord';
import { Goal } from '../types/Goal';
import { GoalService } from '../services/GoalService';
import { ClientRecordsService } from '../services/ClientRecords';

import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { DatePicker } from 'primeng/datepicker';
import { ProgressBarModule } from 'primeng/progressbar';
@Component({
  selector: 'app-client-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    ButtonModule,
    DatePicker,
    ProgressBarModule
  ],
  templateUrl: './client-dashboard.html',
  styleUrl: './client-dashboard.css',
})
export class ClientDashboard implements OnInit {

  client = signal<ClientRecord | null>(null);
  goal = signal<Goal | null>(null);


  //This will need to be replaced when we get auth fully working with frontend
  clientId = "fa26bfc7-76fc-49d0-b99d-681f64a3438b";

  form!: FormGroup;

  constructor(
    private goalService: GoalService,
    private clientService: ClientRecordsService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {

    this.form = this.formBuilder.group({
      currentSavedAmount: [''],

    });

    this.loadClient();
    this.loadGoal();

    this.form.valueChanges.subscribe(val => {
      console.log("FORM:", val);
    });
  }

  loadClient() {
    this.clientService.getById(this.clientId).subscribe({
      next: (data) => {
        this.client.set(data);
      },
      error: (err) => console.error(err)
    });
  }

  loadGoal() {
    this.goalService.getAll().subscribe(goals => {
      const goal = goals.find(g => g.clientId === this.clientId);

      if (goal) {
        this.goal.set(goal);
        this.form.patchValue({
          currentSavedAmount: goal.currentSavedAmount
        });
      }
    });
  }

saveGoal() {
  const goal = this.goal();

  if (this.form.invalid || !goal) return;

  const updatedGoal = {
    ...goal,
    currentSavedAmount: this.form.value.currentSavedAmount
  };

  this.goalService.update(goal.id!, updatedGoal).subscribe({
    next: (res) => {
      this.goal.set(res);
    }
  });
}

}