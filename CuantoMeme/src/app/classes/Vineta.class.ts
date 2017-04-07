export class Vineta {
    
    public id: number;
    public titulo: string;
    public descripcion: string;
    public imgURL: string;
    public likes: number;
    public dislikes: number;

    constructor(id: number, titulo: string, descripcion: string, imgURL: string) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imgURL = imgURL;
        this.likes = 0;
        this.dislikes = 0;
    }
    
}