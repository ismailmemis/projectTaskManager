import { Component, OnInit } from '@angular/core';
import { Project } from '../../api/models';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService } from '../../api/services';
import { take } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProjectBaseComponent } from '../common/project-base/project-base-component';

@Component({
  selector: 'app-view-project',
   imports: [ProjectBaseComponent, CommonModule, FormsModule],
  templateUrl: './view-project.component.html',
  styleUrl: './view-project.component.scss'
})
export class ViewProjectComponent  implements OnInit {
   project: Project = {};

  constructor(
    private readonly projectService: ProjectService,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
  ) {}

    ngOnInit(): void {
      const id = this.route.snapshot.paramMap.get('id');
      if (id) {
        this.projectService.getProjectById({ id: +id }).pipe(take(1)).subscribe({
          next: (project) => {
            this.project = project;
          },
          error: () => {
            this.router.navigate(['/projects']);
          }
        });
      }
    }

    onCancel() {
      this.router.navigate(['/projects']);
    }
}
