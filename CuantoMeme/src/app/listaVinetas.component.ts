import { Vineta } from './classes/Vineta.class';
import { Component, Input, OnInit } from '@angular/core';
import { VinetasService } from './services/vinetas.service';
import { UsuarioService } from './services/usuarios.service';

@Component({
  selector: 'lista-vinetas',
  templateUrl: './templates/listaVinetas.template.html',
  styleUrls: ['./templates/css/listaVinetas.css', './templates/font-awesome/css/font-awesome.css']
})

export class listaVinetasComponent {
  
  //El componente recibe una lista de viñetas y las muestra
  @Input() listaVinetas: Vineta[];

        constructor(private servicioVinetas: VinetasService, private serviciousuarios: UsuarioService) {
          //etc
        }

        ngOnInit() {
          
          this.servicioVinetas.getVinetas().subscribe(
            vinetas => this.listaVinetas = vinetas,
            error => console.error(error)
          );
          
          this.servicioVinetas.getVineta(101).subscribe(
            vineta => console.log(vineta),
            error => console.error(error)
          );
          this.serviciousuarios.getUsers().subscribe(
            usuarios => console.log(usuarios),
            error => console.error(error)
          );
        }

  like(viñeta: Vineta): void {
    viñeta.likes = viñeta.likes + 1;
  }

  dislike(viñeta: Vineta): void {
    viñeta.dislikes = viñeta.dislikes + 1;
  }

}