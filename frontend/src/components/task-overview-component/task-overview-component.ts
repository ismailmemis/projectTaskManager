import { Component } from '@angular/core';
import { TaskService } from '../../api/services';
import { Task } from '../../api/models';
import { AsyncPipe, DatePipe } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-task-component',
  imports: [ButtonModule, AsyncPipe, TableModule, DatePipe],
  templateUrl: './task-overview-component.html',
  styleUrl: './task-overview-component.scss'
})
export class TaskOverviewComponent {

  protected readonly tasks$; 

  constructor(private readonly taskService: TaskService) {
    this.tasks$ = this.taskService.getAllTasks(); 
  }

    onDeleteTask(task: Task) {
  
    }
  
    onEditTask(task: Task) {
  
    }
  
    onViewTask(task: Task) {
  
    }


}
