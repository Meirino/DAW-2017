import { Component } from '@angular/core';
import { Vineta } from './classes/Vineta.class';
import { listaVinetasComponent } from './listaVinetas.component';
import { VinetasService } from './services/vinetas.service';
import { UsuarioService } from './services/usuarios.service';

@Component({
  selector: 'index-component',
  templateUrl: './templates/indexComponent.template.html',
  styleUrls: ['./templates/css/index.css', './templates/font-awesome/css/font-awesome.css']
})

export class IndexComponent {
  title = '¡Bienvenido a CuantoMeme!';
  listaVinetas = [];
  currentPage = 0;

  constructor(private servicioVinetas: VinetasService) {
    //etc
  }

  ngOnInit() {
    this.servicioVinetas.getVinetas(this.currentPage).subscribe(
      vinetas => this.listaVinetas = vinetas,
      error => console.error(error)
    );
  }

  masVinetas(): void {
    this.currentPage = this.currentPage + 1;
    this.servicioVinetas.getVinetas(this.currentPage).subscribe(
      vinetas => this.listaVinetas.push(vinetas),
      error => console.error(error)
    );
  }

}