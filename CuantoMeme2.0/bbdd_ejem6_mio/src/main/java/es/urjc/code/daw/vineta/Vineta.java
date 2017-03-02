package es.urjc.code.daw.vineta;
import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.user.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.user.User.BasicAtt;

@Entity
public class Vineta {
	public interface BasiAtt{}
	public interface UserAtt{}
	public interface ComentariosAtt{}
	
	@Id
	@JsonView(BasicAtt.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@JsonView(BasicAtt.class)
	private String titulo;
	
	@JsonView(BasicAtt.class)
	private String descripcion;
	
	/*
	@JsonView(UserAtt.class)
	
	private User autor;
	
	lo de abajao no workea. el att mappedBy no esta disponible para manyToOne
	@JsonView(UserAtt.class)
	@ManyToOne(mappedBy="viñetas")
	private User autor;
	
	*/
	
	@JsonView(UserAtt.class)
	@ManyToOne
	private User autor;
	
	@JsonView(ComentariosAtt.class)
	@OneToMany(mappedBy="vineta")
	private List<Comentario> comentarios = new ArrayList<>(); 
	
	protected Vineta(){}
	
	public Vineta(String titulo, String descripcion){
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
}
