import { Component } from '@angular/core';

@Component({
  selector: 'signup-component',
  templateUrl: './templates/signup.template.html',
  styleUrls: ['./templates/css/app.component.css']
})

export class SignUpComponent {
  //Comentario
  nombre: string = '';
  email: string = '';
  pass: string = '';
  fuerzaPass: string;

  getPassStrength(): void {
    //Inicializo su fuerza a 0
    let fuerza: number = 0;

    if(this.pass.length > 0) {
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