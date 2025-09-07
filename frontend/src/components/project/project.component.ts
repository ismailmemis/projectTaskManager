import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { ProjectService } from '../../api/services';
import { AsyncPipe } from '@angular/common';


@Component({
  selector: 'app-project',
  imports: [ButtonModule, AsyncPipe],
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss'],
  standalone: true
})
export class Project {

  protected readonly projects$;

  constructor(private readonly projectService: ProjectService) {
    this.projects$ = this.projectService.listProjects();
    console.log("sendet request? " + this.projects$); 
  }
}
