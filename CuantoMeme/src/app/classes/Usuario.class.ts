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
    private subidas: Vineta[];
    private isLogged: Boolean = false;
    private roles: string[] = [];
    private isAdmin: boolean;


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
    setRoles(roles: string[]){
        this.roles = roles;  
        this.isAdmin = (this.roles.indexOf("ROLE_ADMIN") !== -1);
    }
    setSubidas(vinetas: Vineta[]){
        this.subidas = vinetas;
    }
    setFav(vinetas: Vineta[]){
        this.favoritos = vinetas;
    }
    setLikes(vinetas: Vineta[]){
        this.likes= vinetas;
    }
    setDislikes(vinetas: Vineta[]){
        this.dislikes= vinetas;
    }
    getRoles() {
        return this.roles;
    }
    getUsername() {
        return this.username;
    }
}