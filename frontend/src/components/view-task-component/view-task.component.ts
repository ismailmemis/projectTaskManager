import { Component, OnInit } from '@angular/core';
import { Task } from '../../api/models';
import { TaskService } from '../../api/services';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { take } from 'rxjs';
import { TaskBaseComponent } from '../common/task-base-component/task-base-component';

@Component({
  selector: 'app-view-task-component',
  imports: [TaskBaseComponent, CommonModule, FormsModule],
  templateUrl: './view-task.component.html',
  styleUrl: './view-task.component.scss', 
  standalone: true
})
export class ViewTaskComponent implements OnInit {
  task: Task = {}; 

  constructor(
    private readonly taskService: TaskService, 
    private readonly route: ActivatedRoute, 
    private readonly router: Router
  ) {}


  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id'); 
    if(id) {
      this.taskService.getTaskById({id: +id}).pipe(take(1)).subscribe({
        next: (task) => {
          this.task = task; 
        }, 
        error: () => {
            this.router.navigate(['/tasks']);
          }
      })
    }
  }

  onCancel() {
    this.router.navigate(['/tasks']);
  }

}
