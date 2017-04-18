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
		//this.reqIsLogged();
	}
	
	reqIsLogged(){
		//Esto da un 401. Es el valor esperado
		console.log("paso por aqui 401")
		let headers = new Headers({
			'X-Requested-With': 'XMLHttpRequest'
		});
			
		let options = new RequestOptions({headers});		
		this.http.get(BASE_URL+'logIn', options).subscribe(
			response => this.processLogInResponse(response),
			error => {
				if(error.status != 401){
					console.error("Error when asking if logged: "+
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
		
		let userPass = user + ":" + pass;
					
		let headers = new Headers({
			'Authorization': 'Basic '+utf8_to_b64(userPass),
			'X-Requested-With': 'XMLHttpRequest'
		});
		let options = new RequestOptions({headers});		
		console.log("logIN---")
		return this.http.get(BASE_URL+'logIn', options).map(
			response => {
				console.log("______")
				console.log(response)
				this.processLogInResponse(response);
				return this.user;
			}
		);		
	}
	
	logOut() {
		
		return this.http.get(BASE_URL+'logOut').map(
			response => {
				this.isLogged = false;
				this.user = null;
				console.log("Sesión cerrada");
				return response;
			}
		);
	}

	setLoggedUser() {
		this.userUpdated.emit(this.user);
	}

	/*loggedUserIsAdmin() {
		if(this.user && (this.user.getRoles()[1] === "ROLE_ADMIN")) {
			this.isAdmin = true;
			console.log(this.user.getUsername + " es admin");
		}
	}*/
}

function utf8_to_b64(str) {
    return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function(match, p1) {
        return String.fromCharCode(<any>'0x' + p1);
    }));
}