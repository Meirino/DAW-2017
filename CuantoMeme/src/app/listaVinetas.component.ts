import { Vineta } from './classes/Vineta.class';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'lista-vinetaa',
  templateUrl: './templates/listaVinetas.template.html',
  styleUrls: []
})

export class listaVinetasComponent {
  
  //El componente recibe una lista de viñetas y las muestra
  @Input('lista') listaVinetas: Vineta[];
}