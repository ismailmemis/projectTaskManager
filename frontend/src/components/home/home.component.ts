import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { ProjectService } from '../../api/services';
import { AsyncPipe } from '@angular/common';


@Component({
  selector: 'app-home',
  imports: [ButtonModule, AsyncPipe],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  standalone: true
})
export class Home {

  protected readonly projects$;

  constructor(private readonly projectService: ProjectService) {
    this.projects$ = this.projectService.listProjects();
    console.log("sendet request? " + this.projects$); 
  }
}
