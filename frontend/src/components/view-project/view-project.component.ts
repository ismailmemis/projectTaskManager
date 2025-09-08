import { Component, OnInit } from '@angular/core';
import { Project, Task } from '../../api/models';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService, TaskService } from '../../api/services';
import { take } from 'rxjs';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProjectBaseComponent } from '../common/project-base/project-base-component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { MultiSelectModule } from 'primeng/multiselect';

@Component({
  selector: 'app-view-project',
  imports: [ProjectBaseComponent, CommonModule, FormsModule, ButtonModule, MultiSelectModule, TableModule, DatePipe, DialogModule],
  templateUrl: './view-project.component.html',
  styleUrl: './view-project.component.scss', 
  standalone: true
})
export class ViewProjectComponent  implements OnInit {
   project: Project = {};
   unassignedTask: Task[] = []; 
   dialogVisible = false; 
  selectedTasks: Task[] = [];  


  constructor(
    private readonly projectService: ProjectService,
    private readonly taskService: TaskService,
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

        this.taskService.getUnassignedTasks().pipe(take(1)).subscribe({
          next: (tasks) => {
            this.unassignedTask = tasks; 
          }
        })
      }
    }

    onCancel() {
      this.router.navigate(['/projects']);
    }

    openTaskModal() {
      this.dialogVisible = true; 
    }

    onCancelDialog() {
      this.dialogVisible = false;
      this.selectedTasks = []; 
    }

    onSaveTasks() {
      console.log("Ausgewählte Tasks:", this.selectedTasks);
      // hier kannst du die ausgewählten Tasks z.B. ans Backend senden oder ins Projekt hängen
      this.dialogVisible = false;
    }
}
