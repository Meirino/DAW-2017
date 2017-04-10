import { Component } from '@angular/core';
import { Vineta } from './classes/Vineta.class';
import { listaVinetasComponent } from './listaVinetas.component';

@Component({
  selector: 'index-component',
  templateUrl: './templates/indexComponent.template.html',
  styleUrls: ['./templates/css/index.css']
})

export class IndexComponent {
  title = '¡Bienvenido a CuantoMeme!';
  listaVinetas = [];
}