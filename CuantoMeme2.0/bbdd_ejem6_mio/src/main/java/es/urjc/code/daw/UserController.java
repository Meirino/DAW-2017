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
	
	
	@PostConstruct
	public void init(){
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
		
		v3.setTags(t4);
		v3.setTags(t5);
		v4.setTags(t4);
		v4.setTags(t6);
		
		this.vinetarepository.save(v3);
		this.vinetarepository.save(v4);
		
		Comentario c2 = new Comentario("pole");
		c2.setAutor_comentario(usuario3);
		c2.setVineta(v3);
		this.comentariorepository.save(c2);
	}
	/*--------------------------Autenticacion--------------------------*/
	public String requestCurrentPage(HttpServletRequest request){
	    String referrer = request.getHeader("Referer");
	    if(referrer!=null){
	        request.getSession().setAttribute("url_prior_login", referrer);
	    }
	    System.out.println(referrer);
	    return referrer;
	}
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {	
		this.requestCurrentPage(request);
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
	public String home(Model model, HttpServletRequest request) {
		//Sistema de ""Recomendación""
		//Creo un número aleatorio entre 0 y el número de viñetas que existen
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(this.vinetarepository.findAll().size())+1;

		Principal p = request.getUserPrincipal();
    	User user = userrepository.findByUsername(p.getName());
    	System.out.println(userComponent.isLoggedUser());
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		//model.addAttribute("usuario_logged", user);    
		model.addAttribute("usuario", user);
		model.addAttribute("owner",true);
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		model.addAttribute("recomendados", this.vinetarepository.findOne((long) randomInt));
		return "perfil";
	}
	@RequestMapping("/misfavoritos")
	public String misfavoritos(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "Tus viñetas mas favoritas!");
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userrepository.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
			model.addAttribute("vinetas", usuario.getVinetas_favoritas() );
			model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		}
		else{
			return "redirect:/login";
		}

		return "index";
	}
	
	@RequestMapping("/mislikes")
	public String mislikes(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "Las vinetas que mas te gustan");
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userrepository.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
			model.addAttribute("vinetas", usuario.getVinetas_gustadas());
			model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		}
		else{
			return "redirect:/login";
		}
		return "index";
	}
	@RequestMapping("/misdislikes")
	public String misdislikes(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "Las vinetas que mas odias");
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userrepository.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
			model.addAttribute("vinetas", usuario.getVinetas_odiadas());
			model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		}
		else{
			return "redirect:/login";
		}
		return "index";
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
		  model.addAttribute("owner",false);
		  model.addAttribute("tags_mas_usados", this.tagrepository.findAll());

		return "perfil";
	}
	/*------------------Comentarios--------------------------*/
	@RequestMapping(value = "/crearComentario/vineta/{id}", method = RequestMethod.POST)
	public String crearComentario(Model model, HttpSession sesion,@PathVariable long id, @RequestParam String comentario, HttpServletRequest request ) {
		System.out.println("he entrado a crear un comentario");
		String page = this.requestCurrentPage(request);
		if (userComponent.isLoggedUser()){
			  Principal p = request.getUserPrincipal();
		      User user = userrepository.findByUsername(p.getName());
		      Comentario c = new Comentario(comentario);
		      c.setAutor_comentario(user);
		      c.setVineta(this.vinetarepository.findOne(id));
		      this.comentariorepository.save(c);
		      return "redirect:"+page;
			
		}else{
			return "redirect:/login";
		}

	}
	
	/*------------------Vinetas-------------------------*/
	@RequestMapping("/vineta/{id}")
	public String detalles(Model model, @PathVariable long id) {
		model.addAttribute("vineta", this.vinetarepository.findOne((long) id));
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());

		return "detalles";
	}
	
	@RequestMapping(value = "/likevineta/{id}")
	public String likeVineta(Model model, @PathVariable long id, HttpServletRequest request ) {
		String page = this.requestCurrentPage(request);
		boolean is_liked_before = false;
		if (userComponent.isLoggedUser()){
			  Principal p = request.getUserPrincipal();
		      User user = userrepository.findByUsername(p.getName());
		      Vineta v = vinetarepository.findOne(id);
		      
		      for(User u2:v.getUsers_likes()){
		    	  is_liked_before = (u2.getId()==user.getId());
		    	  if (is_liked_before){
		    		  break;
		    	  }
		      }
		      if(!is_liked_before){
			      user.getVinetas_gustadas().add(v);
			      v.like();
			      this.vinetarepository.save(v);
			      this.userrepository.save(user);
		      }

		      return "redirect:"+page;
			
		}else{
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/dislikevineta/{id}")
	public String dislikeVineta(Model model, @PathVariable long id, HttpServletRequest request) {
		String page = this.requestCurrentPage(request);
		boolean is_disliked_before = false;
		if (userComponent.isLoggedUser()){
			  Principal p = request.getUserPrincipal();
		      User user = userrepository.findByUsername(p.getName());
		      Vineta v = vinetarepository.findOne(id);
		      
		      for(User u2:v.getUsers_dislikes()){
		    	  is_disliked_before = (u2.getId()==user.getId());
		    	  if (is_disliked_before){
		    		  break;
		    	  }
		      }
		      if(!is_disliked_before){
			      user.getVinetas_odiadas().add(v);
			      v.dislike();
			      this.vinetarepository.save(v);
			      this.userrepository.save(user);
		      }

		      return "redirect:"+page;
			
		}else{
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/hacerfavorita/{id}")
	public String hacerfavorita(Model model, @PathVariable long id, HttpServletRequest request) {
		String page = this.requestCurrentPage(request);
		boolean is_favorited_before = false;
		if (userComponent.isLoggedUser()){
			  Principal p = request.getUserPrincipal();
		      User user = userrepository.findByUsername(p.getName());
		      Vineta v = vinetarepository.findOne(id);
		      for(User u2:v.getUsers_fav()){
		    	  is_favorited_before = (u2.getId()==user.getId());
		    	  if (is_favorited_before){
		    		  break;
		    	  }
		      }
		      if(!is_favorited_before){
			      user.getVinetas_favoritas().add(v);
			      this.vinetarepository.save(v);
			      this.userrepository.save(user);
		      }

		      return "redirect:"+page;
			
		}else{
			return "redirect:/login";
		}
	}
	
	
	/*----------------------------s-------------------------*/
	
	@RequestMapping("/")
	public String viñetas(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "¡Bienvenido a CuantoMeme!");
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
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());

		return "tagIndex";
	}
	
	
	@RequestMapping("/vinetas")
	public String vinetas(Model model) {
		model.addAttribute("vinetas", this.vinetarepository.findAll());
		return "index";
	}

	
	@RequestMapping(value = "/busqueda")
	 public String search(@RequestParam("nombre") String texto, @RequestParam("modo") String modo, Model model) {
	  model.addAttribute("txt",texto);
	  model.addAttribute("modo", modo);
	  if(modo.equals("titulo")) {
		  System.out.println("Me coge el título");
		  model.addAttribute("lista",this.vinetarepository.findByTitulo(texto));
	  }
	  if(modo.equals("autor")) {
		  System.out.println("Me coge el autor");
		  model.addAttribute("lista",this.userrepository.findByUsername(texto).getVinetas_subidas());
	  }
	  if(modo.equals("tag")) {
		  System.out.println("Me coge el tag");
		  model.addAttribute("lista",this.tagrepository.findByNombre(texto).getVinetas());
	  }
	  model.addAttribute("resultados",this.vinetarepository.findByTitulo(texto).size());
	  return "busqueda";
	 }
}
