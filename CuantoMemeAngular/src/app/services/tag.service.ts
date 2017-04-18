import { Vineta } from '../classes/Vineta.class';
import { Usuario } from '../classes/Usuario.class';
import { Comentario } from '../classes/Comentario.class';
import { Tag } from '../classes/Tag.class';

import { Injectable } from '@angular/core';
import { Http, Response, JsonpModule } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

const BASE_URL = 'http://localhost:8080/api/usuarios/'

@Injectable()
export class TagService {
    constructor(private http: Http){}

    //

}