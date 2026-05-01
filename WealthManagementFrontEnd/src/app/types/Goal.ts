export interface Goal {
  id?: string;
  goalName: string;
  targetAmount: number;
  goalType: string;
  goalDate: string; // ISO string
  currentSavedAmount: number;
  clientId: string;
}