package es.urjc.code.daw;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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
import es.urjc.code.daw.user.*;

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
	
	@Autowired
	private UserComponent userComponent;
	
	private List<User> users_generated = new ArrayList<>();
	private List<Vineta> vinetas_generated = new ArrayList<>();
	private List<Tag> tags_generated = new ArrayList<>();
	private List<Comentario> comentarios_generated = new ArrayList<>();
	
	static final int NUSERS = 10;
	static final int NTAGS = 10;
	static final int NVINETAS = 40;
	static final int NCOMENTARIOS = 30;
	@PostConstruct
	public void init(){
		for(int i= 0; i<NUSERS; i++){
			User u = new User("usuario_"+i, "usuario_"+i, "cuantomeme"+i+"@gmail.com", "ROLE_USER");
			this.users_generated.add(u);
			//this.userrepository.save(u);
		}
		for(int i= 0; i<NVINETAS; i++){
			Vineta v = new Vineta("vineta"+i, "des"+i, "http://runt-of-the-web.com/wordpress/wp-content/uploads/2012/05/funnest-troll-dad-rage-comics-computers.gif");
			this.vinetas_generated.add(v);
			//this.vinetarepository.save(v);
		}
		for(int i= 0; i<NTAGS; i++){
			Tag t = new Tag("Lol"+i);
			this.tags_generated.add(t);
			//this.tagrepository.save(t);
		}
		for(int i= 0; i<NCOMENTARIOS; i++){
			Comentario c = new Comentario("El comentario "+i);
			this.comentarios_generated.add(c);
			//this.comentariorepository.save(c);
		}
		//Metiendo likes de forma aleatoria
		for (Vineta v: this.vinetas_generated.subList(0, 19)){
			/*Forma de guardar un many to many.
			1. Se fuarda el secundario
			2. Al primario se añade el secundario
			3. Se guarda el primario
			*/
			this.vinetarepository.save(v);
			int id_user = (int) (Math.random() * NUSERS);
			User user = this.users_generated.get(id_user);
			user.getVinetas_gustadas().add(v);
			this.userrepository.save(user);
			
		}
		/*Mismo metodo para dislike. Hay que ver porque mete mas de la cuenta, ya que en total
		 * vinetasodiadas+ vinetasgustadas debe ser 40*/
		/*
		for (Vineta v: this.vinetas_generated.subList(19, NVINETAS)){
			
			this.vinetarepository.save(v);
			int id_user = (int) (Math.random() * NUSERS);
			User user = this.users_generated.get(id_user);
			user.getVinetas_odiadas().add(v);
			this.userrepository.save(user);
			
		}*/
		/*
		User usuario3 = new User("pepe", "pepito", "cuantomeme3@gmail.com", "ROLE_USER");
		User usuario4 = new User("jose", "josito", "cuantomeme4@gmail.com", "ROLE_USER");
		
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
		this.comentariorepository.save(c2);*/
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
	@RequestMapping("/signup")
	public String signup() {		
		return "signup";
	}
	@RequestMapping("/home")
	public String profile(Model model, HttpServletRequest request) {
		Principal p = request.getUserPrincipal();
    	User user = userrepository.findByUsername(p.getName());
    	System.out.println(userComponent.isLoggedUser());
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		//model.addAttribute("usuario_logged", user);    
		model.addAttribute("usuario", user);
		return "perfil";
	}
	
	@RequestMapping(value = "/signupuser", method = RequestMethod.POST)
	public String login(Model model, HttpSession sesion, @RequestParam String username, @RequestParam String password, @RequestParam String email ) {
		User usuario = new User(username, password, email,"ROLE_USER" );
		this.userrepository.save(usuario);
		return "redirect:/";		
	}

	@RequestMapping("/perfil/{id}")
	public String perfil(Model model, @PathVariable long id, HttpServletRequest request) {
		  User usuario = this.userrepository.findOne(id);
		  model.addAttribute("usuario", usuario);
		  model.addAttribute("anonymous", !userComponent.isLoggedUser());
		  /*
		  if (userComponent.isLoggedUser()){
			  Principal p = request.getUserPrincipal();
		      User user = userrepository.findByUsername(p.getName());
		      model.addAttribute("usuario_logged", user);
		  }else{
			  model.addAttribute("usuario_logged", new User("por defecto", "pass", "asd", "Role_User"));
		  }
		   */
		  
		return "perfil";
	}
	/*------------------Comentarios-------------------------*/
	@RequestMapping(value = "/crearComentario/vineta/{id}", method = RequestMethod.POST)
	public String crearComentario(Model model, HttpSession sesion,@PathVariable long id, @RequestParam String comentario, HttpServletRequest request ) {
		System.out.println("he entrado a crear un comentario");
		if (userComponent.isLoggedUser()){
			  Principal p = request.getUserPrincipal();
		      User user = userrepository.findByUsername(p.getName());
		      Comentario c = new Comentario(comentario);
		      c.setAutor_comentario(user);
		      c.setVineta(this.vinetarepository.findOne(id));
		      this.comentariorepository.save(c);
		      return "redirect:/vineta/"+id;
			
		}else{
			return "redirect:/login";
		}

	}
	
	/*------------------Vinetas-------------------------*/
	@RequestMapping("/vineta/{id}")
	public String detalles(Model model, @PathVariable long id, HttpServletRequest request) {
		model.addAttribute("vineta", this.vinetarepository.findOne((long) id));
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userrepository.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
		}
		return "detalles";
	}
	
	@RequestMapping(value = "/likevineta/{id}")
	public String likeVineta(Model model, @PathVariable long id) {
		Vineta vineta = this.vinetarepository.findOne(id);
		vineta.like();
		this.vinetarepository.save(vineta);
		return "redirect:/vineta/"+id;
	}
	
	@RequestMapping(value = "/dislikevineta/{id}")
	public String dislikeVineta(Model model, @PathVariable long id) {
		Vineta vineta = this.vinetarepository.findOne(id);
		vineta.dislike();
		this.vinetarepository.save(vineta);
		return "redirect:/vineta/"+id;
	}
	
	
	
	/*----------------------------s-------------------------*/
	
	@RequestMapping("/")
	public String viñetas(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userrepository.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
		}
		model.addAttribute("vinetas", this.vinetarepository.findAll());
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		return "index";
	}

	
	@RequestMapping("/tag/{nombre}")
	public String detalles(Model model, @PathVariable String nombre, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userrepository.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
		}
		model.addAttribute("tag",this.tagrepository.findByNombre(nombre));
		model.addAttribute("lista", this.tagrepository.findByNombre(nombre).getVinetas());
		return "tagIndex";
	}
	

	
	
	@RequestMapping("/vinetas")
	public String vinetas(Model model) {
		model.addAttribute("vinetas", this.vinetarepository.findAll());
		return "index";
	}

}
