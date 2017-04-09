import { Vineta } from './Vineta.class';
import { Usuario } from './Usuario.class';

export class Comentario {
    private ID: number;
    private fecha: Date;
    private username: string;
    private userID: number;
    private userAvatar: string;
    private vineta: Vineta[];
    private texto: string;
}