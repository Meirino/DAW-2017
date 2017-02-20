package com.practicaDAW2017.CuantoMeme;

import java.util.List;

public class Vineta {
	
	private long id;
	private String titulo;
	private String autor;
	private String descripcion;
	private String tag;
	private String Url;
	private List<Comentario> comentarios;
	private int nComentatios;
	
	public Vineta() {}
	
	public Vineta(long ID, String titulo, String autor, String descrp, String tag, String Url) {
		this.setId(ID);
		this.setTitulo(titulo);
		this.setAutor(autor);
		this.setDescripcion(descrp);
		this.setTag(tag);
		this.setImgUrl(Url);
		this.setComentarios(null);
		this.setnComentatios();
	}
	
	public Vineta(long ID, String titulo, String autor, String descrp, String tag, String Url, List<Comentario> comentarios) {
		this.setId(ID);
		this.setTitulo(titulo);
		this.setAutor(autor);
		this.setDescripcion(descrp);
		this.setTag(tag);
		this.setImgUrl(Url);
		this.setComentarios(comentarios);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getImgUrl() {
		return Url;
	}

	public void setImgUrl(String Url) {
		this.Url = Url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public int getnComentatios() {
		return nComentatios;
	}

	public void setnComentatios() {
		if (this.comentarios == null) {
			this.nComentatios = 0;
		} else {
			this.nComentatios = this.comentarios.size();
		}
	}
}
