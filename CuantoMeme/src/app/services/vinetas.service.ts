import { Vineta } from '../classes/Vineta.class';
import { Usuario } from '../classes/Usuario.class';

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
  generateVinetas(vinetas: any[]){
      var lv: Vineta[] = [];
      var autor : Usuario;
      for (let vineta of vinetas) {
          autor = new Usuario(vineta.autor.id, vineta.autor.username, vineta.autor.AvatarURL)
          lv.push(new Vineta(vineta.id, vineta.titulo, vineta.descripcion, vineta.URL, vineta.likes, vineta.dislikes, autor));
         } 
         console.log(lv);
      return lv;
  }

}