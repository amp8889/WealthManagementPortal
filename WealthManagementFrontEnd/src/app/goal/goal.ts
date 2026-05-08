import { Component, OnInit, signal } from '@angular/core';
import { GoalService } from '../services/GoalService';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { GoalType } from '../types/GoalType';
import { Goal as GoalModel } from '../types/Goal';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { Select } from 'primeng/select';
import { DeleteConfirmationModal } from '../components/delete-confirmation-modal/delete-confirmation-modal';
import { CommonModule } from '@angular/common';
import { DatePicker } from 'primeng/datepicker';
import { ClientRecordsService } from '../services/ClientRecords';
import { ProgressBarModule } from 'primeng/progressbar';
import { futureDateValidator } from '../validators/futureDateValidator';
import { MsalBroadcastService } from '@azure/msal-angular';
import { InteractionStatus } from '@azure/msal-browser';
import { filter, take } from 'rxjs/operators';

@Component({
  selector: 'app-goal',
  imports: [TableModule, ButtonModule, DialogModule, InputTextModule, ReactiveFormsModule,
    FormsModule, Select, DeleteConfirmationModal, CommonModule, DatePicker, ProgressBarModule],
  templateUrl: './goal.html',
  styleUrl: './goal.css',
})
export class Goal implements OnInit {

  clients = signal<any[]>([]);
  goals = signal<GoalModel[]>([]);
  selectedGoal = signal<GoalModel | null>(null);
  showFormDialog = signal<boolean>(false);
  showDeleteDialog = signal<boolean>(false);

  constructor(
    private goalService: GoalService,
    private clientService: ClientRecordsService,
    private formBuilder: FormBuilder,
    private msalBroadcastService: MsalBroadcastService  // ← add this
  ) {}

  goalType = Object
    .entries(GoalType).map(([key, value]) => ({
      label: value,
      value: key
    }));

  form!: FormGroup;

  ngOnInit(): void {
    // ← Wait for MSAL to finish initializing before making any API calls
    this.msalBroadcastService.inProgress$
      .pipe(
        filter((status: InteractionStatus) => status === InteractionStatus.None),
        take(1)
      )
      .subscribe(() => {
        this.loadGoals();
        this.clientService.getAll().subscribe({
          next: (data) => this.clients.set(data),
          error: (err) => console.error(err)
        });
      });

    this.form = this.formBuilder.group({
      goalName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      targetAmount: ['', [Validators.required, Validators.min(1), Validators.max(100_000_000)]],
      goalType: ['', [Validators.required]],
      goalDate: ['', [Validators.required, futureDateValidator]],
      currentSavedAmount: ['', [Validators.required, Validators.min(0), Validators.max(100_000_000)]],
      clientId: ['', [Validators.required]],
    });
  }

  loadGoals() {
    this.goalService.getAll().subscribe({
      next: (data) => this.goals.set(data),
      error: (err) => console.error(err)
    });
  }

  getClientName(clientId: string): string {
    const client = this.clients().find(c => c.id === clientId);
    return client ? `${client.firstName} ${client.lastName}` : '—';
  }

  saveGoal() {
    if (this.form.invalid) return;

    const { goalName, targetAmount, goalType, goalDate, currentSavedAmount, clientId } = this.form.value;
    const formattedDate = goalDate
      ? new Date(goalDate).toISOString().split('T')[0]
      : null;

    const payload: GoalModel = {
      goalName, targetAmount, goalType,
      goalDate: formattedDate,
      currentSavedAmount, clientId
    };

    if (this.selectedGoal() === null) {
      this.goalService.create(payload).subscribe({
        next: (data) => {
          this.goals.update((currentList) => [...currentList, data]);
          this.showFormDialog.set(false);
        },
        error: (err) => {
          console.error(err);
          this.showFormDialog.set(false);
        }
      });
    } else {
      payload.id = this.selectedGoal()!.id;
      this.goalService.update(payload!.id!, payload).subscribe({
        next: (data) => {
          this.goals.update((currentList) =>
            currentList.map(goal => goal.id === data.id ? data : goal)
          );
          this.showFormDialog.set(false);
        },
        error: (err) => {
          console.error(err);
          this.showFormDialog.set(false);
        }
      });
    }
  }

  handleDeleteGoal(goal: GoalModel) {
    this.selectedGoal.set(goal);
    this.showDeleteDialog.set(true);
  }

  deleteGoal() {
    if (this.selectedGoal() === null || this.selectedGoal()!.id === null) {
      console.log('NO ID');
      return;
    }

    this.goalService.delete(this.selectedGoal()!.id!).subscribe({
      next: () => {
        this.goals.update((currentList) =>
          currentList.filter(goals => goals.id !== this.selectedGoal()!.id)
        );
        this.showDeleteDialog.set(false);
      },
      error: (err) => {
        console.log(err);
        this.showDeleteDialog.set(false);
      }
    });
  }
}