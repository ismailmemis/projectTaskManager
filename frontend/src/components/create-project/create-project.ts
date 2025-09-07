import { Component } from '@angular/core';
import { ProjectBase } from '../common/project-base/project-base';
import { Project } from '../../api/models';

@Component({
  selector: 'app-create-project',
  imports: [ProjectBase],
  templateUrl: './create-project.html',
  styleUrl: './create-project.scss'
})
export class CreateProject {

  onCreate(project: Project) {
    console.log('Neues Projekt erstellen:', project);
  }

  onCancel() {
    console.log('Projekt-Erstellung abgebrochen');
  }
}