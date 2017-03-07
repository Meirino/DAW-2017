package es.urjc.code.daw;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.comentario.ComentarioRepository;
import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.tag.TagRepository;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserRepository;
import es.urjc.code.daw.vineta.Vineta;
import es.urjc.code.daw.vineta.VinetaRepository;

@Controller
public class UserController {

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
		
		User usuario3 = new User("pepe", "pepito", "cuantomeme3@gmail.com");
		User usuario4 = new User("jose", "josito", "cuantomeme4@gmail.com");
		
		Vineta v3 = new Vineta("vineta3", "des3", "http://i2.kym-cdn.com/photos/images/facebook/000/125/918/RMUBQ.png");
		Vineta v4 = new Vineta("vineta4", "des4", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465");
		
		v3.setAutor(usuario3);
		v4.setAutor(usuario4);
		
		this.userrepository.save(usuario3);
		this.userrepository.save(usuario4);
		this.vinetarepository.save(v3);
		this.vinetarepository.save(v4);
		
		Tag t4 = new Tag("Trolldad");
		Tag t5 = new Tag("Inglip");
		Tag t6 = new Tag("Yaoming");
		
		this.tagrepository.save(t4);
		this.tagrepository.save(t5);
		this.tagrepository.save(t6);
		
		v3.getTags().add(t4);
		v3.getTags().add(t5);
		v4.getTags().add(t4);
		v4.getTags().add(t6);
		
		this.vinetarepository.save(v3);
		this.vinetarepository.save(v4);
		
		Comentario c2 = new Comentario("pole");
		c2.setAutor_comentario(usuario3);
		c2.setVineta(v3);
		this.comentariorepository.save(c2);
	}
	/*--------------------------Autenticacion--------------------------*/
	@RequestMapping("/login")
	public String login() {		
		return "login";
	}
	
	@RequestMapping("/loginerror")
	public String loginError() {		
		return "loginerror";
	}
	@RequestMapping("/logout")
	public String logout() {		
		return "index";
	}
	
	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	public String login(Model model, HttpSession sesion, @RequestParam String username, User usuario) {		
		this.userrepository.save(usuario);
		sesion.setAttribute("user", usuario);
		model.addAttribute("user", sesion.getAttribute("user"));
		return "user_test";
		
	}
	
	/*------------------Comentarios-------------------------*/
	@RequestMapping(value = "/crearComentario/vineta/{id}", method = RequestMethod.POST)
	public String crearComentario(Model model, HttpSession sesion,@PathVariable long id, @RequestParam String comentario) {
		System.out.println("he entrado a crear un comentario");
		if (sesion.getAttribute("user")!= null){
			System.out.println("estas loggeado");
			Comentario comen = new Comentario(comentario);
			Vineta vineta = this.vinetarepository.findOne(id);
			comen.setAutor_comentario((User)sesion.getAttribute("user"));
			comen.setVineta(vineta);
			this.comentariorepository.save(comen);
			
		}else{
			return "usuario no registrado"; //Cambiar a una pagina que diga que el usuario no esta registrado
		}
		model.addAttribute("vinetas", this.vinetarepository.findAllByOrderByCreationdateDesc());
		return "index";
	}
	
	/*------------------Vinetas-------------------------*/
	@RequestMapping("/vineta/{id}")
	public String detalles(Model model, @PathVariable long id) {
		model.addAttribute("vineta", this.vinetarepository.findOne((long) id));
		return "detalles";
	}
	
	@RequestMapping(value = "/likevineta/{id}")
	public String likeVineta(Model model, @PathVariable long id) {
		System.out.println("llego a lie vineta con el id "+ id);
		Vineta vineta = this.vinetarepository.findOne(id);
		vineta.like();
		this.vinetarepository.save(vineta);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/dislikevineta/{id}")
	public String dislikeVineta(Model model, @PathVariable long id) {
		Vineta vineta = this.vinetarepository.findOne(id);
		vineta.dislike();
		this.vinetarepository.save(vineta);
		return "redirect:/";
	}
	
	
	
	/*----------------------------s-------------------------*/
	
	@RequestMapping("/")
	public String viñetas(Model model) {
		model.addAttribute("vinetas", this.vinetarepository.findAllByOrderByCreationdateDesc());
		return "index";
	}

	
	@RequestMapping("/tag/{nombre}")
	public String detalles(Model model, @PathVariable String nombre) {
		model.addAttribute("tag",this.tagrepository.findByNombre(nombre));
		model.addAttribute("lista", this.tagrepository.findByNombre(nombre).getVinetas());
		return "tagIndex";
	}
	
	@RequestMapping("/perfil/{id}")
	public String perfil(Model model, @PathVariable long id) {
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName();
	      if (name != "anonymousUser") {
	    		  User usuario = this.userrepository.findByUsername(name);
	    		  if (usuario.getId() == id){
	    			  model.addAttribute("owner_profile", true);
	    		  }else{
	    			  model.addAttribute("owner_profile", false);
	    			  model.addAttribute("usuario", usuario);
	    		  }
	      }else{
	    	  model.addAttribute("owner_profile", false);
	      }
		return "perfil";
	}
	
	@RequestMapping("/vinetas")
	public String vinetas(Model model) {
		model.addAttribute("vinetas", this.vinetarepository.findAll());
		return "index";
	}

}
