import { Component } from '@angular/core';

@Component({
  selector: 'index-component',
  template: '<h3>{{title}}</h3>',
  styleUrls: ['./templates/css/app.component.css']
})
export class IndexComponent {
  title = '¡Bienvenido a CuantoMeme!';
}