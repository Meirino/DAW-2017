import { Vineta } from './classes/Vineta.class';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'lista-vinetaa',
  templateUrl: './templates/listaVinetas.template.html',
  styleUrls: []
})

export class vinetasDetalleComponent {
  
  //El mostrará los detalles de la viñeta
  vineta: Vineta;

}