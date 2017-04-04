import { Vineta } from './Vineta.class';

export class Usuario {

    private username: string;
    private password: string;
    private email: string;
    private avatarURL: string;
    private publicaciones: Vineta[];
    private seguidos: Usuario[];
    private seguidores: Usuario[];
    //private comentarios: Comentario[];
    private likes: Vineta[];
    private dislikes: Vineta[];
    private favoritos: Vineta[];

    constructor(username: string, contraseña: string, email:string) {
        this.username = username;
        this.password = contraseña;
        this.email = email;
    }
}