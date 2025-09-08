import { Component, OnInit } from '@angular/core';
import { Task } from '../../api/models';
import { TaskService } from '../../api/services';
import { ActivatedRoute, Router } from '@angular/router';
import { take } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { TaskBaseComponent } from '../common/task-base-component/task-base-component';

@Component({
  selector: 'app-edit-task-component',
  imports: [ButtonModule, TaskBaseComponent, CommonModule, FormsModule],
  templateUrl: './edit-task-component.html',
  styleUrl: './edit-task-component.scss'
})
export class EditTaskComponent implements OnInit {

  task: Task = {};

  constructor(
    private readonly taskService: TaskService,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.taskService.getTaskById({ id: +id }).pipe(take(1)).subscribe({
        next: (task) => {
          this.task = task;
        },
        error: () => {
          this.router.navigate(['/tasks']);
        }
      });
    }
  }

  onSave(): void {
    this.taskService.updateTask({ id: this.task.id!, body: this.task }).pipe(take(1)).subscribe({
      next: () => {
        this.router.navigate(['/tasks']);
      },
      error: () => {
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/tasks']);
  }
}