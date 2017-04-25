import { Router, ActivatedRoute} from '@angular/router';
import { Vineta } from './classes/Vineta.class';
import { Component, Input, OnInit } from '@angular/core';
import { VinetasService } from './services/vinetas.service';
import { ComentariosService } from './services/comentario.service';
import { LoginService } from './services/login.service';

import 'rxjs/add/operator/switchMap';

@Component({
  selector: 'vinetas-detalles',
  templateUrl: './templates/detallesVineta.template.html',
  styleUrls: ['./templates/css/detallesVineta.css', './templates/font-awesome/css/font-awesome.css']
})

export class vinetasDetalleComponent implements OnInit {
  private vineta: Vineta;
  constructor(
  private login: LoginService,
  private route: ActivatedRoute,
  private router: Router,
  private servicioVinetas: VinetasService,
  private serviciocomentarios: ComentariosService

) {
}

//Consige el id de la viñeta a la que estamos accediendo
ngOnInit() {
      console.log("llego2 con el id"+this.route.snapshot.params['id'] );

  //this.route.params.subscribe((params: Params) => this.id = +params['id']);
  this.servicioVinetas.getVineta(this.route.snapshot.params['id']).subscribe(
    vineta=> this.vineta = vineta,
    error => console.log(error)
  );
}


    like(id: number): void {
    if(this.login.isLogged === false) {
      this.router.navigateByUrl("/login");
    } else {
      //llamar a la API
      this.servicioVinetas.likeVineta(id).subscribe(
        likes => {
        this.login.user.setLikes([]);
        for (var i = 0; i < likes["length"]; i++) { 
          this.login.user.likes.push(likes[i]);
        }
        console.log(this.login.user)
        this.vineta.likes = this.vineta.likes+1;
    },///console.log(likes),this.login.user.setLikes(likes.instanceof()),
        error => console.log(error)
      ); 
    }
  }

  dislike(id: number): void {
    if(this.login.isLogged === false) {
      this.router.navigateByUrl("/login");
    } else {
      //llamar a la API
      this.servicioVinetas.dislikeVineta(id).subscribe(
        dislikes => {
        this.login.user.setDislikes([]);
        for (var i = 0; i < dislikes["length"]; i++) { 
          this.login.user.dislikes.push(dislikes[i]);
        }
        console.log(this.login.user)
        this.vineta.dislikes = this.vineta.dislikes+1;
        },
        error => console.log(error)
      );
    }
  }
  favorite(id: number): void {
    if(this.login.isLogged === false) {
      this.router.navigateByUrl("/login");
    } else {
      //llamar a la API
      this.servicioVinetas.favoriteVineta(id).subscribe(
        favorites => {
        this.login.user.setFav([]);
        for (var i = 0; i < favorites["length"]; i++) { 
          this.login.user.favoritos.push(favorites[i]);
        }
        console.log(this.login.user)
        },
        error => console.log(error)
      );
    }
  }

  comentar(comentario: string):void {
    var id = this.vineta.id
     if(this.login.isLogged === false) {
      this.router.navigateByUrl("/login");
    } else {
      //llamar a la API
      this.serviciocomentarios.comentarVineta(this.vineta.id, comentario).subscribe(
        vineta => {this.vineta = <Vineta>vineta,
        console.log("-------")
        console.log(this.vineta)
        console.log("-------0")
        console.log(vineta[0])
        console.log("-------1")
        console.log(vineta[1])
        console.log("-------2")
        console.log(vineta[2])
        let link:any[] = ['/vineta', id];
        this.router.navigate(link)
    },
    error => console.log(error)
      );
    }

}
}