import { Routes } from '@angular/router';
import { ProjectOverviewComponent } from '../components/project-overview-component/project-overview-component';
import { TaskComponent } from '../components/task-component/task-component';
import { EmptyComponent } from '../components/empty-component/empty-component';
import { CreateProjectComponent } from '../components/create-project/create-project-component';
import { EditProjectComponent } from '../components/edit-project-component/edit-project-component';
import { ViewProjectComponent } from '../components/view-project/view-project.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', component: EmptyComponent },  // zeigt nichts, bis ein Menüpunkt gewählt wird
  { path: 'projects', component: ProjectOverviewComponent },
  { path: 'projects/create', component: CreateProjectComponent },
  { path: 'projects/edit/:id', component: EditProjectComponent },
   { path: 'projects/view/:id', component: ViewProjectComponent },
  { path: 'tasks', component: TaskComponent },
  { path: '**', redirectTo: '' }
];
