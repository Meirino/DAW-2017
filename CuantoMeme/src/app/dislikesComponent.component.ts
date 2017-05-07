import { Component } from '@angular/core';
import { Vineta } from './classes/Vineta.class';
import { listaVinetasComponent } from './listaVinetas.component';
import { VinetasService } from './services/vinetas.service';
import { UsuarioService } from './services/usuarios.service';
import { LoginService } from './services/login.service';
import { Usuario } from './classes/Usuario.class';
import { Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'dislikes-component',
  templateUrl: './templates/likes.template.html',
  styleUrls: ['./templates/css/index.css', './templates/font-awesome/css/font-awesome.css']
})

export class DislikesComponent {
  title = 'Viñetas que odias';
  listaVinetas: Vineta[];

  constructor(private servicioVinetas: VinetasService, public serviciologin: LoginService, private router: Router) {}

  ngOnInit() {
    this.listaVinetas = [];
      if(this.serviciologin.isLogged) {
          if(this.serviciologin.user.dislikes){
            this.listaVinetas = this.serviciologin.user.getdislikes();
          }
      } else {
          this.router.navigateByUrl('/login');
      }
  }

}