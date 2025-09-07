import { Routes } from '@angular/router';
import { Project } from '../components/project/project.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'home', component: Project },
];
