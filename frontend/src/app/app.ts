import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
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

  protected menuItems = [
    {
      label: "Projekte", 
      icon: "pi-folder"
    }, 
    {
      label: "Aufgaben", 
      icon: "pi-list"
    }
  ]
}
