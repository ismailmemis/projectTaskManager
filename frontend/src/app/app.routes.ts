import { Routes } from '@angular/router';
import { ProjectComponent } from '../components/project-component/project-component';
import { TaskComponent } from '../components/task-component/task-component';
import { EmptyComponent } from '../components/empty-component/empty-component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', component: EmptyComponent },  // zeigt nichts, bis ein Menüpunkt gewählt wird
  { path: 'projects', component: ProjectComponent },
  { path: 'tasks', component: TaskComponent },
  { path: '**', redirectTo: '' }
];
