export interface ClientRecord {
  clientRecordsId?: string;
  firstName: string;
  lastName: string;
  clientTier: string;
  country: string;
  riskTolerance: string;
  primaryObjective: string;
  goalIds: string[];
}