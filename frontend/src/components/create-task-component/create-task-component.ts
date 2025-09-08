import { Component } from '@angular/core';
import { TaskBaseComponent } from '../common/task-base-component/task-base-component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../../api/services';
import { Router } from '@angular/router';
import { Task, TaskStatus } from '../../api/models';

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
  }

}
