package com.practicaDAW2017.CuantoMeme;

public class Comentario {
	
	private long ID;
	private String Autor;
	private String Comentario;
	
	public Comentario(long ID, String autor, String texto) {
		this.setID(ID);
		this.setAutor(autor);
		this.setComentario(texto);
	}

	public String getAutor() {
		return Autor;
	}

	public void setAutor(String autor) {
		Autor = autor;
	}

	public String getComentario() {
		return Comentario;
	}

	public void setComentario(String comentario) {
		Comentario = comentario;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

}
