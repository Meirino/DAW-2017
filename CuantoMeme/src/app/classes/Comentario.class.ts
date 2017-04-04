import { Vineta } from './Vineta.class';
import { Usuario } from './Usuario.class';

export class Comentario {
    private ID: number;
    private fecha: Date;
    private autor: Usuario;
    private vineta: Vineta[];
    private texto: string;
}