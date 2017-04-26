import { Component } from '@angular/core';
import { LoginService } from './services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'signup-component',
  templateUrl: './templates/signup.template.html',
  styleUrls: ['./templates/css/app.component.css']
})

export class SignUpComponent {
    constructor(private ServicioLogin: LoginService, private redireccion: Router) {}

  //Comentario
  nombre: string = '';
  email: string = '';
  pass: string = '';
  fuerzaPass: string = 'nula';
  signupuser(){
    this.ServicioLogin.signup(this.nombre, this.email, this.pass).subscribe(
      response => {
                  if (response == 201){
                      alert("Creado correctamente. \n Ahora se te redirigira al portal de Login")
                      this.redireccion.navigateByUrl("/login");

                  }
      },
      error => console.log(error))
  }
  getPassStrength(): void {
    //Inicializo su fuerza a 0
    let fuerza: number = 0;

    if(this.pass.length > 1) {
      fuerza = (5 * this.pass.length);

      //Si contiene números entonces le doy puntos
      if(this.pass.replace(/[^0-9]/g,"").length > 0) {
        fuerza = fuerza + 20;
      }

      //Si la fuerza es mayor de cincuenta es media
      if(fuerza > 0) {
        this.fuerzaPass = "baja";
      } if(fuerza >= 30) {
        this.fuerzaPass = "media";
      } if(fuerza >= 60) {
        this.fuerzaPass = "fuerte";
      } if(fuerza >= 90) {
        this.fuerzaPass = "muy fuerte";
      }

    } else {
      this.fuerzaPass = "nula";
    }
    
  }

}