import { Vineta } from '../classes/Vineta.class';
import { Usuario } from '../classes/Usuario.class';
import { Comentario } from '../classes/Comentario.class';
import { Tag } from '../classes/Tag.class';

import { Injectable } from '@angular/core';
import { Http, Response, JsonpModule } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';


const BASE_URL = 'http://localhost:8080/api/vinetas/'

@Injectable()
export class VinetasService {
    constructor(private http: Http){}
    
    getVinetas(page:number){
        return this.http.get(BASE_URL+"?page="+page)
        .map(response => this.generateVinetas(response.json()))
        .catch(error => this.handleError(error))
    }

    getVinetasTag(tag: string) {
        return this.http.get(BASE_URL+"/busq/"+tag+"?filtro=tag")
        .map(response => this.generateVinetas(response.json()))
        .catch(error => this.handleError(error))
    }
    
    getVineta(id: number){
        return this.http.get(BASE_URL+id).map(
            response => this.generateVinetaWithComents(response.json())//this.extractVinetas(response)
        )
        .catch(error => this.handleError(error))
    }
    
    generateVinetas(vinetas: any[]){
      var lv: Vineta[] = [];
      for (let vineta of vinetas) {
          lv.push(this.generateVineta(vineta));
         }
         console.log(lv); 
      return lv;
    }

    generateVineta(vineta: any){
        var autor : Usuario = this.generateAutor(vineta.autor)
        var tag : Tag = this.generateTag(vineta.tags);
        return new Vineta(vineta.id, vineta.titulo, vineta.descripcion, vineta.URL, vineta.likes, vineta.dislikes, autor,tag);
    }
    generateVinetaWithComents(vineta: any){
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
        return new Usuario(autor.id, autor.username, autor.AvatarURL);
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
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}