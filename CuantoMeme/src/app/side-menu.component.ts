import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute} from '@angular/router';
import { Usuario } from './classes/Usuario.class';
import { LoginService } from './services/login.service';

@Component({
  selector: 'side-menu',
  templateUrl: './templates/sidemenu.template.html',
  styleUrls: ['./templates/css/sidemenu.css', './templates/font-awesome/css/font-awesome.css']
})

export class sideMenuComponent implements OnInit {
    activeUser: Usuario;

    constructor(private servicioLogin: LoginService) {
      this.activeUser = this.servicioLogin.user;
    }

    ngOnInit() {
      this.servicioLogin.userUpdated.subscribe(
        user => this.activeUser = user
      );
    }

    logOut() {
      this.servicioLogin.logOut().subscribe(response => console.log(response), error => console.log(error));
    }
}