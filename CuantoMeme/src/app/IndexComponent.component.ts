import { Component } from '@angular/core';
import { Vineta } from './classes/Vineta.class';
import { listaVinetasComponent } from './listaVinetas.component';
import { VinetasService } from './services/vinetas.service';

@Component({
  selector: 'index-component',
  templateUrl: './templates/indexComponent.template.html',
  styleUrls: ['./templates/css/index.css']
})

export class IndexComponent {
  title = '¡Bienvenido a CuantoMeme!';
  listaVinetas = [];

  constructor(private servicioVinetas: VinetasService) {
    //etc
  }

  ngOnInit() {
    this.servicioVinetas.getVinetas().subscribe(
      vinetas => this.listaVinetas = vinetas,
      error => console.error(error)
    );
  }

}