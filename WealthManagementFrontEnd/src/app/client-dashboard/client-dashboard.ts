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
  clientId = "some-client-id";

  form!: FormGroup;

  constructor(
    private goalService: GoalService,
    private clientService: ClientRecordsService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {

    this.form = this.formBuilder.group({
      goalName: [''],
      targetAmount: [''],
      currentSavedAmount: [''],
      goalDate: ['']
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
    this.goalService.getById(this.clientId).subscribe({
      next: (data) => {
        this.goal.set(data);

        if (data) {
          this.form.patchValue({
            ...data,
            goalDate: data.goalDate ? new Date(data.goalDate) : null
          });
        }
      },
      error: (err) => console.error(err)
    });
  }

  saveGoal() {
    if (this.form.invalid || !this.goal()) return;

    const formValue = this.form.value;

    const updatedGoal: Goal = {
      ...this.goal()!,
      goalName: formValue.goalName,
      targetAmount: Number(formValue.targetAmount),
      currentSavedAmount: Number(formValue.currentSavedAmount),
      goalType: this.goal()!.goalType, 
      clientId: this.clientId,

      goalDate: formValue.goalDate
        ? new Date(formValue.goalDate).toISOString().split('T')[0]
        : null
    };

    this.goalService.update(updatedGoal.id!, updatedGoal).subscribe({
      next: (res) => {
        this.goal.set(res);
        console.log("Updated goal:", res);
      },
      error: (err) => console.error(err)
    });
  }

}