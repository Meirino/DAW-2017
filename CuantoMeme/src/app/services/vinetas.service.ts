import { Vineta } from '../classes/Vineta.class';
import { Usuario } from '../classes/Usuario.class';
import { Comentario } from '../classes/Comentario.class';
import { Tag } from '../classes/Tag.class';

import { Injectable } from '@angular/core';
import { Http, Response, JsonpModule, RequestOptions, Headers } from '@angular/http';
import { LoginService } from './login.service';
import { Router, ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';


const BASE_URL = 'http://localhost:8080/api/vinetas/'

@Injectable()
export class VinetasService {

    constructor(private http: Http, private router: Router){}
    
    getVinetas(page:number){
        var url = BASE_URL+"?page="+page;
        console.log(url)
        return this.http.get(url, { withCredentials: true })
        .map(response => this.generateVinetas(response.json()))
        .catch(error => this.handleError(error))
    }

    likes(){
        return this.http.get(BASE_URL+'/likes', { withCredentials: true })
        .map(response => this.generateVinetas(response.json()))
        .catch(error => this.handleError(error))
    }

    dislikes(){
        return this.http.get(BASE_URL+'/dislikes', { withCredentials: true })
        .map(response => this.generateVinetas(response.json()))
        .catch(error => this.handleError(error))
    }

    favorites(){
        return this.http.get(BASE_URL+'/favorites', { withCredentials: true })
        .map(response => this.generateVinetas(response.json()))
        .catch(error => this.handleError(error))
    }

    uploaded(){
        return this.http.get(BASE_URL+'/uploaded', { withCredentials: true })
        .map(response => this.generateVinetas(response.json()))
        .catch(error => this.handleError(error))
    }

    eliminarViñeta(id: number) {
        //Hacer petición API
        this.http.delete(BASE_URL+id, { withCredentials: true }).subscribe(response => console.log(response), error => console.log(error));
    }

    likeVineta(id: number) {
    const body = JSON.stringify("");
    const headers = new Headers({
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    });

    var url = BASE_URL+"likes/"+id;

    console.log(url);

    const options = new RequestOptions({ withCredentials: true, headers });
    return this.http.put(url, null, options)
                .map(response => this.generateVinetas(response.json()))
                .catch(error => error);
    }

    dislikeVineta(id: number) {
    const body = JSON.stringify("");
    const headers = new Headers({
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    });
    var url = BASE_URL+"dislikes/"+id;
    console.log(url)
    const options = new RequestOptions({ withCredentials: true, headers });
            return this.http.put(url, null, options)
                .map(response => this.generateVinetas(response.json()))
                .catch(error => error);
    }

    favoriteVineta(id: number) {
    const body = JSON.stringify("");
    const headers = new Headers({
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    });
    var url = BASE_URL+"favorite/"+id;
    console.log(url)
    const options = new RequestOptions({ withCredentials: true, headers });
            return this.http.put(url, null, options)
                .map(response => this.generateVinetas(response.json()))
                .catch(error => error);
    }

    getVinetasTag(tag: string) {
        return this.http.get(BASE_URL+"/busq/"+tag+"?filtro=tag", { withCredentials: true })
        .map(response => this.generateVinetas(response.json()))
        .catch(error => this.handleError(error))
    }

    busqVinetas(texto: string, modo:string) {
        return this.http.get(BASE_URL+"/busq/"+texto+"?filtro="+modo, { withCredentials: true })
        .map(response => this.generateVinetas(response.json()))
        .catch(error => this.handleError(error))
    }

    publicarVineta(formulario: FormData): void {
        //Llamar a la API
        let headers = new Headers();
        this.http.post(BASE_URL, formulario, { withCredentials: true }).subscribe(data => this.router.navigateByUrl('/vineta/' + data.json().id), error => console.log(error));
    }
    
    getVineta(id: number){
        return this.http.get(BASE_URL+id, { withCredentials: true }).map(
            response => this.generateVinetaWithComents(response.json())//this.extractVinetas(response)
        )
        .catch(error => this.handleError(error))
    }
    
    generateVinetas(vinetas: any[]){
      var lv: Vineta[] = [];
      for (let vineta of vinetas) {
          lv.push(this.generateVineta(vineta));
         }
      return lv;
    }

    generateVineta(vineta: any){
        var tag : Tag = this.generateTag(vineta.tags);
        var v: Vineta = new Vineta(vineta.id, vineta.titulo, vineta.descripcion, vineta.URL, vineta.likes, vineta.dislikes,tag);
        if (vineta.autor){
        var autor : Usuario = this.generateAutor(vineta.autor)
        v.setAutor(autor);
        }
        return v
    }
    generateVinetaWithComents(vineta: any){
        console.log("llego a generar vineta with commetn"+vineta)
        var v : Vineta = this.generateVineta(vineta);
        var comentarios : Comentario[] = [];
        for(let comentario of vineta.comentarios){
            comentarios.push(this.generateComentario(comentario));
        }
        v.setComentarios(comentarios);
        return v
        //return new Vineta(vineta.id, vineta.titulo, vineta.descripcion, vineta.URL, vineta.likes, vineta.dislikes, autor);
    }
    generateAutor(autor: any){
        return new Usuario(autor.id, autor.username, autor.AvatarURL, autor.email);
    }
    generateComentario(comentario: any){
        var autor : Usuario = this.generateAutor(comentario.autor_comentario);
        return new Comentario(comentario.id, comentario.fecha, comentario.comentario, autor);
    }
    
    generateTag(tag: any){
        return new Tag(tag.id, tag.nombre);
    }
    private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text());
	}
}