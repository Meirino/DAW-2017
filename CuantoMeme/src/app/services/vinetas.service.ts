import { Vineta } from '../classes/Vineta.class';
import { Injectable } from '@angular/core';
import { Http, Response, JsonpModule } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class VinetasService {

    constructor(private httpService: Http) {
        //
    }
}