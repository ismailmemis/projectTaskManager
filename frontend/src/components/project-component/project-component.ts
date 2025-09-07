import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { ProjectService } from '../../api/services';
import { AsyncPipe, DatePipe } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Project } from '../../api/models';


@Component({
  selector: 'app-project',
  imports: [ButtonModule, AsyncPipe, TableModule, DatePipe],
  templateUrl: './project-component.html',
  styleUrls: ['./project-component.scss'],
  standalone: true
})
export class ProjectComponent {

  protected readonly projects$;

  constructor(private readonly projectService: ProjectService) {
    this.projects$ = this.projectService.listProjects();
  }

  onDeleteProject(project: Project) {

  }

  onEditProject(project: Project) {

  }

  onViewProject(project: Project) {

  }
}
