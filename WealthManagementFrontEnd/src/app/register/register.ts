// import { Component } from '@angular/core';
// import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
// import { AuthService } from '../services/auth.service';
// import { CommonModule } from '@angular/common';
// import { ButtonModule } from 'primeng/button';
// import { CardModule } from 'primeng/card';
// import { InputTextModule } from 'primeng/inputtext';
// import { PasswordModule } from 'primeng/password';
// import { Router } from '@angular/router';

// @Component({
//   selector: 'app-register',
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     CardModule,
//     PasswordModule,
//     InputTextModule,
//     ButtonModule,
//   ],
//   templateUrl: './register.html',
//   styleUrl: './register.css',
// })
// export class Register {
//   form: FormGroup;

//   constructor(
//     private fb: FormBuilder,
//     private auth: AuthService,
//     private router: Router
//   ) {
//     this.form = this.fb.group({
//       email: ['', [Validators.required, Validators.email]],
//       password: ['', Validators.required],
//       firstName: ['', Validators.required],
//       lastName: ['', Validators.required]
//     });
//   }

//   submit() {
//     if (this.form.invalid) return;

//     this.auth.register(this.form.value).subscribe({
//       next: () => {
//         console.log('Registered successfully');
//         this.router.navigate(['/login']); 
//       },
//       error: err => console.error(err)
//     });
//   }
// }