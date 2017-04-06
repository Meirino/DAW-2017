import { Component } from '@angular/core';

@Component({
  selector: 'component-two',
  template: '<h3>{{title}}</h3>',
  styleUrls: ['./templates/css/app.component.css']
})
export class BusquedaComponent {
  title = '¡Componente 2!';
}