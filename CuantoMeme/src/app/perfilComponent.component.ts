import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { LoginService } from './services/login.service';
import { UsuarioService } from './services/usuarios.service';
import { VinetasService } from './services/vinetas.service';

import { Usuario } from './classes/Usuario.class';
import { Vineta } from './classes/Vineta.class';

@Component({
  selector: 'perfil-component',
  templateUrl: './templates/perfil.template.html',
  styleUrls: ['./templates/css/perfil.css', './templates/font-awesome/css/font-awesome.css']
})

export class PerfilComponent implements OnInit {
    opcion: string = 'publicadas';
    email: string = 'No disponible';
    username: string = 'John Doe';
    subidas: Vineta[];
    avatar: string = '';
    isAdmin: boolean = false;
    seguidores: Usuario[];
    seguidos: Usuario[];

    constructor(private ServicioLogin: LoginService, private ServicioUsuarios :UsuarioService, private Ruta: ActivatedRoute, private ServicioVinetas: VinetasService, private router: Router) {
      //
    }

    ngOnInit() {
      if (this.ServicioLogin.isLogged && (this.ServicioLogin.user.id === this.Ruta.snapshot.params['id'])) {
            this.router.navigateByUrl('/home');
      } else {
          if(this.ServicioLogin.isLogged) {
            this.isAdmin = this.ServicioLogin.user.isAdmin;
          }
          this.ServicioUsuarios.getUser(this.Ruta.snapshot.params['id']).subscribe(
            response => {
              console.log(response);
              this.username = response.username;
              this.avatar = response.avatarURL;
              this.email = response.email;
              //
            },
            error => console.log(error)
          );
          this.ServicioUsuarios.getUserPublicadas(this.Ruta.snapshot.params['id']).subscribe(
            response => this.subidas = response,
            error => console.log(error)
          );
          this.ServicioUsuarios.getFollowers(this.Ruta.snapshot.params['id']).subscribe(seguidores => {
            this.seguidores = seguidores; 
            console.log(seguidores);
          }, error => console.log(error)
          );
          
          this.ServicioUsuarios.getFollowings(this.Ruta.snapshot.params['id']).subscribe(seguidos => {
            this.seguidos = seguidos; 
            console.log(seguidos);
          }, error => console.log(error)
          );
          
      }

      /*
      this.ServicioUsuarios.getUser(this.Ruta.snapshot.params['id']).subscribe(
        user => {
          console.log(user);
          this.user = user;
          if(this.ServicioLogin.user && this.user.id === this.ServicioLogin.user.id) {
            this.propio = true;
          }
          this.avatar = this.user.getAvatar();
          this.subidas = this.user.getSubidas();
          for(let vineta of this.subidas) {
            vineta.autor = this.user;
          }
          this.email = this.user.getEmail();
          this.username = this.user.getUsername();
        },
        error => console.log(error)
      );*/
    }

    eleccion(opción: string): void {
      this.opcion = opción;
    }

    eliminarUsuario() {
      this.ServicioUsuarios.eliminarUsuario(this.Ruta.snapshot.params['id']);
      this.router.navigateByUrl('/');
    }

    seguir(): void {
      //Llamar a la API con id el this.Ruta.snapshot.params['id']
    }
}