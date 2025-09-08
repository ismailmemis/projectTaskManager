import { Component } from '@angular/core';
import { TaskBaseComponent } from '../common/task-base-component/task-base-component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../../api/services';
import { Router } from '@angular/router';
import { Task, TaskStatus } from '../../api/models';
import { take } from 'rxjs';

@Component({
  selector: 'app-create-task-component',
  imports: [TaskBaseComponent, CommonModule, FormsModule],
  templateUrl: './create-task-component.html',
  styleUrl: './create-task-component.scss'
})
export class CreateTaskComponent {

  task: Task = {};

  constructor(private readonly taskService: TaskService, private readonly router: Router) {

  }

  onCancel() {
    this.router.navigate(['/tasks']);
  }

  onSave() {
    console.log("create task on save");
    console.log("task: ", this.task);
    this.task.status = TaskStatus.Offen;
    if (!this.task.title || !this.task.description) {
      return;
    }
    this.taskService.createNewTask({ body: this.task }).pipe(take(1)).subscribe({
      next: (response) => {
        this.router.navigate(['/tasks']);
      },
      error: (error) => {
        console.error('Fehler beim Erstellen des Projekts:', error);
        // Hier kann z.B. eine Fehlermeldung angezeigt werden
      }
    });
  }
}