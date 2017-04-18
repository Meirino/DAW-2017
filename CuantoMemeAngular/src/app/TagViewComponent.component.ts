import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute} from '@angular/router';
import { VinetasService } from './services/vinetas.service';

@Component({
  selector: 'tag-view',
  templateUrl: './templates/tagView.template.html',
  styleUrls: ['./templates/css/index.css']
})

export class TagViewComponent {
  title = '';
  tag: string;
  listaVinetas = [];

  constructor(private route: ActivatedRoute, private router: Router, private servicioViñeta: VinetasService) {
    //etc
  }

  ngOnInit() {
    this.tag = this.route.snapshot.params['nombre']
    this.title = 'Buscando tag: ' + this.tag;
    //Llamar al servicio de viñetas, coger las viñetas que tengan ese tag y dárselos a la lista de viñetas
    this.servicioViñeta.getVinetasTag(this.tag).subscribe(
      vinetas => this.listaVinetas = vinetas,
      error => console.error(error)
    );
  }

}