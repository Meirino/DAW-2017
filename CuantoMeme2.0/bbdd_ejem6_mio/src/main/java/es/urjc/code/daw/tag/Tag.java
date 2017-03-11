package es.urjc.code.daw.tag;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import es.urjc.code.daw.vineta.Vineta;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Tag {
	public interface VinetaAtt {}
	public interface BasicAtt {}
	
	@Id
	@JsonView(BasicAtt.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@JsonView(BasicAtt.class)
	private String nombre;
	
	@OneToMany(mappedBy="tags")
	private List<Vineta> vinetas = new ArrayList<>();
	
	protected Tag(){}

	public Tag(String nombre) {
		super();
		this.nombre = nombre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Vineta> getVinetas() {
		return vinetas;
	}

	public void setVinetas(List<Vineta> vinetas) {
		this.vinetas = vinetas;
	}
	
	
}
