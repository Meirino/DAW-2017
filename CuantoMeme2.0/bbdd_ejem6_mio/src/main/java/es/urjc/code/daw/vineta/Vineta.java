package es.urjc.code.daw.vineta;
import es.urjc.code.daw.tag.*;
import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.user.*;

import java.util.ArrayList;
import java.util.Date;
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
	private Date creationdate;
	@JsonView(BasicAtt.class)
	private String URL;
	
	@JsonView(BasicAtt.class)
	private long likes = 0;
	
	@JsonView(BasicAtt.class)
	private long dislikes = 0;
	
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
	
	public Vineta(String titulo, String descripcion, String URL){
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.URL = URL;
		this.creationdate = new Date();
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}

	public long getDislikes() {
		return dislikes;
	}

	public void setDislikes(long dislikes) {
		this.dislikes = dislikes;
	}

	public Date getCreated_date() {
		return creationdate;
	}

	public void setCreated_date(Date created_date) {
		this.creationdate = created_date;
	}
	
	public void like(){
		this.likes++;
	}
	public void dislike(){
		this.dislikes++;
	}
}
