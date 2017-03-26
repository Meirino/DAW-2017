package es.urjc.code.daw.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.tag.TagRepository;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserRepository;
import es.urjc.code.daw.vineta.Vineta;
import es.urjc.code.daw.vineta.VinetaRepository;

@RestController
public class CMRestControler {
	
	interface VinetaView extends Vineta.BasicAtt, Vineta.UserAtt, User.BasicAtt, Vineta.TagAtt, Tag.BasicAtt, Vineta.ComentariosAtt, Comentario.BasicAtt, Comentario.UserAtt{}
	interface UserView extends User.BasicAtt, User.VinetaAtt, User.ComentarioAtt, Comentario.BasicAtt, Vineta.BasicAtt{}
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private VinetaRepository vinetarepository;
	
	@Autowired
	private TagRepository tagrepository;
	
	@JsonView(VinetaView.class)
	@RequestMapping("/api/vinetas2/")
	public List<Vineta> getvinetas2(){
		return this.vinetarepository.findAll();
	}
	@JsonView(VinetaView.class)
	@RequestMapping("/api/vinetas3/")
	public Page<Vineta> getvinetas3(Pageable page){
		return this.vinetarepository.findAll(page);
	}
	@JsonView(Vineta.BasicAtt.class)
	@RequestMapping("/api/vineta/{id}")
	public Vineta getvineta(@PathVariable long id){
		return this.vinetarepository.findOne(id);
	}
	
	@JsonView(User.BasicAtt.class)
	@RequestMapping("/api/usuarios/")
	public List<User> getusuarios(){
		return this.userrepository.findAll();
	}
	
	@JsonView(UserView.class)
	@RequestMapping("/api/usuarios/{id}")
	public User getusuario(@PathVariable int id){
		return this.userrepository.findOne((long) id);
	}
	
	@JsonView(UserView.class)
	@RequestMapping("/api/usuariosByName/{nombre}")
	public User getusuariobyname(@PathVariable String nombre){
		return this.userrepository.findByUsername(nombre);
	}
	
}
