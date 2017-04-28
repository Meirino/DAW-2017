//import { Vineta } from '../classes/Vineta.class';
//import { Usuario } from '../classes/Usuario.class';
import { Comentario } from '../classes/Comentario.class';
//import { Tag } from '../classes/Tag.class';

import { Injectable } from '@angular/core';
import { Http, Response, JsonpModule, RequestOptions, Headers, URLSearchParams } from '@angular/http';

import {VinetasService } from './vinetas.service'
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

const BASE_URL = 'http://localhost:8080/api/comentarios/'

@Injectable()
export class ComentariosService {
constructor(private http: Http, private serviciovineta: VinetasService ){}

    //
comentarVineta(id: number, comentario: string) {
    const params = new URLSearchParams();
    params.append('texto', comentario);
    const headers = new Headers({
      'Content-Type': 'application/x-www-form-urlencoded',
      'X-Requested-With': 'XMLHttpRequest'
    });
    var url = BASE_URL+"vineta/"+id;
    console.log(url)
    const options = new RequestOptions();
    options.withCredentials = true;
    options.search = params;
    options.headers = headers;
    console.log("voy a pintar withCredentials "+options.withCredentials)
            return this.http.post(url, null, options)
                .map(response => this.serviciovineta.generateVinetaWithComents(response.json()))
                .catch(error => error);
    }

    eliminarComentario(id: number) {
        //Llamar a la API
        this.http.delete(BASE_URL+id, { withCredentials: true }).subscribe(response => console.log(response), error => console.log(error));
    }
}