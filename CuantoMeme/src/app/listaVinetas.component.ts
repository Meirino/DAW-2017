import { Vineta } from './classes/Vineta.class';
import { Component, Input, OnInit } from '@angular/core';
import { VinetasService } from './services/vinetas.service';

@Component({
  selector: 'lista-vinetas',
  templateUrl: './templates/listaVinetas.template.html',
  styleUrls: ['./templates/css/listaVinetas.css', './templates/font-awesome/css/font-awesome.css']
})

export class listaVinetasComponent implements OnInit {
  
  //El componente recibe una lista de viñetas y las muestra
  @Input() listaVinetas: Vineta[];
  private vinetas: Vineta[];

  constructor(private servicioVinetas: VinetasService) {
    //etc
  }

  ngOnInit() {
    this.servicioVinetas.getVinetas().subscribe(
      vinetas => this.generateVinetas(vinetas),
      error => console.error(error)
    );
  }
  generateVinetas(vinetas: any[]){
    for (let vineta of vinetas) {
      this.listaVinetas.push(new Vineta(vineta.id, vineta.titulo, vineta.descripcion, vineta.URL, vineta.likes, vineta.dislikes));
       // 1, "string", false
    } 
    console.log("-------");
    console.log(this.listaVinetas);
  }

  like(viñeta: Vineta): void {
    //Añadir la llamada a la API de hacer like
    viñeta.likes += 1;
  }

  dislike(viñeta: Vineta): void {
    //Añadir la llamada a la API de hacer dislike
    viñeta.dislikes += 1;
  }

  fav(viñeta: Vineta): void {
    //Añadir la llamada a la API de hacer favoritas
  }

}