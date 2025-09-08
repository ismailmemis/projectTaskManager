import { Routes } from '@angular/router';
import { ProjectOverviewComponent } from '../components/project-overview-component/project-overview-component';
import { TaskOverviewComponent } from '../components/task-overview-component/task-overview-component';
import { EmptyComponent } from '../components/empty-component/empty-component';
import { CreateProjectComponent } from '../components/create-project/create-project-component';
import { EditProjectComponent } from '../components/edit-project-component/edit-project-component';
import { ViewProjectComponent } from '../components/view-project/view-project.component';
import { ViewTaskComponent } from '../components/view-task-component/view-task.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', component: EmptyComponent },  // zeigt nichts, bis ein Menüpunkt gewählt wird
  { path: 'projects', component: ProjectOverviewComponent },
  { path: 'projects/create', component: CreateProjectComponent },
  { path: 'projects/edit/:id', component: EditProjectComponent },
  { path: 'projects/view/:id', component: ViewProjectComponent },
  { path: 'tasks', component: TaskOverviewComponent },
  {path: 'tasks/view/:id', component: ViewTaskComponent},
  { path: '**', redirectTo: '' }
];
