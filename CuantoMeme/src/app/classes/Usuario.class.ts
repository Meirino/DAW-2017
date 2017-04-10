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

    constructor(id: number, username: string, avatar: string) {
        this.username = username;
        this.id = id;
        this.avatarURL = avatar;
    }
    getlikes(){
        //likes = /api/users/byusername[vientaslikes]
    }
}