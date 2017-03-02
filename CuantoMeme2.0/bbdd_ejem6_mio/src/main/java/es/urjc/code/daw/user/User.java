package es.urjc.code.daw.user;
import es.urjc.code.daw.vineta.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;
import es.urjc.code.daw.comentario.*;
@Entity
public class User {
	public interface BasicAtt {}
	public interface VinetaAtt{}
	public interface ComentarioAtt{}
	public interface SeguidoresAtt{}
	
	@Id
	@JsonView(BasicAtt.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@JsonView(BasicAtt.class)
	private String username;
	
	private String password;
	
	@JsonView(VinetaAtt.class)	
	@OneToMany(mappedBy="autor")
	private List<Vineta> viñetas = new ArrayList<>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="Usuario_Seguidores")
	@JsonView(SeguidoresAtt.class)
	private List<User> seguidores = new ArrayList<>();
	
	@JsonView(ComentarioAtt.class)
	@OneToMany(mappedBy="autor_comentario")
	private List<Comentario> comentarios = new ArrayList<>();
	
	protected User(){}
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}

	
	public List<User> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(List<User> seguidores) {
		this.seguidores = seguidores;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Vineta> getViñetas() {
		return viñetas;
	}

	public void setViñetas(List<Vineta> viñetas) {
		this.viñetas = viñetas;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	
}
