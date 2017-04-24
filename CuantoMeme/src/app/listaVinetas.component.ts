import { Vineta } from './classes/Vineta.class';
import { Component, Input, OnInit } from '@angular/core';
import { LoginService } from './services/login.service';
import { Router, ActivatedRoute} from '@angular/router';
import { VinetasService } from './services/vinetas.service';

@Component({
  selector: 'lista-vinetas',
  templateUrl: './templates/listaVinetas.template.html',
  styleUrls: ['./templates/css/listaVinetas.css', './templates/font-awesome/css/font-awesome.css']
})

export class listaVinetasComponent {
  
  //El componente recibe una lista de viñetas y las muestra
  @Input() listaVinetas: Vineta[];

  constructor(private login: LoginService, private router: Router, private servicioVinetas: VinetasService) {
    //etc
  }

  like(viñeta: Vineta): void {
    if(this.login.isLogged === false) {
      this.router.navigateByUrl("/login");
    } else {
      //llamar a la API
      this.servicioVinetas.likeVineta(viñeta.id).subscribe(
        likes => {
        this.login.user.setLikes([]);
        for (var i = 0; i < likes["length"]; i++) { 
          this.login.user.likes.push(likes[i]);
        }
        console.log(this.login.user)
        viñeta.likes = viñeta.likes+1;
    },///console.log(likes),this.login.user.setLikes(likes.instanceof()),
        error => console.log(error)
      ); 
    }
  }

  dislike(viñeta: Vineta): void {
    if(this.login.isLogged === false) {
      this.router.navigateByUrl("/login");
    } else {
      //llamar a la API
      this.servicioVinetas.dislikeVineta(viñeta.id).subscribe(
        dislikes => {
        this.login.user.setDislikes([]);
        for (var i = 0; i < dislikes["length"]; i++) { 
          this.login.user.dislikes.push(dislikes[i]);
        }
        console.log(this.login.user)
        viñeta.dislikes = viñeta.dislikes+1;
        },
        error => console.log(error)
      );
    }
  }
  favorite(viñeta: Vineta): void {
    if(this.login.isLogged === false) {
      this.router.navigateByUrl("/login");
    } else {
      //llamar a la API
      this.servicioVinetas.favoriteVineta(viñeta.id).subscribe(
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

}