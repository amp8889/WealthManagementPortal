export interface Goal {
  goalId?: string;
  goalName: string;
  targetAmount: number;
  goalType: string;
  goalDate: string; // ISO string
  currentSavedAmount: number;
  clientId: string;
}