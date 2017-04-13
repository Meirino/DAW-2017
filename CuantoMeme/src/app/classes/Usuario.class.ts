import { Vineta } from './Vineta.class';
import { Comentario } from './Comentario.class';

export class Usuario {
    
    private id: number;
    private avatarURL: string;
    private username: string;
    private password: string;
    private email: string;
    private publicaciones: Vineta[];
    private seguidos: Usuario[];
    private seguidores: Usuario[];
    //private comentarios: Comentario[];
    private likes: Vineta[];
    private dislikes: Vineta[];
    private favoritos: Vineta[];
    private isLogged: Boolean = false;
    private roles: string[] = [];


    constructor(id: number, username: string, avatar: string) {
        this.username = username;
        this.id = id;
        this.avatarURL = avatar;
    }
    getlikes(){
        //likes = /api/users/byusername[vientaslikes]
    }
    setLogged(logged:Boolean){
        this.isLogged = logged;
    }
    isAdmin(){
        return this.roles.indexOf("ROLE_ADMIN") !== -1;
    }
    setRoles(roles: string[]){
        this.roles = roles;
    }
}