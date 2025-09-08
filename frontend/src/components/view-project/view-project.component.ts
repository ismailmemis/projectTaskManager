import { Component, OnInit } from '@angular/core';
import { Project, Task } from '../../api/models';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService, TaskService } from '../../api/services';
import { concatMap, from, switchMap, take, toArray } from 'rxjs';
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
export class ViewProjectComponent implements OnInit {
  project: Project = {};
  unassignedTask: Task[] = [];
  dialogVisible = false;
  selectedTasks: Task[] = [];


  constructor(
    private readonly projectService: ProjectService,
    private readonly taskService: TaskService,
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
  if (!this.project.id) {
    console.error("Projekt-ID ist undefined");
    return;
  }

  from(this.selectedTasks).pipe(
    // Für jede Task: assignTaskToProject aufrufen, nacheinander (concatMap)
    concatMap(task => 
      this.projectService.assignTaskToProject({ projectId: this.project.id!, taskId: task.id! })
    ),
    // Alle Ergebnisse als Array sammeln
    toArray(),
    // Nach allen Zuweisungen: Projekt neu laden
    switchMap(() => this.projectService.getProjectById({ id: this.project.id! }).pipe(take(1))),
    // Nach Projektladen: Unassigned Tasks neu laden
    switchMap((proj) => {
      this.project = proj;
      return this.taskService.getUnassignedTasks().pipe(take(1));
    })
  ).subscribe({
    next: (tasks) => {
      this.unassignedTask = tasks;
      this.selectedTasks = [];
      this.dialogVisible = false;
      console.log("Alle Tasks erfolgreich zugewiesen und UI aktualisiert");
    },
    error: (err) => {
      console.error("Fehler bei der Zuweisung oder UI-Aktualisierung", err);
      this.dialogVisible = false;
    }
  });
}
}
