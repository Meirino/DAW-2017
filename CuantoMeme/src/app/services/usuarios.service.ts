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
const BASE_URL = 'http://localhost:8080/api/usuarios/'

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
        var usuario : Usuario = new Usuario(user.id, user.username, user.AvatarURL);
        return usuario;
    }

    actualizarAvatar(formulario: FormData): Observable<boolean> {
        //Llamada a la API
        return this.http.put(BASE_URL+'avatar', formulario, { withCredentials: true }).map(data => true, error => console.log(error));
    }

    actualizarDatos(formulario: FormData) {
        //Llamar a la API
        //Actualizar datos en local
    }

    getUserLikes (id: number): Observable<Vineta[]> {
        return this.http.get(BASE_URL+id+'/likes').map(
            response => this.serviciovineta.generateVinetas(response.json()),
            error => console.log(error)
        );
    }

    getUserDislikes (id: number): Observable<Vineta[]> {
        return this.http.get(BASE_URL+id+'/dislikes').map(
            response => this.serviciovineta.generateVinetas(response.json()),
            error => console.log(error)
        );
    }

    getUserFavoritas (id: number): Observable<Vineta[]> {
        return this.http.get(BASE_URL+id+'/favorites').map(
            response => this.serviciovineta.generateVinetas(response.json()),
            error => console.log(error)
        );
    }

    getUserPublicadas (id: number): Observable<Vineta[]> {
        return this.http.get(BASE_URL+id+'/publicadas').map(
            response => this.serviciovineta.generateVinetas(response.json()),
            error => console.log(error)
        );
    }

    recuperarAvatar(): string {
        let url: string;
        this.http.get(BASE_URL+'avatar', { withCredentials: true }).subscribe(
            response => url = response.json(),
            error => console.log(error)
        );
        return url;
    }

    eliminarUsuario(id: number) {
        //Hacer petición API
    }


}