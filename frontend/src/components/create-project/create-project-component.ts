import { Component } from '@angular/core';
import { Project } from '../../api/models';
import { ProjectService } from '../../api/services';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import { ProjectBaseComponent } from '../common/project-base/project-base-component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';


@Component({
  selector: 'app-create-project',
  imports: [ProjectBaseComponent,  ConfirmDialogModule],
  templateUrl: './create-project-component.html',
  styleUrl: './create-project-component.scss',
  standalone: true
})
export class CreateProjectComponent {


  constructor(private readonly projectService: ProjectService, private readonly router: Router) { }

  onCreate(project: Project) {
    console.log('Neues Projekt erstellen:', project);
    if (project.name && project.name.trim().length <= 0) {
      // Error handling
      return; 
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
    this.router.navigate(['/projects']);
  }
}