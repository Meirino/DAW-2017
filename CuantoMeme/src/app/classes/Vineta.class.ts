import { Comentario } from './Comentario.class';

export class Vineta {
    
    public id: number;
    public titulo: string;
    public descripcion: string;
    public imgURL: string;
    public likes: number;
    public dislikes: number;
    public userAvatar: string;
    public userID: number;
    public username: string;
    public tagname: string;
    public comentarios: Comentario[];

    constructor(id: number, titulo: string, descripcion: string, imgURL: string, likes: number, dislikes: number, useravatar: string, userID: number, username: string, tagname: string, comentarios: Comentario[]) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imgURL = imgURL;
        this.likes = likes;
        this.dislikes = dislikes;
        this.userAvatar = useravatar;
        this.userID = userID;
        this.username = username;
        this.tagname = tagname;
        this.comentarios = comentarios;
    }
    
}