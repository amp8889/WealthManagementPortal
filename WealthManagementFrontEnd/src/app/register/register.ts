import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { SelectModule } from 'primeng/select';
import { InputMaskModule } from 'primeng/inputmask';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { HttpClient, HttpParams } from '@angular/common/http';
import { AuthService } from '../services/AuthService';

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
    private authService: AuthService
  ) {
    this.registerForm = this.fb.group({
      role: ['CLIENT', Validators.required],
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

    this.authService.register(role, registrationCode).subscribe({
      next: (response) => {
        this.hideRegistrationCodeDialog();
        this.authService.fetchCurrentUser().subscribe(() => {
          window.location.href = '/';
        });
      },
      error: (error) => {
        console.error('Registration failed:', error);
        this.isSubmitting = false;
      }
    });
  }
}