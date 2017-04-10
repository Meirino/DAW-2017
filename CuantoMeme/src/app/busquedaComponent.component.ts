import { Component, OnInit } from '@angular/core';
import { Vineta } from './classes/Vineta.class';
import { VinetasService } from './services/vinetas.service';

@Component({
  selector: 'busq-component',
  templateUrl: './templates/busqueda.template.html',
  styleUrls: ['./templates/css/busqueda.css']
})

export class BusquedaComponent {
  title = 'Búsqueda avanzada';
  resultado: Vineta[] = [];
  texto: string;
  modo: string = "titulo";
  cuatrocientoscuatro: boolean = false;

  constructor(private servicioViñeta: VinetasService) {
    //etc
  }

  buscar() {
    this.servicioViñeta.busqVinetas(this.texto, this.modo).subscribe(
      vinetas => this.resultado = vinetas,
      error => function() {
        console.error(error);
        this.cuatrocientoscuatro = true;
        this.resultado = [];
      }
    );
  }

}