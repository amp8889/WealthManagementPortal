import { Component, OnInit, signal } from '@angular/core';
import { ClientRecordsService } from '../services/ClientRecords';
import { ClientRecord } from '../types/ClientRecord';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { table } from 'console';
import { ClientTier } from '../types/ClientTier';
import { RiskTolerance } from '../types/RiskTolerance';
import { PrimaryObjective } from '../types/PrimaryObjective';
import { Select } from 'primeng/select';
import { DeleteConfirmationModal } from '../components/delete-confirmation-modal/delete-confirmation-modal';

@Component({
  selector: 'app-client-records',
  imports: [TableModule, ButtonModule, DialogModule, InputTextModule, ReactiveFormsModule, FormsModule, Select, DeleteConfirmationModal],
  templateUrl: './client-records.html',
  styleUrl: './client-records.css',
})
export class ClientRecords implements OnInit {




  clients = signal<ClientRecord[]>([]);
  selectedClient = signal<ClientRecord | null>(null);

 
  showFormDialog = signal<boolean>(false);
  showDeleteDialog = signal<boolean>(false);


  constructor(private clientService: ClientRecordsService, private formBuilder: FormBuilder
) {

    
  }


  clientTierOptions = Object.values(ClientTier);
  riskToleranceOptions = Object.values(RiskTolerance);
  primaryObjectiveOptions = Object.values(PrimaryObjective);

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
      goalIds: [""]
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

    const {id, firstName, lastName, clientTier, country, riskTolerance, primaryObjective, goalIds} = this.form.value;
    const clientTierKey = Object.entries(ClientTier).find(([, val]) => val === clientTier)?.[0];
    const riskToleranceKey = Object.entries(RiskTolerance).find(([, val]) => val === riskTolerance)?.[0];
    const primaryObjectiveKey = Object.entries(PrimaryObjective).find(([, val]) => val === primaryObjective)?.[0];

    const payload: ClientRecord = {
      // clientRecordsId: "1",
      firstName,
      lastName,
      clientTier: clientTierKey as ClientTier,
      country,
      riskTolerance: riskToleranceKey as RiskTolerance,
      primaryObjective: primaryObjectiveKey as PrimaryObjective,
      goalIds
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


  handleCreateMovie(){
    this.selectedClient.set(null)

    this.form.setValue({
      firstName: "",
      lastName: "",
      clientTier: null,
      country: "",
      riskTolerance: null,
      primaryObjective: null,
      goalIds: ""


    })
    this.showFormDialog.set(true);

  }




  handleUpdateClientRecord(clientRecord: ClientRecord) {

    console.log("SELECTED Client Record:");
    console.log(clientRecord);
    
    // track which movie was selected
    this.selectedClient.set(clientRecord);

    // pre-filling form with values
    this.form.setValue({
      firstName: clientRecord.firstName,
      lastName: clientRecord.lastName,
      clientTier: ClientTier[clientRecord.clientTier as string as keyof typeof ClientTier],
      country: clientRecord.country,
      riskTolerance: RiskTolerance[clientRecord.riskTolerance as string as keyof typeof RiskTolerance],
      primaryObjective: PrimaryObjective[clientRecord.primaryObjective as string as keyof typeof PrimaryObjective]
    })

    // open the dialog
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


