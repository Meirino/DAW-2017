import { Vineta } from './Vineta.class';
import { Usuario } from './Usuario.class';

export class Comentario {
    public ID: number;
    private fecha: Date;
    private comentario: string;
    private autor_comentario: Usuario;
    
    constructor(id: number, fecha: Date, comentario: string, autor: Usuario){
        this.ID = id;
        this.fecha = fecha;
        this.comentario = comentario;
        this.autor_comentario = autor;
    }
}