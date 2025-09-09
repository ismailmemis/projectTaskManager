import { Component, OnInit } from '@angular/core';
import { AssignTaskToProject, Project, Task } from '../../api/models';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService, TaskService } from '../../api/services';
import { concatMap, filter, from, of, switchMap, take, toArray } from 'rxjs';
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

    //rxjs
    //Programmbibliothek für Reaktive Programmierung
    from(this.selectedTasks).pipe( // wandle array der ausgewählten taks in ein Observable um
      concatMap(task => { // für jeden Task seqquentiell eine payload generieren und request aufrufen
        if (!task.id) return of(null); //return Observable null wenn task keine id hat

        const payload: AssignTaskToProject = {
          projectId: this.project.id!,
          taskId: task.id
        };
        return this.projectService.assignTaskToProject({ body: payload });
      }),
      filter(result => result !== null),
      toArray()
    ).subscribe({
      // Direkte UI-Updates
      next: (assignedTasks) => {
        // Direkt die lokalen Arrays aktualisieren
        const assigned = assignedTasks.filter(Boolean) as Task[];
        /**
         * alle erfolgreichen Tasks dem Projekt hinzufügen
         * diese Tasks auch aus der Liste der unassigned tasks entfernen
         */
        assigned.forEach(task => {
          this.project.tasks = [...(this.project.tasks || []), task]; // Task zum Projekt hinzufügen
          this.unassignedTask = this.unassignedTask.filter(t => t.id !== task.id); // aus unassigned entfernen
        });

        this.selectedTasks = [];
        this.dialogVisible = false;
        console.log("Alle Tasks erfolgreich zugewiesen und UI aktualisiert");
      },
      error: (err) => {
        console.error("Fehler bei der Zuweisung", err);
        this.dialogVisible = false;
      }
    });
  }
}
