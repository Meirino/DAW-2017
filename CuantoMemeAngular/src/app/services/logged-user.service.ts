import { Injectable, OnInit } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import { Usuario } from '../classes/Usuario.class';

@Injectable()
export class loggedUserService {
    
    UsuarioLogeado: Usuario;

    constructor() {
        this.UsuarioLogeado = null;
    }

    setUsuario(user: Usuario): void {
        this.UsuarioLogeado = user;
    }

    getUsuario(): Usuario {
        return this.UsuarioLogeado;
    }

    isAdmin(): boolean {
        if(this.UsuarioLogeado.getRoles[2]) {
            return true;
        } else {
            return false;
        }
    }

}