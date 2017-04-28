import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { LoginService } from './services/login.service';
import { UsuarioService } from './services/usuarios.service';
import { VinetasService } from './services/vinetas.service';

import { Usuario } from './classes/Usuario.class';
import { Vineta } from './classes/Vineta.class';

@Component({
  selector: 'home-component',
  templateUrl: './templates/home.template.html',
  styleUrls: ['./templates/css/perfil.css', './templates/font-awesome/css/font-awesome.css']
})

export class HomeComponent implements OnInit {
    opcion: string = 'publicadas';
    email: string = 'cuantomeme@cuantomeme.com';
    subidas: Vineta[] = [];
    propio: boolean = false;

    //Subir viñeta
    tituloVineta: string = '';
    descVineta: string = '';
    tagVineta: string = '';
    imgVineta: FileList;

    //Cambiar avatar
    imgAvatar: FileList;

    //CambiarUsuario
    usernameModified: string;
    emailModified: string;

    constructor(private ServicioLogin: LoginService, private ServicioUsuarios :UsuarioService, private Ruta: ActivatedRoute, private ServicioVinetas: VinetasService, private router: Router) {
      //
    }

    ngOnInit() {
      if(!this.ServicioLogin.isLogged) {
        this.router.navigateByUrl('/');
      } else {
        this.ServicioUsuarios.getUserPublicadas(this.ServicioLogin.user.id).subscribe(
            response => this.ServicioLogin.user.setSubidas(response),
            error => console.log(error)
        );
        console.log(this.ServicioLogin.user.getSubidas());
      }
    }

    eleccion(opción: string): void {
      this.opcion = opción;
    }

    fileChange(e) {
      this.imgVineta = e.target.files;
    }

    avatarChange(e) {
      this.imgAvatar = e.target.files;
    }

    subirVineta() {
      if(this.imgVineta.length > 0) {
        let file: File = this.imgVineta[0];
        let formData: FormData = new FormData();

        formData.append('file', file);
        formData.append('titulo', this.tituloVineta);
        formData.append('desc', this.descVineta);
        formData.append('tags', this.tagVineta);

        this.ServicioVinetas.publicarVineta(formData);
      }
    }

    cambiarAvatar() {
      if(this.imgAvatar.length > 0) {
        let file: File = this.imgAvatar[0];
        let formData: FormData = new FormData();

        formData.append('file', file);
        console.log('Nombre del avatar: ' + this.imgAvatar[0].name);

        this.ServicioUsuarios.actualizarAvatar(formData).subscribe(
          data => {
            this.ServicioLogin.user.avatarURL = 'http://localhost:8080/imgs/' + this.imgAvatar[0].name;
            for(let vineta of this.ServicioLogin.user.getSubidas()) {
              vineta.autor.avatarURL = this.ServicioLogin.user.avatarURL;
            }
          },
          error => console.log(error)
        );
      }
    }

    cambiarUsuario() {
        let formData: FormData = new FormData();

        formData.append('nombre', this.usernameModified);
        formData.append('email', this.emailModified);

        this.ServicioUsuarios.actualizarDatos(formData);
    }
}