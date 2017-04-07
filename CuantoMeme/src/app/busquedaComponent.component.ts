import { Component } from '@angular/core';

@Component({
  selector: 'busq-component',
  template: '<h3>{{title}}</h3>',
  styleUrls: ['./templates/css/app.component.css']
})
export class BusquedaComponent {
  title = '¡Componente 2!';
}