import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { LoginService } from './services/login.service';
import { UsuarioService } from './services/usuarios.service';

import { Usuario } from './classes/Usuario.class';
import { Vineta } from './classes/Vineta.class';

@Component({
  selector: 'perfil-component',
  templateUrl: './templates/perfil.template.html',
  styleUrls: ['./templates/css/perfil.css', './templates/font-awesome/css/font-awesome.css']
})

export class PerfilComponent implements OnInit {
    user: Usuario;
    opcion: string = 'publicadas';
    email: string = 'No disponible';
    username: string;
    subidas: Vineta[];
    avatar: string;
    propio: boolean = false;

    //Subir viñeta
    tituloVineta: string = '';
    descVineta: string = '';
    tagVineta: string = '';
    imgVineta: FileList;

    //Cambiar avatar

    //CambiarUsuario

    constructor(private ServicioLogin: LoginService, private ServicioUsuarios :UsuarioService, private Ruta: ActivatedRoute) {
      //
    }

    ngOnInit() {
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
      );
    }

    eleccion(opción: string): void {
      this.opcion = opción;
    }

    fileChange(e) {
      this.imgVineta = e.target.files;
    }

    subirVineta() {}
}