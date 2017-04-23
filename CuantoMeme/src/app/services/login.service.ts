import { Injectable, OnInit, EventEmitter } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import { Usuario } from '../classes/Usuario.class';
import {VinetasService} from './vinetas.service'

import 'rxjs/Rx';
const BASE_URL = 'http://localhost:8080/api/usuarios/'

@Injectable()
export class LoginService {
	
	isLogged = false;
	user : Usuario;
	userUpdated:EventEmitter<Usuario> = new EventEmitter<Usuario>();

	constructor(private http: Http, private  vinetaservice: VinetasService){
		this.reqIsLogged();
	}
	
	reqIsLogged() {

        const headers = new Headers({
            'X-Requested-With': 'XMLHttpRequest'
        });

        const options = new RequestOptions({ withCredentials: true, headers });

        this.http.get(URL + 'logIn', options).subscribe(
            response => this.processLogInResponse(response),
            error => {
                if (error.status !== 401) {
                    console.error('Error when asking if logged: ' +
                        JSON.stringify(error));
                }
            }
        );
    }
	
	private processLogInResponse(response){
        var response = response.json()
        this.isLogged = true;
        this.user  = new Usuario(response.id, response.username, response.AvatarURL)
        this.user.setRoles(response.roles)
        this.user.setLogged(true);
        this.user.setSubidas(this.vinetaservice.generateVinetas(response.vinetas_subidas));
        this.user.setDislikes(this.vinetaservice.generateVinetas(response.vinetas_odiadas));
        this.user.setLikes(this.vinetaservice.generateVinetas(response.vinetas_gustadas));
        this.user.setFav(this.vinetaservice.generateVinetas(response.vinetas_favoritas));

}
	

    logIn(user: string, pass: string) {

        const userPass = user + ':' + pass;

        const headers = new Headers({
            'Authorization': 'Basic ' + utf8_to_b64(userPass),
            'X-Requested-With': 'XMLHttpRequest'
        });

        const options = new RequestOptions({ withCredentials: true, headers });
		var url = BASE_URL + 'logIn';
		console.log(url)
        return this.http.get(url, options).map(
            response => {
                this.processLogInResponse(response);
                return this.user;
            }
        );
    }

    logOut() {

        return this.http.get(BASE_URL + 'logOut', { withCredentials: true }).map(
            response => {
                this.isLogged = false;
                this.user = null;
                return response;
            }
        );
    }

	setLoggedUser(user: Usuario) {
		this.user = user;
		this.userUpdated.emit(this.user);
	}
}

function utf8_to_b64(str) {
    return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function(match, p1) {
        return String.fromCharCode(<any>'0x' + p1);
    }));
}