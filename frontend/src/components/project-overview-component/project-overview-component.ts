import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { ProjectService } from '../../api/services';
import { AsyncPipe, DatePipe } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Project } from '../../api/models';
import { Router } from '@angular/router';
@Component({
  selector: 'app-project',
  imports: [ButtonModule, AsyncPipe, TableModule, DatePipe],
  templateUrl: './project-overview-component.html',
  styleUrls: ['./project-overview-component.scss'],
  standalone: true
})
export class ProjectOverviewComponent {

  protected readonly projects$;

  constructor(private readonly projectService: ProjectService, private readonly router: Router) {
    this.projects$ = this.projectService.listProjects();
  }

  onCreateProject() {
     this.router.navigate(['/projects/create']);    
  }

  onDeleteProject(project: Project) {

  }

  onEditProject(project: Project) {
    console.log("click"); 
    this.router.navigate(['/projects/edit', project.id]);   
  }

  onViewProject(project: Project) {
    console.log("view clocked"); 
    this.router.navigate(['/projects/view', project.id]);   
  }
}
