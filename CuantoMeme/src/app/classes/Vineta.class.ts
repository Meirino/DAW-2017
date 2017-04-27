import { Comentario } from './Comentario.class';
import { Usuario } from './Usuario.class';
import { Tag } from './Tag.class';

export class Vineta {
    
    public id: number;
    public titulo: string;
    public descripcion: string;
    public imgURL: string;
    likes: number;
    dislikes: number;
    public autor : Usuario;
    public tag : Tag;

    public comentarios: Comentario[] = [];

    constructor(id: number, titulo: string, descripcion: string, imgURL: string, likes: number, dislikes: number, tag : Tag){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imgURL = imgURL;
        this.likes = likes;
        this.dislikes = dislikes;
        this.tag = tag;
    }
    setAutor(autor: Usuario){
        this.autor = autor;
    }
    setComentarios(comentarios: Comentario[]){
        this.comentarios = comentarios;
    }
    
}