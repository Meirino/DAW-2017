import { Vineta } from '../classes/Vineta.class';
import { Usuario } from '../classes/Usuario.class';
import { Comentario } from '../classes/Comentario.class';
import { Tag } from '../classes/Tag.class';

import { Injectable } from '@angular/core';
import { Http, Response, JsonpModule, RequestOptions } from '@angular/http';

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

    likeVineta(id: number){

    /*var headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Access-Control-Allow-Origin', '');
    headers.append('Access-Control-Allow-Headers', '');
    headers.append('Access-Control-Allow-Methods', '*');
    headers.append('Access-Control-Allow-Credentials', 'true');


    let options = new RequestOptions(headers);
    console.log("llego al put")
        return this.http.put(BASE_URL+"like2/"+id, { withCredentials: true, headers: headers })
            .map(response => response)
            .catch(error => error);
            
      Desde aqui
      org.springframework.security.authentication.UsernamePasswordAuthenticationToken@b37b: Principal: admin; Credentials: [PROTECTED]; Authenticated: false; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@b364: RemoteIpAddress: 0:0:0:0:0:0:0:1; SessionId: null; Not granted any authorities
      Desde apiclient
      org.springframework.security.authentication.UsernamePasswordAuthenticationToken@b37b: Principal: usuario_9; Credentials: [PROTECTED]; Authenticated: false; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@b364: RemoteIpAddress: 0:0:0:0:0:0:0:1; SessionId: null; Not granted any authorities


      org.springframework.security.authentication.UsernamePasswordAuthenticationToken@441d0230: Principal: usuario_7; Credentials: [PROTECTED]; Authenticated: true; Details: null; Granted Authorities: ROLE_USER
    org.springframework.security.authentication.UsernamePasswordAuthenticationToken@b37b: Principal: admin; Credentials: [PROTECTED]; Authenticated: false; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@b364: RemoteIpAddress: 0:0:0:0:0:0:0:1; SessionId: null; Not granted any authorities
******
org.springframework.security.authentication.UsernamePasswordAuthenticationToken@441d0230: Principal: usuario_6; Credentials: [PROTECTED]; Authenticated: true; Details: null; Granted Authorities: ROLE_USER
******

******
org.springframework.security.authentication.UsernamePasswordAuthenticationToken@80cbfd33: Principal: pepe; Credentials: [PROTECTED]; Authenticated: true; Details: null; Granted Authorities: ROLE_USER
******
            */
    let headers = new Headers({
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    });
    let options = new RequestOptions(headers);
    console.log("llego al put")
        return this.http.put(BASE_URL+"like2/"+id, options)
            .map(response => response)
            .catch(error => error);
    }
    getVinetasTag(tag: string) {
        return this.http.get(BASE_URL+"/busq/"+tag+"?filtro=tag")
        .map(response => this.generateVinetas(response.json()))
        .catch(error => this.handleError(error))
    }

    busqVinetas(texto: string, modo:string) {
        return this.http.get(BASE_URL+"/busq/"+texto+"?filtro="+modo)
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
		return Observable.throw("Server error (" + error.status + "): " + error.text());
	}
}