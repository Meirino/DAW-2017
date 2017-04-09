import { Component } from '@angular/core';
import { Vineta } from './classes/Vineta.class';
import { listaVinetasComponent } from './listaVinetas.component';

const VIÑETAS: Vineta[] = [
    { id: 1, titulo: 'Viñeta 1', descripcion: 'jajajajajajaja', imgURL: '../../assets/rc1.png', likes: 1, dislikes: 0 },
    { id: 2, titulo: 'Viñeta 2', descripcion: 'jajajajaja', imgURL: '../../assets/rc1.png', likes: 5, dislikes: 10 },
    { id: 3, titulo: 'Viñeta 3', descripcion: 'jaja', imgURL: '../../assets/rc1.png', likes: 6, dislikes: 6 }
];

@Component({
  selector: 'index-component',
  templateUrl: './templates/indexComponent.template.html',
  styleUrls: ['./templates/css/index.css']
})

export class IndexComponent {
  title = '¡Bienvenido a CuantoMeme!';
  listaVinetas = [];
}