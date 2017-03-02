package es.urjc.code.daw;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.comentario.ComentarioRepository;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.vineta.*;

@RestController
public class CMControler {
	interface VinetaView extends Vineta.BasiAtt, Vineta.UserAtt, User.BasicAtt{}
	interface UserView extends User.BasicAtt, User.VinetaAtt, User.ComentarioAtt, Comentario.BasicAtt{}
	interface ComentarioView extends Comentario.BasicAtt, Comentario.UserAtt, User.BasicAtt, Comentario.VinetaAtt, Vineta.BasiAtt{}
	@Autowired
	private UserRepository userrepository;
	@Autowired
	private VinetaRepository vinetarepository;
	
	@Autowired
	private ComentarioRepository comentariorepository;
	
	@PostConstruct
	public void init(){
		User usuario1 = new User("joaquin", "joa");
		User usuario2 = new User("paco", "paquito");
		
		this.userrepository.save(usuario1);
		this.userrepository.save(usuario2);
		
		Vineta v1 = new Vineta("vineta1", "des1");
		Vineta v2 = new Vineta("vineta2", "des2");
		
		v1.setAutor(usuario1);
		v2.setAutor(usuario1);
		
		this.vinetarepository.save(v1);
		this.vinetarepository.save(v2);

		
		Comentario c1 = new Comentario("12/12/2015", "mi primer comentario");
		c1.setAutor_comentario(usuario1);
		c1.setVineta(v1);
		this.comentariorepository.save(c1);
		//this.userrepository.save(usuario2);
		


	}

	
	@JsonView(VinetaView.class)
	@RequestMapping("/vinetas/")
	public List<Vineta> getvinetas(){
		return this.vinetarepository.findAll();
	}
	
	@JsonView(UserView.class)
	@RequestMapping("/usuarios/")
	public List<User> getusuarios(){
		return this.userrepository.findAll();
	}
	
	@JsonView(ComentarioView.class)
	@RequestMapping("/comentarios/")
	public List<Comentario> getcomentarios(){
		return this.comentariorepository.findAll();
	}
}
