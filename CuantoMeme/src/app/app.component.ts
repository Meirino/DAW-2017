import { Component, trigger, state, style, transition, animate } from '@angular/core';
import { sideMenuComponent } from './side-menu.component';

@Component({
  selector: 'app-root',
  templateUrl: './templates/app.component.html',
  styleUrls: ['./templates/css/app.component.css', './templates/font-awesome/css/font-awesome.css'],
  animations: [
    trigger('slideInOut', [
      state('in', style({
        transform: 'translate3d(0, 0, 0)'
      })),
      state('out', style({
        transform: 'translate3d(100%, 0, 0)'
      })),
      transition('in => out', animate('400ms ease-in-out')),
      transition('out => in', animate('400ms ease-in-out'))
    ]),
  ]
})
export class AppComponent {
  title = 'app works!';
  textos: Array<string> = [];
  entrada: string;
  toggled: string = 'out';

  addText(): void {
    this.entrada.replace('\n', '<br/>');
    this.textos.push(this.entrada);
    this.entrada = '';
  }

  toggleMenu(): void {
    this.toggled = this.toggled === 'out' ? 'in' : 'out';
  }
}