package es.urjc.code.daw.vineta;
import es.urjc.code.daw.tag.*;
import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.user.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Vineta {
	//public interface BasicAtt{}
	//public interface UserAtt{}
	//public interface ComentariosAtt{}
	//public interface TagAtt{}
	
	@Id
	//@JsonView(BasicAtt.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//@JsonView(BasicAtt.class)
	private String titulo;
	
	//@JsonView(BasicAtt.class)
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date creationdate;
	//@JsonView(BasicAtt.class)
	private String URL;
	
	//@JsonView(BasicAtt.class)
	private long likes = 0;
	
	//@JsonView(BasicAtt.class)
	private long dislikes = 0;
	
	//@JsonView(BasicAtt.class)
	private String descripcion;
	
	private String username_autor;
	private long  id_autor;
	private String nombre_tag;
	private long  id_tag;
	

	//@JsonView(UserAtt.class)
	@JsonIgnore
	@ManyToOne
	private User autor;
	
	//@JsonView(ComentariosAtt.class)
	@JsonIgnore
	@OneToMany(mappedBy="vineta", cascade = CascadeType.ALL)
	private List<Comentario> comentarios = new ArrayList<>(); 
	
	//@JsonView(TagAtt.class)
	@JsonIgnore
	@ManyToOne
	private Tag tags = null;
	
	//@JsonView(UserAtt.class)
	@JsonIgnore
	@ManyToMany(mappedBy="vinetas_favoritas")
	private List<User> users_fav = new ArrayList<>();
	
	//@JsonView(UserAtt.class)
	@JsonIgnore
	@ManyToMany(mappedBy="vinetas_gustadas")
	private List<User> users_likes = new ArrayList<>();
	
	//@JsonView(UserAtt.class)
	@JsonIgnore
	@ManyToMany(mappedBy="vinetas_odiadas")
	private List<User> users_dislikes = new ArrayList<>();
	
	protected Vineta(){}
	
	public Vineta(String titulo, String descripcion, String URL){
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.URL = URL;
		this.creationdate = new Date();
	}

	
	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
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
		this.username_autor = autor.getUsername();
		this.id_autor = autor.getId();
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Tag getTags() {
		return tags;
	}

	public void setTags(Tag tags) {
		this.tags = tags;
		this.id_tag = tags.getId();
		this.nombre_tag = tags.getNombre();
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

	public List<User> getUsers_fav() {
		return users_fav;
	}

	public void setUsers_fav(List<User> users_fav) {
		this.users_fav = users_fav;
	}

	public List<User> getUsers_likes() {
		return users_likes;
	}

	public void setUsers_likes(List<User> users_likes) {
		this.users_likes = users_likes;
	}

	public List<User> getUsers_dislikes() {
		return users_dislikes;
	}

	public void setUsers_dislikes(List<User> users_dislikes) {
		this.users_dislikes = users_dislikes;
	}

	public String getNombre_tag() {
		return nombre_tag;
	}

	public void setNombre_tag(String nombre_tag) {
		this.nombre_tag = nombre_tag;
	}

	public long getId_tag() {
		return id_tag;
	}

	public void setId_tag(long id_tag) {
		this.id_tag = id_tag;
	}

	public long getId_autor() {
		return id_autor;
	}

	public void setId_autor(long id_autor) {
		this.id_autor = id_autor;
	}

	public String getUsername_autor() {
		return username_autor;
	}

	public void setUsername_autor(String username_autor) {
		this.username_autor = username_autor;
	}

	@Override
	public String toString() {
		return "Vineta [id=" + id + ", titulo=" + titulo + ", creationdate=" + creationdate + ", URL=" + URL
				+ ", likes=" + likes + ", dislikes=" + dislikes + ", descripcion=" + descripcion + ", autor=" + autor
				+ ", comentarios=" + comentarios + ", tags=" + tags + ", users_fav=" + users_fav + ", users_likes="
				+ users_likes + ", users_dislikes=" + users_dislikes + "]";
	}
	
}
