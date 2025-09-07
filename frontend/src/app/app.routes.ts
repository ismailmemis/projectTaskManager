import { Routes } from '@angular/router';
import { ProjectOverviewComponent } from '../components/project-overview-component/project-overview-component';
import { TaskComponent } from '../components/task-component/task-component';
import { EmptyComponent } from '../components/empty-component/empty-component';
import { CreateProject } from '../components/create-project/create-project';

export const routes: Routes = [
  { path: '', pathMatch: 'full', component: EmptyComponent },  // zeigt nichts, bis ein Menüpunkt gewählt wird
  { path: 'projects', component: ProjectOverviewComponent },
  { path: 'projects/create', component: CreateProject },
  { path: 'tasks', component: TaskComponent },
  { path: '**', redirectTo: '' }
];
