import { Comentario } from './Comentario.class';

export class Vineta {
    
    public id: number;
    public titulo: string;
    public descripcion: string;
    public imgURL: string;
    public likes: number;
    public dislikes: number;

    constructor(id: number, titulo: string, descripcion: string, imgURL: string, likes: number, dislikes: number) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imgURL = imgURL;
        this.likes = likes;
        this.dislikes = dislikes;
    }
    
}