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
		
		User usuario1 = new User("joaquin", "joa", "cuantomeme1@gmail.com", "ROLE_ADMIN");
		User usuario2 = new User("paco", "paquito", "cuantomeme2@gmail.com", "ROLE_ADMIN");
		
		Vineta v1 = new Vineta("bbvineta1", "des1", "http://runt-of-the-web.com/wordpress/wp-content/uploads/2012/05/funnest-troll-dad-rage-comics-computers.gif");
		Vineta v2 = new Vineta("aavineta2", "des2", "http://www.leragecomics.com/wp-content/uploads/2011/04/VzxVF-640x546.png");
		
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
		
		Comentario c1 = new Comentario("mi primer comentario");
		c1.setAutor_comentario(usuario1);
		c1.setVineta(v1);
		this.comentariorepository.save(c1);
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
