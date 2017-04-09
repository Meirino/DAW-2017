import { Vineta } from './classes/Vineta.class';
import { Component, Input, OnInit } from '@angular/core';
import { VinetasService } from './services/vinetas.service';

@Component({
  selector: 'lista-vinetas',
  templateUrl: './templates/listaVinetas.template.html',
  styleUrls: ['./templates/css/listaVinetas.css', './templates/font-awesome/css/font-awesome.css']
})

export class listaVinetasComponent implements OnInit {
  
  //El componente recibe una lista de viñetas y las muestra
  @Input() listaVinetas: Vineta[];

  constructor(private servicioVinetas: VinetasService) {
    //etc
  }

  ngOnInit() {
    //
  }

  like(viñeta: Vineta): void {
    //Añadir la llamada a la API de hacer like
    viñeta.likes += 1;
  }

  dislike(viñeta: Vineta): void {
    //Añadir la llamada a la API de hacer dislike
    viñeta.dislikes += 1;
  }

  fav(viñeta: Vineta): void {
    //Añadir la llamada a la API de hacer favoritas
  }

}