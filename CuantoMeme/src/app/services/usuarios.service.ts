import { Vineta } from '../classes/Vineta.class';
import { Usuario } from '../classes/Usuario.class';
import { Comentario } from '../classes/Comentario.class';
import { Tag } from '../classes/Tag.class';

import { Injectable } from '@angular/core';
import { Http, Response, JsonpModule , Headers} from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
const BASE_URL = 'http://127.0.0.1:8080/api/usuarios/'

@Injectable()
export class UsuarioService {
    constructor(private http: Http){}
    
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

}