import { Component, OnInit, signal } from '@angular/core';
import { ClientRecordsService } from '../services/ClientRecords';
import { ClientRecord } from '../types/ClientRecord';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { table } from 'console';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClientTier } from '../types/ClientTier';
import { RiskTolerance } from '../types/RiskTolerance';
import { PrimaryObjective } from '../types/PrimaryObjective';
import { Select } from 'primeng/select';
import { DeleteConfirmationModal } from '../components/delete-confirmation-modal/delete-confirmation-modal';
import { DatePickerModule } from 'primeng/datepicker';



@Component({
  selector: 'app-client-records',
  imports: [TableModule, ButtonModule, DialogModule, InputTextModule, ReactiveFormsModule, FormsModule, Select, DeleteConfirmationModal, DatePickerModule],
  templateUrl: './client-records.html',
  styleUrl: './client-records.css',
})
export class ClientRecords implements OnInit {




  clients = signal<ClientRecord[]>([]);
  selectedClient = signal<ClientRecord | null>(null);

 
  showFormDialog = signal<boolean>(false);
  showDeleteDialog = signal<boolean>(false);


  constructor(private clientService: ClientRecordsService, private formBuilder: FormBuilder) {

    
  }


clientTierOptions = Object.entries(ClientTier).map(([key, value]) => ({
  label: value,  
  value: key      
}));

  riskToleranceOptions = Object.entries(RiskTolerance).map(([key, value]) => ({
    label: value,
    value: key
  }));
  primaryObjectiveOptions = Object.entries(PrimaryObjective).map(([key, value]) => ({
    label: value,
    value: key
  }));

  form!: FormGroup;

  ngOnInit(): void {
    this.loadClients();


    this.form = this.formBuilder.group({
      firstName: ["", [Validators.required]],
      lastName: ["", [Validators.required]],
      clientTier: ["", [Validators.required]],
      country: ["", [Validators.required]],
      riskTolerance: [""],
      primaryObjective: [""],
    });


  }






  loadClients() {
    this.clientService.getAll().subscribe({
      next:(data) => {
        this.clients.set(data);
      },

      error:(err) => {
        console.error(err)
      }
      
    });
  }




  saveClient() {
      if(this.form.invalid) {
      return;
    }

    const {id, firstName, lastName, clientTier, country, riskTolerance, primaryObjective} = this.form.value;
    // const clientTierKey = Object.entries(ClientTier).find(([, val]) => val === clientTier)?.[0];
    // const riskToleranceKey = Object.entries(RiskTolerance).find(([, val]) => val === riskTolerance)?.[0];
    // const primaryObjectiveKey = Object.entries(PrimaryObjective).find(([, val]) => val === primaryObjective)?.[0];

    const payload: ClientRecord = {
      // clientRecordsId: "1",
      firstName,
      lastName,
      clientTier,
      country,
      riskTolerance,
      primaryObjective
      
    }

    if(this.selectedClient() === null){
      this.clientService.create(payload).subscribe({
        next: (data) => {
          this.clients.update((currentList) => [...currentList, data]);
          this.showFormDialog.set(false);
        },
        error:(err) => {
          console.error(err);
          this.showFormDialog.set(false);
        }
      })
    }
    else {
      payload.id = this.selectedClient()!.id;
      this.clientService.update(payload!.id!, payload).subscribe({
        next: (data) => {
          this.clients.update((currentList) => currentList.map(clientRecord => clientRecord.id === data.id ? data : clientRecord));
          this.showFormDialog.set(false);
        },
        error: (err) => {
          console.error(err)
          this.showFormDialog.set
        }
      })
    }
  }


  handleUpdateClientRecord(clientRecord: ClientRecord) {

    console.log("SELECTED Client Record:");
    console.log(clientRecord);
    
    this.selectedClient.set(clientRecord);
    // This is for pre-filling form with values that are already set
    this.form.setValue({
      firstName: clientRecord.firstName,
      lastName: clientRecord.lastName,
      clientTier: clientRecord.clientTier,
      country: clientRecord.country,
      riskTolerance: clientRecord.riskTolerance,
      primaryObjective: clientRecord.primaryObjective
    })
    

    
    this.showFormDialog.set(true);
  }



  handleDeleteClientRecord(clientRecord: ClientRecord) {
    this.selectedClient.set(clientRecord);
    this.showDeleteDialog.set(true);
  }



  deleteClient() {

    if(this.selectedClient() === null || this.selectedClient()!.id === null) {
      console.log("NO ID")
      return
    }

    this.clientService.delete(this.selectedClient()!.id!).subscribe({
      next: () => {
        this.clients.update((currentList) => currentList.filter(clientRecords => clientRecords.id !== this.selectedClient()!.id));
        this,this.showDeleteDialog.set(false);
      },
      error: (err) =>{
        console.log(err)
        this.showDeleteDialog.set(false);
      }


    })



  }



}


