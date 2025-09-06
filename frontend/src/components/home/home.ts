import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';


@Component({
  selector: 'app-home',
  imports: [ButtonModule],
  templateUrl: './home.html',
  styleUrls: ['./home.scss'],
  standalone: true
})
export class Home {

  constructor(){}

  onCheckButtonClick() {
    console.log("check clicked"); 
  }

}
