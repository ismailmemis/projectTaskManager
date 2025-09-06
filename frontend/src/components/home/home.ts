import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';


@Component({
  selector: 'app-home',
  imports: [ButtonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
  standalone: true
})
export class Home {

}
