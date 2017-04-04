import { Component } from '@angular/core';

@Component({
  selector: 'component-one',
  template: '<h3>{{title}}</h3>',
  styleUrls: ['./templates/css/app.component.css']
})
export class ComponentOne {
  title = '¡Componente 1!';
}