import { Router, ActivatedRoute} from '@angular/router';
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
  private vineta: Vineta;
  constructor(
  private route: ActivatedRoute,
  private router: Router,
  private servicioViñeta: VinetasService
) {    console.log("llego1");
}

//Consige el id de la viñeta a la que estamos accediendo
ngOnInit() {
      console.log("llego2 con el id"+this.route.snapshot.params['id'] );

  //this.route.params.subscribe((params: Params) => this.id = +params['id']);
  this.servicioViñeta.getVineta(this.route.snapshot.params['id']).subscribe(
    vineta=> this.vineta = vineta,
    error => console.log(error)
  );
}


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