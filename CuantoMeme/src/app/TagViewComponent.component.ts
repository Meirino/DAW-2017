import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'tag-view',
  templateUrl: './templates/tagView.template.html',
  styleUrls: ['./templates/css/index.css']
})

export class TagViewComponent {
  title = '';
  listaVinetas = [];

  constructor() {
    //etc
  }

  ngOnInit() {
    this.title = 'Buscando tag: ';
    //Llamar al servicio de tags, coger el tag y dárselos a la lista de viñetas
  }

}