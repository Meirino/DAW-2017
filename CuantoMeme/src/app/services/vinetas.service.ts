import { Vineta } from '../classes/Vineta.class';
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
            response => response.json()//this.extractVinetas(response)
        )
    }
    /*
    extractVinetas(response: Response){
        //console.log(response);
        console.log(response.json());
        return response.json().map(({id, titulo}) => new Vineta(id:id, titulo:titulo))
    }
    */

}