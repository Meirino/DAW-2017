package es.urjc.code.daw.comentario;

import es.urjc.code.daw.user.*;
import es.urjc.code.daw.vineta.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;


@Entity
public class Comentario {
	
	public interface BasicAtt {}
	public interface UserAtt {}
	public interface VinetaAtt {}

	@Id
	@JsonView(BasicAtt.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@JsonView(BasicAtt.class)
	private String fecha;
	
	@JsonView(BasicAtt.class)
	private String comentario;
	
	@JsonView(UserAtt.class)
	@ManyToOne
	private User autor_comentario;
	
	@JsonView(VinetaAtt.class)
	@ManyToOne
	private Vineta vineta;
	
	//@OneToMany(mappedBy="comentarios")
	//private Vineta vineta;
	
	protected Comentario(){}

	public Comentario(String fecha, String comentario) {
		super();
		this.fecha = fecha;
		this.comentario = comentario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	public User getAutor_comentario() {
		return autor_comentario;
	}

	public void setAutor_comentario(User autor_comentario) {
		this.autor_comentario = autor_comentario;
	}

	public Vineta getVineta() {
		return vineta;
	}

	public void setVineta(Vineta vineta) {
		this.vineta = vineta;
	}
	
	
		
}
