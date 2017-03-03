package es.urjc.code.daw;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.comentario.ComentarioRepository;
import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.tag.TagRepository;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserRepository;
import es.urjc.code.daw.vineta.Vineta;
import es.urjc.code.daw.vineta.VinetaRepository;

@Controller
public class CMController {

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
		
		User usuario1 = new User("joaquin", "joa");
		User usuario2 = new User("paco", "paquito");
		
		Vineta v1 = new Vineta("vineta1", "des1");
		Vineta v2 = new Vineta("vineta2", "des2");
		
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
		
		v1.getTags().add(t1);
		v1.getTags().add(t2);
		v2.getTags().add(t1);
		v2.getTags().add(t3);
		
		this.vinetarepository.save(v1);
		this.vinetarepository.save(v2);
		
		Comentario c1 = new Comentario("12/12/2015", "mi primer comentario");
		c1.setAutor_comentario(usuario1);
		c1.setVineta(v1);
		this.comentariorepository.save(c1);
		/*
		
		Tag t1 = new Tag("Lol");
		Tag t2 = new Tag("Lol2");
		Tag t3 = new Tag("Lol3");
		
		Comentario c1 = new Comentario("12/12/2015", "mi primer comentario");
		

		
		this.tagrepository.save(t1);
		this.tagrepository.save(t2);
		this.tagrepository.save(t3);
		
		v1.getTags().add(t1);
		v1.getTags().add(t2);
		v2.getTags().add(t1);
		v2.getTags().add(t3);
		
		v1.setAutor(usuario1);
		v2.setAutor(usuario1);
		
		c1.setAutor_comentario(usuario1);
		c1.setVineta(v1);
		
		
		this.comentariorepository.save(c1);
		this.vinetarepository.save(v1);
		this.vinetarepository.save(v2);

*/

	}
	
	@RequestMapping("/")
	public String viñetas(Model model) {
		model.addAttribute("vinetas", this.vinetarepository.findAll());
		return "index";
	}
	
	@RequestMapping("/vineta/{id}")
	public String detalles(Model model, @PathVariable int id) {
		model.addAttribute("vineta", this.vinetarepository.findOne((long) id));
		return "detalles";
	}
	
	@RequestMapping("/tag/{nombre}")
	public String detalles(Model model, @PathVariable String nombre) {
		model.addAttribute("tag",this.tagrepository.findByNombre(nombre));
		model.addAttribute("lista", this.tagrepository.findByNombre(nombre).getVinetas());
		return "tagIndex";
	}
}
