import { Vineta } from './classes/Vineta.class';
import { Component, Input, OnInit } from '@angular/core';
import { LoginService } from './services/login.service';
import { Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'lista-vinetas',
  templateUrl: './templates/listaVinetas.template.html',
  styleUrls: ['./templates/css/listaVinetas.css', './templates/font-awesome/css/font-awesome.css']
})

export class listaVinetasComponent {
  
  //El componente recibe una lista de viñetas y las muestra
  @Input() listaVinetas: Vineta[];

  constructor(private login: LoginService, private router: Router) {
    //etc
  }

  like(viñeta: Vineta): void {
    if(this.login.isLogged === false) {
      this.router.navigateByUrl("/login");
    } else {
      //llamar a la API
      viñeta.likes = viñeta.likes + 1;
    }
  }

  dislike(viñeta: Vineta): void {
    if(this.login.isLogged === false) {
      this.router.navigateByUrl("/login");
    } else {
      //llamar a la API
      viñeta.dislikes = viñeta.likes + 1;
    }
  }

}