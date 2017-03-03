package es.urjc.code.daw.vineta;
import es.urjc.code.daw.tag.*;
import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.user.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Vineta {
	public interface BasicAtt{}
	public interface UserAtt{}
	public interface ComentariosAtt{}
	public interface TagAtt{}
	
	@Id
	@JsonView(BasicAtt.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@JsonView(BasicAtt.class)
	private String titulo;
	
	@JsonView(BasicAtt.class)
	private String descripcion;
	
	
	@JsonView(UserAtt.class)
	@ManyToOne
	private User autor;
	
	@JsonView(ComentariosAtt.class)
	@OneToMany(mappedBy="vineta")
	private List<Comentario> comentarios = new ArrayList<>(); 
	
	@JsonView(TagAtt.class)
	@ManyToMany
	private List<Tag> tags = new ArrayList<>();
	
	protected Vineta(){}
	
	public Vineta(String titulo, String descripcion){
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public User getAutor() {
		return autor;
	}

	public void setAutor(User autor) {
		this.autor = autor;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	
}
