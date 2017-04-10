import { Router, ActivatedRoute, Params } from '@angular/router';
import { Vineta } from './classes/Vineta.class';
import { Component, Input, OnInit } from '@angular/core';
import { VinetasService } from './services/vinetas.service';
import 'rxjs/add/operator/switchMap';

@Component({
  selector: 'vinetas-detalles',
  templateUrl: './templates/detallesVineta.template.html',
  styleUrls: ['./templates/css/detallesVineta.css', './templates/font-awesome/css/font-awesome.css']
})

export class vinetasDetalleComponent implements OnInit {

  constructor(
  private route: ActivatedRoute,
  private router: Router,
  private servicioViñeta: VinetasService
) {}

//Consige el id de la viñeta a la que estamos accediendo
ngOnInit() {
  this.route.params.subscribe((params: Params) => this.id = +params['id']);
  this.servicioViñeta.getVineta(this.id).subscribe(
    resultado=> console.log(resultado),
    error => console.log(error)
  );
}

  public id: number;
  public vineta2: Vineta;

  like(): void {
    //Añadir la llamada a la API de hacer like
    //viñeta.likes += 1;
  }

  dislike(): void {
    //Añadir la llamada a la API de hacer dislike
    //viñeta.dislikes += 1;
  }

  fav(): void {
    //Añadir la llamada a la API de hacer favoritas
  }

  comentar():void {
    //Añadir la llamada a la API para crear un comentario
  }

}