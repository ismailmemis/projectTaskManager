import { Routes } from '@angular/router';
import { Home } from '../components/home/home';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'home', component: Home },
];
