import { Router, ActivatedRoute, Params } from '@angular/router';
import { Vineta } from './classes/Vineta.class';
import { Component, Input, OnInit } from '@angular/core';
import 'rxjs/add/operator/switchMap';

@Component({
  selector: 'lista-vinetaa',
  templateUrl: './templates/detallesVineta.template.html',
  styleUrls: []
})

export class vinetasDetalleComponent implements OnInit {

  constructor(
  private route: ActivatedRoute,
  private router: Router
) {}

//Consige el id de la viñeta a la que estamos accediendo
ngOnInit() {
  this.route.params.subscribe((params: Params) => this.id = +params['id']);

  //Llamar a la API para coger la viñeta

}

  vineta: Vineta;
  id: number;

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

  comentar():void {
    //Añadir la llamada a la API para crear un comentario
  }

}