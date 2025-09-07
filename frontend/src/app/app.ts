import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MenuModule } from 'primeng/menu';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MenuModule ],
  standalone: true,
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('taskmanager');

  constructor(private router: Router) {}

  protected menuItems = [
    {
      label: "Projekte", 
      icon: "pi pi-folder",
      routerLink: "/projects", 
      routerLinkActiveOptions: { exact: false }
    }, 
    {
      label: "Aufgaben", 
      icon: "pi pi-list", 
      routerLink: "/tasks", 
      routerLinkActiveOptions: { exact: false }
    }
  ]
}
