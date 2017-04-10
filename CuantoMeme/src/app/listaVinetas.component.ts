import { Vineta } from './classes/Vineta.class';
import { Component, Input, OnInit } from '@angular/core';
import { VinetasService } from './services/vinetas.service';

@Component({
  selector: 'lista-vinetas',
  templateUrl: './templates/listaVinetas.template.html',
  styleUrls: ['./templates/css/listaVinetas.css', './templates/font-awesome/css/font-awesome.css']
})

export class listaVinetasComponent {
  
  //El componente recibe una lista de viñetas y las muestra
  @Input() listaVinetas: Vineta[];

  constructor() {
    //
  }

  like(viñeta: Vineta): void {
    viñeta.likes = viñeta.likes + 1;
  }

  dislike(viñeta: Vineta): void {
    viñeta.dislikes = viñeta.dislikes + 1;
  }

}