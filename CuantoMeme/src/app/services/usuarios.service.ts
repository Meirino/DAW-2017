import { Vineta } from '../classes/Vineta.class';
import { Usuario } from '../classes/Usuario.class';
import { Comentario } from '../classes/Comentario.class';
import { Tag } from '../classes/Tag.class';
import {VinetasService} from './vinetas.service'
import { Injectable } from '@angular/core';
import { Http, Response, JsonpModule , Headers} from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
const BASE_URL = 'http://127.0.0.1:8080/api/usuarios/'

@Injectable()
export class UsuarioService {
    constructor(private http: Http, private serviciovineta: VinetasService){}
    
    getUsers(){
        return this.http.get(BASE_URL).map(
            response => this.generateUsers(response.json())//this.extractVinetas(response)
        )
    }
    generateUsers(users: any[]){
      var lu: Usuario[] = [];
      for (let user of users) {
          lu.push(this.generateUser(user));
         }
      return lu;
    }
    generateUser(user: any){
        return new Usuario(user.id, user.username, user.AvatarURL);
    }

   getUser(id){
        return this.http.get(BASE_URL+id, { withCredentials: true }).map(
            response => this.generateFullUser(response.json()),
            error => console.error(error)
        )
    }
    generateFullUser(user: any){
        var usuario : Usuario = new Usuario(user.id, user.username, user.AvatarURL)
        usuario.setSubidas(this.serviciovineta.generateVinetas(user.vinetas_subidas))
        usuario.setDislikes(this.serviciovineta.generateVinetas(user.vinetas_odiadas))
        usuario.setLikes(this.serviciovineta.generateVinetas(user.vinetas_gustadas))
        usuario.setFav(this.serviciovineta.generateVinetas(user.vinetas_favoritas))
        return usuario
    }

    actualizarAvatar(formulario: FormData) {
        //Llamada a la API
        this.http.put(BASE_URL+'avatar', formulario, { withCredentials: true }).subscribe(data => console.log(data), error => console.log(error));
        //Actualizar Avatar en local
    }

    actualizarDatos(formulario: FormData) {
        //Llamar a la API
        //Actualizar datos en local
    }


}