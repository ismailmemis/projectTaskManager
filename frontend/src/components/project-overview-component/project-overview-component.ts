import { Component, OnDestroy } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { ProjectService } from '../../api/services';
import { AsyncPipe, DatePipe } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Project } from '../../api/models';
import { Router } from '@angular/router';
import { BehaviorSubject, switchMap, tap } from 'rxjs';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
@Component({
  selector: 'app-project',
  providers: [ConfirmationService, MessageService],
  imports: [ButtonModule, AsyncPipe, TableModule, DatePipe, ConfirmDialogModule],
  templateUrl: './project-overview-component.html',
  styleUrls: ['./project-overview-component.scss'],
  standalone: true
})
export class ProjectOverviewComponent implements OnDestroy {

  protected readonly projects$;
  private readonly refreshSubject$ = new BehaviorSubject<void>(undefined);

  constructor(private readonly projectService: ProjectService, private readonly router: Router, private readonly confirmationService: ConfirmationService, private readonly messageService: MessageService) {
    this.projects$ = this.refreshSubject$.pipe(switchMap(() => this.projectService.listProjects()));
  }

  ngOnDestroy() {
    this.refreshSubject$.complete();
  }

  onCreateProject() {
    this.router.navigate(['/projects/create']);
  }

  onDeleteProject(project: Project) {
    this.confirmationService.confirm({
      message: 'Möchten sie das Projekt löschen?',
      header: 'Projekt löschen',
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
        if (project.id) {
          this.projectService.deleteProject({ id: project.id }).pipe(
            tap(() => {
              this.refreshSubject$.next();

            })
          ).subscribe(() => {
            this.messageService.add({
              severity: 'success',
              summary: 'Erfolg',
              detail: 'Projekt wurde gelöscht',
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

  onEditProject(project: Project) {
    console.log("click");
    this.router.navigate(['/projects/edit', project.id]);
  }

  onViewProject(project: Project) {
    console.log("view clicked");
    this.router.navigate(['/projects/view', project.id]);
  }
}
