import { Component, OnInit } from '@angular/core';
import { Project } from '../../api/models';
import { ProjectService } from '../../api/services';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { take } from 'rxjs';
import { ProjectBaseComponent } from '../common/project-base/project-base-component';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-edit-project',
  standalone: true,
  imports: [ButtonModule, ProjectBaseComponent, CommonModule, FormsModule],
  templateUrl: './edit-project-component.html',
  styleUrl: './edit-project-component.scss',
})
export class EditProjectComponent implements OnInit {

  project: Project = {};

  constructor(
    private readonly projectService: ProjectService,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
  ) { }

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

  onEdit(): void {
    this.projectService.updateProject({ id: this.project.id!, body: this.project }).pipe(take(1)).subscribe({
      next: () => {
        this.router.navigate(['/projects']);
      },
      error: () => {
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/projects']);
  }
}
