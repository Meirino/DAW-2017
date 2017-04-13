import { Injectable, OnInit } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import 'rxjs/Rx';

export interface User {  
    id?: number;
    name: string;
    roles: string[];
}

@Injectable()
export class LoginService {
	
	isLogged = false;
	isAdmin = false;
	user: User;
	
	constructor(private http: Http){
		this.reqIsLogged();
	}
	
	reqIsLogged(){
		//Esto da un 401. Es el valor esperado
		let headers = new Headers({
			'X-Requested-With': 'XMLHttpRequest'
		});
			
		let options = new RequestOptions({headers});		
		console.log("reqislogged---")

		this.http.get('http://localhost:8080/api/usuarios/logIn', options).subscribe(
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
		this.isLogged = true;
		this.user = response.json();
		this.isAdmin = this.user.roles.indexOf("ROLE_ADMIN") !== -1;
        console.log("es admin"+this.isAdmin)
	}
	
	logIn(user: string, pass: string) {
		
		let userPass = user + ":" + pass;
					
		let headers = new Headers({
			'Authorization': 'Basic '+utf8_to_b64(userPass),
			'X-Requested-With': 'XMLHttpRequest'
		});
		let options = new RequestOptions({headers});		
		console.log("logIN---")
		return this.http.get('http://localhost:8080/api/usuarios/logIn', options).map(
			response => {
				this.processLogInResponse(response);
				return this.user;
			}
		);		
	}
	
	logOut(){
		
		return this.http.get('logOut').map(
			response => {
				this.isLogged = false;
				this.isAdmin = false;
				return response;
			}
		);
	}	
}

function utf8_to_b64(str) {
    return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function(match, p1) {
        return String.fromCharCode(<any>'0x' + p1);
    }));
}