import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { SelectModule } from 'primeng/select';
import { InputMaskModule } from 'primeng/inputmask';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { HttpClient, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, SelectModule, InputMaskModule, ButtonModule, DialogModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
})
export class Register {
  registerForm: FormGroup;
  registrationCodeDialogVisible = false;
  isSubmitting = false;

  roleOptions = [
    { label: 'Client', value: 'CLIENT' },
    { label: 'Advisor', value: 'ADVISOR' }
  ];

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {
    this.registerForm = this.fb.group({
      role: ['', Validators.required],
      registrationCode: ['', [Validators.required]]
    });
  }

  showRegistrationCodeDialog() {
    this.registrationCodeDialogVisible = true;
  }

  hideRegistrationCodeDialog() {
    this.registrationCodeDialogVisible = false;
    this.isSubmitting = false;
  }

  onSubmit() {
    if (this.registerForm.invalid || this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;
    
    // Get form values
    const role = this.registerForm.get('role')?.value;
    let registrationCode = this.registerForm.get('registrationCode')?.value || '';
    
    // Format for NoSQL id
    registrationCode = registrationCode.toLowerCase();
    
    // Build query parameters
    const params = new HttpParams()
      .set('role', role)
      .set('relatedId', registrationCode);
    
    // Hit the PUT /api/register endpoint
    this.http.post('/api/register', null, { 
      params: params,
      responseType: 'text'
    }).subscribe({
      next: (response) => {
        alert(response);
        this.hideRegistrationCodeDialog();
        
        window.location.href = '/'; // TODO: Redirect after register
      },
      error: (error) => {
        console.error('Registration failed:', error);
        alert(error.error || 'Registration failed, please try again.');
        this.isSubmitting = false;
      }
    });
  }
}