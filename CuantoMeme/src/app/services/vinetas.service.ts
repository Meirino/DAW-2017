import { Vineta } from '../classes/Vineta.class';
import { Usuario } from '../classes/Usuario.class';
import { Comentario } from '../classes/Comentario.class';
import { Injectable } from '@angular/core';
import { Http, Response, JsonpModule } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
const BASE_URL = 'http://127.0.0.1:8080/api/vinetas/'

@Injectable()
export class VinetasService {
    constructor(private http: Http){}
    
    getVinetas(){
        return this.http.get(BASE_URL).map(
            response => this.generateVinetas(response.json())//this.extractVinetas(response)
        )
    }
    
    getVineta(id: number){
        return this.http.get(BASE_URL+id).map(
            response => this.generateVinetaWithComents(response.json())//this.extractVinetas(response)
        )
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
        return new Vineta(vineta.id, vineta.titulo, vineta.descripcion, vineta.URL, vineta.likes, vineta.dislikes, autor);
    }
    generateVinetaWithComents(vineta: any){
        var autor : Usuario = this.generateAutor(vineta.autor)
        var comentarios : Comentario[] = [];
        for(let comentario of vineta.comentarios){
            comentarios.push(this.generateComentario(comentario));
        }
        var vineta2 : Vineta = new Vineta(vineta.id, vineta.titulo, vineta.descripcion, vineta.URL, vineta.likes, vineta.dislikes, autor);
        vineta2.setComentarios(comentarios);
        return vineta2
        //return new Vineta(vineta.id, vineta.titulo, vineta.descripcion, vineta.URL, vineta.likes, vineta.dislikes, autor);
    }
    generateAutor(autor: any){
        return new Usuario(autor.id, autor.username, autor.AvatarURL);
    }
    generateComentario(comentario: any){
        var autor : Usuario = this.generateAutor(comentario.autor_comentario);
        return new Comentario(comentario.id, comentario.fecha, comentario.comentario, autor);
    }
}