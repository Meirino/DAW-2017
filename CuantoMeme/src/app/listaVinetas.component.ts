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
        response => console.log(response),
        error => console.log(error)
      );
      this.login.user.addLike(viñeta);
      viñeta.likes = viñeta.likes + 1;
    }
  }

  dislike(viñeta: Vineta): void {
    if(this.login.isLogged === false) {
      this.router.navigateByUrl("/login");
    } else {
      //llamar a la API
      this.login.user.addLike(viñeta);
      viñeta.dislikes = viñeta.likes + 1;
    }
  }

}