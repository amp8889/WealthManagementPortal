export interface Goal {
  id?: string;
  goalName: string;
  targetAmount: number;
  goalType: string;
  goalDate: string | null; 
  currentSavedAmount: number;
  clientId: string;
}