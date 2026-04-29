import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MenuItem } from 'primeng/api';
import {ButtonModule } from "primeng/button";
import {MenubarModule } from "primeng/menubar";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ButtonModule, MenubarModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  constructor(
    private router: Router
  ) {}
  
  navItems: MenuItem[] = [
    {label: "Movies", command: () => this.router.navigate(["/client_records"])}
  ]}
