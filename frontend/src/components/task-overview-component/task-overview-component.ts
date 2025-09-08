import { Component } from '@angular/core';
import { TaskService } from '../../api/services';
import { Task } from '../../api/models';
import { AsyncPipe, DatePipe } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { Router } from '@angular/router';

@Component({
  selector: 'app-task-component',
  imports: [ButtonModule, AsyncPipe, TableModule, DatePipe],
  templateUrl: './task-overview-component.html',
  styleUrl: './task-overview-component.scss', 
  standalone: true
})
export class TaskOverviewComponent {

  protected readonly tasks$; 

  constructor(private readonly taskService: TaskService, private readonly router: Router) {
    this.tasks$ = this.taskService.getAllTasks(); 
  }

    onDeleteTask(task: Task) {
  
    }
  
    onEditTask(task: Task) {
      
    }

    onCreateTask() {
      console.log("onj create task"); 
      this.router.navigate(['tasks/create']); 
    }
  
    onViewTask(task: Task) {
      console.log("view clicked"); 
      this.router.navigate(['tasks/view', task.id]); 
    }


}
