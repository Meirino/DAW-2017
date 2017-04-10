import { Comentario } from './Comentario.class';
import { Usuario } from './Usuario.class';
import { Tag } from './Tag.class';

export class Vineta {
    
    private id: number;
    private titulo: string;
    private descripcion: string;
    private imgURL: string;
    private likes: number;
    private dislikes: number;
    private autor : Usuario;
    private tag : Tag;

    private comentarios: Comentario [] = [];

    constructor(id: number, titulo: string, descripcion: string, imgURL: string, likes: number, dislikes: number, autor: Usuario, tag : Tag){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imgURL = imgURL;
        this.likes = likes;
        this.dislikes = dislikes;
        this.autor = autor;
        this.tag = tag;
    }

    setComentarios(comentarios: Comentario[]){
        this.comentarios = comentarios;
    }
    
}