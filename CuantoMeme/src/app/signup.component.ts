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
  pass2: string = ''; 
}