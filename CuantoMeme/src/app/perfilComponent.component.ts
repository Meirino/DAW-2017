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
    followinguser = false;
    subidas: Vineta[];
    isAdmin: boolean = false;
    usuario : Usuario;

    constructor(private ServicioLogin: LoginService, private ServicioUsuarios :UsuarioService, private Ruta: ActivatedRoute, private ServicioVinetas: VinetasService, private router: Router) {
      //
    }

    ngOnInit() {
      if (this.ServicioLogin.isLogged){
        this.isAdmin = this.ServicioLogin.user.isAdmin;
          if (this.ServicioLogin.user.id === this.Ruta.snapshot.params['id']) {
            this.router.navigateByUrl('/home');}
      } 
      this.ServicioUsuarios.getUser(this.Ruta.snapshot.params['id']).subscribe(
            response => { 
              this.usuario = response;
              if (this.ServicioLogin.isLogged){
                this.followinguser = this.ServicioLogin.user.isFollowed(this.usuario.id);
              }
              //
            },
            error => console.log(error)
          );
      this.ServicioUsuarios.getUserPublicadas(this.Ruta.snapshot.params['id']).subscribe(
            response => this.usuario.setSubidas(response),
            error => console.log(error)
          );
      this.ServicioUsuarios.getFollowers(this.Ruta.snapshot.params['id']).subscribe(seguidores => {
            this.usuario.setFollowers(seguidores); 
          }, error => console.log(error)
          );
          
      this.ServicioUsuarios.getFollowings(this.Ruta.snapshot.params['id']).subscribe(seguidos => {
            this.usuario.setFollowings(seguidos);
          }, error => console.log(error)
          );
          
      

    }

    eleccion(opción: string): void {
      this.opcion = opción;
    }

    eliminarUsuario() {
      this.ServicioUsuarios.eliminarUsuario(this.Ruta.snapshot.params['id']);
      this.router.navigateByUrl('/');
    }

    followuser(){
      if(this.ServicioLogin.isLogged === false) {
        this.router.navigateByUrl("/login");
      }else {
        this.ServicioUsuarios.followUser(this.usuario.id).subscribe(
        seguidos=>{
              this.ServicioLogin.user.setFollowings([]);
              for (var i = 0; i < seguidos["length"]; i++) { 
                this.ServicioLogin.user.seguidos.push(seguidos[i]);
            }
            this.followinguser = this.ServicioLogin.user.isFollowed(this.usuario.id);
            let link:any[] = ['/perfil', this.usuario.id];
            this.router.navigate(link)
    },
    error => console.error(error)
      )
    }
    }
    unfollowuser(){
      if(this.ServicioLogin.isLogged === false) {
        this.router.navigateByUrl("/login");
      }else {
        this.ServicioUsuarios.unfollowUser(this.usuario.id).subscribe(
        seguidos=>{
              
              this.ServicioLogin.user.setFollowings([]);
              if(typeof seguidos !== "undefined"){
                console.log("vamos a ver los seguidos")
                console.log(seguidos)
                for (var i = 0; i < seguidos["length"]; i++) { 
                  this.ServicioLogin.user.seguidos.push(seguidos[i]);
                }
              }else{
                console.log("no considero que haya nada")
              }
            this.followinguser = this.ServicioLogin.user.isFollowed(this.usuario.id);
            let link:any[] = ['/perfil', this.usuario.id];
            this.router.navigate(link)
    },
    error => console.error(error)
      )
    }
    }
}