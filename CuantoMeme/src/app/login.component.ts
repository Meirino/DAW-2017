import { Component, Output, EventEmitter } from '@angular/core';
import { Usuario } from './classes/Usuario.class';
import { LoginService } from './services/login.service';
import { loggedUserService } from './services/logged-user.service';

@Component({
  selector: 'login-component',
  templateUrl: './templates/login.template.html',
  styleUrls: ['./templates/css/app.component.css']
})
export class LoginComponent {

  constructor(private ServicioLogin: LoginService, private servicioLogeado: loggedUserService) {}

  username: string;
  pass: string;

  //@Output() loggedUser = new EventEmitter<Usuario>();
  //Comentario

  login() {
    this.ServicioLogin.logIn(this.username, this.pass).subscribe(
      user =>  {
        this.ServicioLogin.setLoggedUser(user);
        console.log(this.ServicioLogin.user);
      },
      error => console.log(error)
    );
  }

}