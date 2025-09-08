import { Component, OnDestroy } from '@angular/core';
import { TaskService } from '../../api/services';
import { Task } from '../../api/models';
import { AsyncPipe, DatePipe } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { Router } from '@angular/router';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { BehaviorSubject, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-task-component',
  providers: [ConfirmationService, MessageService],
  imports: [ButtonModule, AsyncPipe, TableModule, DatePipe, ConfirmDialogModule],
  templateUrl: './task-overview-component.html',
  styleUrl: './task-overview-component.scss',
  standalone: true
})
export class TaskOverviewComponent implements OnDestroy {

  protected readonly tasks$;
  private readonly refreshSubject$ = new BehaviorSubject<void>(undefined); 

  constructor(private readonly taskService: TaskService, private readonly router: Router, private readonly confirmationService: ConfirmationService, private readonly messageService: MessageService) {
    this.tasks$ = this.refreshSubject$.pipe(switchMap(() => this.taskService.getAllTasks())); 
  }
  ngOnDestroy() {
    this.refreshSubject$.complete(); 
  }

  onDeleteTask(task: Task) {
     this.confirmationService.confirm({
      message: 'Möchten sie die Task löschen?',
      header: 'Task löschen',
      closable: true,
      closeOnEscape: true,
      icon: 'pi pi-exclamation-triangle',
      rejectButtonProps: {
        label: 'Abbrechen',
        severity: 'secondary',
        outlined: true,
      },
      acceptButtonProps: {
        label: 'Löschen',
      },
      accept: () => {
        if(task.id){
          this.taskService.deleteTask({id: task.id}).pipe(
            tap(() => {
              this.refreshSubject$.next(); 
              
            })
          ).subscribe(() => {
            this.messageService.add({
                severity: 'success',
                summary: 'Erfolg',
                detail: 'Task wurde gelöscht',
                life: 3000,
              });
          })
        }
      },
      reject: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Rejected',
          detail: 'You have rejected',
          life: 3000,
        });
      },
    });
  }

  onEditTask(task: Task) {
    this.router.navigate(['tasks/edit', task.id]);
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
