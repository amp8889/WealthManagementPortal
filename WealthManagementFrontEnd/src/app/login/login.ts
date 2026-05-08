import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-login',
  imports: [
    CardModule,
    ButtonModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  constructor(private auth: AuthService) {}

  loginWithMicrosoft() {
    this.auth.login();
  }
}