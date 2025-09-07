import { Component } from '@angular/core';
import { ProjectBase } from '../common/project-base/project-base';
import { Project } from '../../api/models';
import { ProjectService } from '../../api/services';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import { MessageService } from "primeng/api";
import { ToastModule } from 'primeng/toast';



@Component({
  selector: 'app-create-project',
  imports: [ProjectBase, ToastModule],
  templateUrl: './create-project.html',
  styleUrl: './create-project.scss',
  standalone: true,
})
export class CreateProject {


  constructor(private readonly projectService: ProjectService, private readonly router: Router
  ) { }

  onCreate(project: Project) {
    console.log('Neues Projekt erstellen:', project);
    if (project.name && project.name.trim().length <= 0) {

    }
    this.projectService.createProject({ body: project }).pipe(take(1)).subscribe({
      next: (response) => {
        this.router.navigate(['/projects']);
      },
      error: (error) => {
        console.error('Fehler beim Erstellen des Projekts:', error);
        // Hier kann z.B. eine Fehlermeldung angezeigt werden
      }
    });
  }

  onCancel() {
    console.log('Projekt-Erstellung abgebrochen');
  }
}