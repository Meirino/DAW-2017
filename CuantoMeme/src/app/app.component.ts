import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './templates/app.component.html',
  styleUrls: ['./templates/css/app.component.css']
})
export class AppComponent {
  title = 'app works!';
  textos: Array<string> = [];
  entrada: string;

  addText(): void {
    this.entrada.replace('\n', '<br/>');
    this.textos.push(this.entrada);
    this.entrada = '';
  }
}