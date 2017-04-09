import { Router, ActivatedRoute, Params } from '@angular/router';
import { Vineta } from './classes/Vineta.class';
import { Component, Input, OnInit } from '@angular/core';
import 'rxjs/add/operator/switchMap';

const VIÑETAS: Vineta[] = [
    { id: 1, titulo: 'Viñeta 1', descripcion: 'jajajajajajaja me encanta', imgURL: '../../assets/rc1.png', likes: 1, dislikes: 0 },
    { id: 2, titulo: 'Viñeta 2', descripcion: 'jajajajaja', imgURL: '../../assets/rc1.png', likes: 5, dislikes: 10 },
    { id: 3, titulo: 'Viñeta 3', descripcion: 'jaja', imgURL: '../../assets/rc1.png', likes: 6, dislikes: 6 }
];

@Component({
  selector: 'vinetas-detalles',
  templateUrl: './templates/detallesVineta.template.html',
  styleUrls: ['./templates/css/detallesVineta.css', './templates/font-awesome/css/font-awesome.css']
})

export class vinetasDetalleComponent implements OnInit {

  constructor(
  private route: ActivatedRoute,
  private router: Router
) {}

//Consige el id de la viñeta a la que estamos accediendo
ngOnInit() {
  this.route.params.subscribe((params: Params) => this.id = +params['id']);
  this.vineta = VIÑETAS[this.id-1];

  //Llamar a la API para coger la viñeta

}

  id: number;
  vineta: Vineta;

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