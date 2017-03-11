package es.urjc.code.daw;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.tag.TagRepository;
import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.comentario.ComentarioRepository;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.vineta.*;

@RestController
public class CMControler {
	
	interface VinetaView extends Vineta.BasicAtt, Vineta.UserAtt, User.BasicAtt, Vineta.TagAtt, Tag.BasicAtt, Vineta.ComentariosAtt, Comentario.BasicAtt, Comentario.UserAtt{}
	interface UserView extends User.BasicAtt, User.VinetaAtt, User.ComentarioAtt, Comentario.BasicAtt, Vineta.BasicAtt{}
	interface ComentarioView extends Comentario.BasicAtt, Comentario.UserAtt, User.BasicAtt, Comentario.VinetaAtt, Vineta.BasicAtt{}
	interface TagView extends Tag.BasicAtt {}
	
	@Autowired
	private UserRepository userrepository;

	@Autowired
	private ComentarioRepository comentariorepository;
	
	@Autowired
	private VinetaRepository vinetarepository;
	
	@Autowired
	private TagRepository tagrepository;
	

	@PostConstruct
	public void init(){
		User usuario1 = new User("joaquin", "joa", "cuantomeme1@gmail.com");
		User usuario2 = new User("Raul", "raul94", "cuantomeme1@gmail.com");
		
		Vineta v1 = new Vineta("vineta1", "des1", "http://runt-of-the-web.com/wordpress/wp-content/uploads/2012/05/funnest-troll-dad-rage-comics-computers.gif");
		v1.setAutor(usuario1);
		this.userrepository.save(usuario1);
		this.vinetarepository.save(v1);
		
		Vineta v2 = new Vineta("vineta2", "des2", "http://www.leragecomics.com/wp-content/uploads/2011/04/VzxVF-640x546.png");
		
		v1.setAutor(usuario1);
		v2.setAutor(usuario2);
		
		this.userrepository.save(usuario1);
		this.userrepository.save(usuario2);
		this.vinetarepository.save(v1);
		this.vinetarepository.save(v2);
		
		Tag t1 = new Tag("Lol");
		Tag t2 = new Tag("Lol2");
		Tag t3 = new Tag("Lol3");
		
		this.tagrepository.save(t1);
		this.tagrepository.save(t2);
		this.tagrepository.save(t3);
		
		v1.setTags(t1);
		v1.setTags(t2);
		v2.setTags(t1);
		v2.setTags(t3);
		
		this.vinetarepository.save(v1);
		this.vinetarepository.save(v2);
		
		Comentario c1 = new Comentario("mi primer comentario");
		c1.setAutor_comentario(usuario1);
		c1.setVineta(v1);
		this.comentariorepository.save(c1);
	}

	
	@JsonView(VinetaView.class)
	@RequestMapping("/api/vinetas/")
	public Page<Vineta> getvinetas(Pageable page){
		return this.vinetarepository.findAllByOrderByCreationdateDesc(page);
	}
	@JsonView(VinetaView.class)
	@RequestMapping("/api/vinetas2/")
	public List<Vineta> getvinetas(){
		return this.vinetarepository.findAll();
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
	
	@JsonView(ComentarioView.class)
	@RequestMapping("/api/comentarios/")
	public List<Comentario> getcomentarios(){
		return this.comentariorepository.findAll();
	}
	
	@JsonView(TagView.class)
	@RequestMapping("/api/tags/")
	public List<Tag> getTags(){
		return this.tagrepository.findAll();
	}
	
	@JsonView(TagView.class)
	@RequestMapping("/api/tags/{nombre}")
	public Tag getTagsByName(@PathVariable String nombre){
		return this.tagrepository.findByNombre(nombre);
	}
}
