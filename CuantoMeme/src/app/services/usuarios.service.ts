import { Vineta } from '../classes/Vineta.class';
import { Usuario } from '../classes/Usuario.class';
import { Comentario } from '../classes/Comentario.class';
import { Tag } from '../classes/Tag.class';
import {VinetasService} from './vinetas.service'
import { Injectable } from '@angular/core';
import { Http, Response, JsonpModule, RequestOptions, Headers } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
const BASE_URL = 'http://localhost:8080/api/usuarios/'

@Injectable()
export class UsuarioService {
    constructor(private http: Http, private serviciovineta: VinetasService){}
    
    getUsers(){
        return this.http.get(BASE_URL, { withCredentials: true }).map(
            response => this.generateUsers(response.json())//this.extractVinetas(response)
        )
    }

    getFollowers(id:number){
            return this.http.get(BASE_URL+id+'/followers', { withCredentials: true }).map(
            response => this.generateUsers(response.json())//this.extractVinetas(response)
        )
    }
    getFollowings(id:number){
            return this.http.get(BASE_URL+id+'/followings', { withCredentials: true }).map(
            response => this.generateUsers(response.json())//this.extractVinetas(response)
        )
    }

    followUser(id:number){
        const body = JSON.stringify("");
        const headers = new Headers({
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        });
        var url = BASE_URL+"follow/"+id;
        console.log(url)
        const options = new RequestOptions({ withCredentials: true, headers });
            return this.http.put(url, null, options)
                .map(response => this.generateUsers(response.json()))
                .catch(error => error);
    }
    unfollowUser(id:number){
        const body = JSON.stringify("");
        const headers = new Headers({
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        });
        var url = BASE_URL+"unfollow/"+id;
        console.log(url)
        const options = new RequestOptions({ withCredentials: true, headers });
            return this.http.put(url, null, options)
                .map(response => this.generateUsers(response.json()))
                .catch(error => error);
    }
    generateUsers(users: any[]){
      var lu: Usuario[] = [];
      for (let user of users) {
          lu.push(this.generateUser(user));
         }
      return lu;
    }
    generateUser(user: any){
        return new Usuario(user.id, user.username, user.AvatarURL, user.email);
    }

   getUser(id){
        return this.http.get(BASE_URL+id, { withCredentials: true }).map(
            response => this.generateFullUser(response.json()),
            error => console.error(error)
        )
    }
    generateFullUser(user: any){
        var usuario : Usuario = new Usuario(user.id, user.username, user.AvatarURL, user.email);
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
        return this.http.get(BASE_URL+id+'/likes', { withCredentials: true }).map(
            response => this.serviciovineta.generateVinetas(response.json()),
            error => console.log(error)
        );
    }

    getUserDislikes (id: number): Observable<Vineta[]> {
        return this.http.get(BASE_URL+id+'/dislikes', { withCredentials: true }).map(
            response => this.serviciovineta.generateVinetas(response.json()),
            error => console.log(error)
        );
    }

    getUserFavoritas (id: number): Observable<Vineta[]> {
        return this.http.get(BASE_URL+id+'/favorites', { withCredentials: true }).map(
            response => this.serviciovineta.generateVinetas(response.json()),
            error => console.log(error)
        );
    }

    getUserPublicadas (id: number): Observable<Vineta[]> {
        return this.http.get(BASE_URL+id+'/publicadas', { withCredentials: true }).map(
            response => this.serviciovineta.generateVinetas(response.json()),
            error => console.log(error)
        );
    }
    getUserTimeline (id: number): Observable<Vineta[]> {
        return this.http.get(BASE_URL+id+'/timeline', { withCredentials: true }).map(
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
        this.http.delete(BASE_URL+id, { withCredentials: true }).subscribe(response => console.log(response), error => console.log(error));
    }

    getTimeline(): Observable<Vineta[]> {
        return this.http.get(BASE_URL+'timeline', { withCredentials: true }).map(response => this.serviciovineta.generateVinetas(response.json()), error => console.log(error));
    }


}