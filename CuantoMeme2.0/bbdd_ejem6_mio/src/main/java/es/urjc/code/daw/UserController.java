package es.urjc.code.daw;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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

	
	
	@PostConstruct
	public void init(){
		User usuario3 = new User("pepe", "pepito", "cuantomeme3@gmail.com", "ROLE_USER");
		User usuario4 = new User("jose", "josito", "cuantomeme4@gmail.com", "ROLE_USER");
		User admin = new User("admin", "admin", "admin@gmail.com","ROLE_USER", "ROLE_ADMIN");

		Vineta v3 = new Vineta("vineta3", "des3", "http://i2.kym-cdn.com/photos/images/facebook/000/125/918/RMUBQ.png");
		Vineta v4 = new Vineta("vineta4", "des4", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465");
		
		v3.setAutor(usuario3);
		v4.setAutor(usuario4);
		
		this.userrepository.save(usuario3);
		this.userrepository.save(usuario4);
		this.userrepository.save(admin);
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

	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request) {	
		this.requestCurrentPage(request);
		model.addAttribute("error", false);
		return "login";
	}
	
	@RequestMapping("/loginerror")
	public String loginError(Model model) {
		model.addAttribute("error", true);
		return "login";
	}
	@RequestMapping("/logout")
	public String logout() {		
		return "index";
	}
	@RequestMapping("/signup")
	public String signup() {		
		return "signup";
	}
	
	@RequestMapping(value = "/signupuser", method = RequestMethod.POST)
	public String login(Model model, HttpSession sesion, @RequestParam String username, @RequestParam String password, @RequestParam String email ) {
		User usuario = new User(username, password, email,"ROLE_USER" );
		this.userrepository.save(usuario);
		return "redirect:/";		
	}

	/*------------------------Usuario----------------------------------*/
	public String requestCurrentPage(HttpServletRequest request){
	    String referrer = request.getHeader("Referer");
	    if(referrer!=null){
	        request.getSession().setAttribute("url_prior_login", referrer);
	    }
	    return referrer;
	}
	
	@RequestMapping(value = "/home")
	public String home(Model model, HttpServletRequest request) {
		//Sistema de ""Recomendación""
		//Creo un número aleatorio entre 0 y el número de viñetas que existen
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(this.vinetarepository.findAll().size())+1;

		Principal p = request.getUserPrincipal();
    	User user = userrepository.findByUsername(p.getName());
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		//model.addAttribute("usuario_logged", user);    
		model.addAttribute("usuario", user);
		model.addAttribute("owner",true);
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		model.addAttribute("recomendados", this.vinetarepository.findOne((long) randomInt));
		model.addAttribute("seguidos",user.getFollowing());
		model.addAttribute("seguidores", user.getFollowers());
		return "perfil";
	}
	@RequestMapping(value = "/misfavoritos")
	public String misfavoritos(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "Tus viñetas mas favoritas!");
		Principal p = request.getUserPrincipal();
    	User usuario = userrepository.findByUsername(p.getName());
		model.addAttribute("usuario", usuario);
		model.addAttribute("vinetas", usuario.getVinetas_favoritas() );
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
		System.out.println("hola 1");
		return "misvinetas-social";

	}
	
	@RequestMapping(value = "/mislikes")
	public String mislikes(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "Las vinetas que mas te gustan");
		Principal p = request.getUserPrincipal();
    	User usuario = userrepository.findByUsername(p.getName());
		model.addAttribute("usuario", usuario);
		model.addAttribute("vinetas", usuario.getVinetas_gustadas());
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
		return "misvinetas-social";
	}
	
	@RequestMapping(value = "/misdislikes")
	public String misdislikes(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "Las vinetas que mas odias");
		Principal p = request.getUserPrincipal();
    	User usuario = userrepository.findByUsername(p.getName());
		model.addAttribute("usuario", usuario);
		model.addAttribute("vinetas", usuario.getVinetas_odiadas());
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
		return "misvinetas-social";
	}
	

	@RequestMapping(value = "/perfil/{id}")
	public String perfil(Model model, @PathVariable long id, HttpServletRequest request) {
		  boolean isfollowed = false;
			if (userComponent.isLoggedUser()){
				User user_tofollow = this.userrepository.findOne(id);
				Principal p = request.getUserPrincipal();
			    User current_user = userrepository.findByUsername(p.getName());
			    isfollowed = current_user.ifollow(user_tofollow);
			}
		  User usuario = this.userrepository.findOne(id);
		  System.out.println(usuario.getUsername());
		  model.addAttribute("owner",false);//Si es admin, le  tratamos como si fuera dueño del perfil
		  model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
		  model.addAttribute("usuario", usuario);
		  model.addAttribute("anonymous", !userComponent.isLoggedUser());
		  model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		  model.addAttribute("isfollowed", isfollowed);
		  model.addAttribute("seguidos",usuario.getFollowing());
			model.addAttribute("seguidores", usuario.getFollowers());
		return "perfil";
	}
	@RequestMapping(value = "/seguirperfil/{id}")
	public String seguirPerfil(Model model, @PathVariable long id, HttpServletRequest request) {		  
		  User user_tofollow = this.userrepository.findOne(id);
		  Principal p = request.getUserPrincipal();
	      User current_user = userrepository.findByUsername(p.getName());
	      current_user.addFollowing(user_tofollow);
	      this.userrepository.save(current_user);
		  model.addAttribute("owner",false);
		  model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
	      model.addAttribute("usuario", current_user);
		  model.addAttribute("anonymous", !userComponent.isLoggedUser());
		  model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		  model.addAttribute("isfollowed", current_user.ifollow(user_tofollow));
	      String page = this.requestCurrentPage(request);  
	      return "redirect:"+page;
	}
	@RequestMapping(value = "/dejarseguirperfil/{id}")
	public String dejarseguirPerfil(Model model, @PathVariable long id, HttpServletRequest request) {
		  User user_tounfollow = this.userrepository.findOne(id);
		  Principal p = request.getUserPrincipal();
	      User current_user = userrepository.findByUsername(p.getName());
	      current_user.getFollowing().remove(user_tounfollow);
	      this.userrepository.save(current_user);
		  model.addAttribute("owner",false);
		  model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
	      model.addAttribute("usuario", current_user);
		  model.addAttribute("anonymous", !userComponent.isLoggedUser());
		  model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		  model.addAttribute("isfollowed", current_user.ifollow(user_tounfollow));
	      String page = this.requestCurrentPage(request);  
	      return "redirect:"+page;
	}
	
	@RequestMapping(value = "/eliminarperfil/{id}", method = RequestMethod.POST)
	public String eliminarPerfil(Model model, @PathVariable long id, HttpServletRequest request) {
		boolean isAdmin = request.isUserInRole("ROLE_ADMIN");
		if (isAdmin){
		  this.userrepository.delete(id);}
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "¡Bienvenido a CuantoMeme!");
		model.addAttribute("admin", isAdmin);
		model.addAttribute("vinetas", this.vinetarepository.findAllByOrderByCreationdateDesc());
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		return "redirect:/";
	}
	
	
	/*------------------Comentarios--------------------------*/
	@RequestMapping(value = "/crearComentario/vineta/{id}", method = RequestMethod.POST)
	public String crearComentario(Model model, HttpSession sesion,@PathVariable long id, @RequestParam String comentario, HttpServletRequest request ) {
		  String page = this.requestCurrentPage(request);  
		  Principal p = request.getUserPrincipal();
	      User user = userrepository.findByUsername(p.getName());
	      Comentario c = new Comentario(comentario);
	      c.setAutor_comentario(user);
	      c.setVineta(this.vinetarepository.findOne(id));
	      this.comentariorepository.save(c);
	      return "redirect:"+page;

	}
	@RequestMapping(value = "/eliminarcomentario/{id}")
	public String eliminarComentario(Model model, @PathVariable long id, HttpServletRequest request ) {
		  Principal p = request.getUserPrincipal();
	      User user = userrepository.findByUsername(p.getName());
	      Comentario c = comentariorepository.findOne(id);
	      if(c.getAutor_comentario().getId() == user.getId()){
	    	  comentariorepository.delete(id);
	      }
	      String page = this.requestCurrentPage(request);  
	      return "redirect:"+page;
	}
	
	/*------------------Vinetas-------------------------*/
	@RequestMapping(value = "/vinetas")
	public String vinetas(Model model, HttpServletRequest request) {
		boolean isAdmin = false;
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "¡Bienvenido a CuantoMeme!");
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userrepository.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
			isAdmin = request.isUserInRole("ROLE_ADMIN");
		}
		model.addAttribute("admin", isAdmin);
		model.addAttribute("vinetas", this.vinetarepository.findAllByOrderByCreationdateDesc());
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		return "index";
	}
	
	@RequestMapping("/vineta/{id}")
	public String detalles(Model model, @PathVariable long id, HttpServletRequest request) {
		boolean isfollowed = false;
		boolean owner = false;
		Vineta v = this.vinetarepository.findOne((long) id);
		if (userComponent.isLoggedUser()){
			User user_tofollow = this.userrepository.findOne(id);
			Principal p = request.getUserPrincipal();
		    User current_user = userrepository.findByUsername(p.getName());
		    isfollowed = current_user.ifollow(user_tofollow);
		    owner = (v.getAutor().getId() == current_user.getId()) || request.isUserInRole("ROLE_ADMIN");
		}

		model.addAttribute("isfollowed", isfollowed);
		model.addAttribute("admin", owner);
	    model.addAttribute("usuariologged", userComponent.isLoggedUser());
		model.addAttribute("vineta", v);
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());

		return "detalles";
	}
	
	@RequestMapping(value = "/likevineta/{id}")
	public String likeVineta(Model model, @PathVariable long id, HttpServletRequest request ) {
		  String page = this.requestCurrentPage(request);
		  boolean is_liked_before = false;
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
	}
	@RequestMapping(value = "/eliminarvineta/{id}")
	public String eliminarVineta(Model model, @PathVariable long id, HttpServletRequest request ) {
		  Principal p = request.getUserPrincipal();
	      User user = userrepository.findByUsername(p.getName());
	      Vineta v = vinetarepository.findOne(id);
	      /*
	      for(User u:v.getUsers_likes()){
	    	  System.out.println("Esta vineta le gusta a "+u.getUsername());
	    	  u.getVinetas_gustadas().remove(v);
	    	  userrepository.save(u);
	      }*/
	      if((v.getAutor().getId() == user.getId()) || request.isUserInRole("ROLE_ADMIN") ){
	    	  vinetarepository.delete(id);
	      }
	      //String page = this.requestCurrentPage(request);  
	      return "redirect:/";
	}
	
	@RequestMapping(value = "/dislikevineta/{id}")
	public String dislikeVineta(Model model, @PathVariable long id, HttpServletRequest request) {
		String page = this.requestCurrentPage(request);
		boolean is_disliked_before = false;
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
	}
	
	@RequestMapping(value = "/hacerfavorita/{id}")
	public String hacerfavorita(Model model, @PathVariable long id, HttpServletRequest request) {
		String page = this.requestCurrentPage(request);
		boolean is_favorited_before = false;
		Principal p = request.getUserPrincipal();
	    User user = userrepository.findByUsername(p.getName());
	    Vineta v = vinetarepository.findOne(id);
	    for(User u2:v.getUsers_fav()){
	  	  is_favorited_before = (u2.getId()==user.getId());
	   	  if (is_favorited_before){
	   		  break;
    	}	      }
	    if(!is_favorited_before){
	      user.getVinetas_favoritas().add(v);
	      this.vinetarepository.save(v);
	      this.userrepository.save(user);
	    }
	    return "redirect:"+page;
	}
	
	/*----------------------------Tags-------------------------*/
	
	@RequestMapping(value = "/tag/{nombre}")
	public String detalles(Model model, @PathVariable String nombre, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userrepository.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
		}
		model.addAttribute("tag",this.tagrepository.findByNombre(nombre));
		model.addAttribute("lista", this.tagrepository.findByNombre(nombre).get(0).getVinetas());
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());

		return "tagIndex";
	}
	/*----------------------------Global-------------------------*/
	@RequestMapping(value = "/")
	public String viñetas(Model model, HttpServletRequest request) {
		boolean isAdmin = false;
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "¡Bienvenido a CuantoMeme!");
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userrepository.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
			isAdmin = request.isUserInRole("ROLE_ADMIN");
		}
		model.addAttribute("admin", isAdmin);
		model.addAttribute("vinetas", this.vinetarepository.findAllByOrderByCreationdateDesc());
		model.addAttribute("tags_mas_usados", this.tagrepository.findAll());
		return "index";
	}
	
	  @RequestMapping(value = "/busqueda")
	  public String search(@RequestParam("nombre") String texto, @RequestParam("modo") String modo, Model model, HttpServletRequest request) {
	   model.addAttribute("mensaje", "Resultado para tu busqueda: "+modo+" igual a "+texto);
	   if(modo.equals("titulo")) {
	    model.addAttribute("vinetas",this.vinetarepository.findByTitulo(texto));
	   }
	   if(modo.equals("autor")) {
		   User usuario = this.userrepository.findByUsername(texto);
		   if (usuario == null){
			   model.addAttribute("vinetas", null);
		   }else{
			    model.addAttribute("vinetas",usuario.getVinetas_subidas());

		   }
	   }
	   if(modo.equals("tag")) {
		   Tag tag = this.tagrepository.findByNombre(texto).get(0);
		   if (tag == null){
			   model.addAttribute("vinetas", null);
		   }else{
			    model.addAttribute("vinetas",tag.getVinetas());
		   }
	   }
	   model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
	   model.addAttribute("tags_mas_usados", this.tagrepository.findAll());

	   return "resultadobusqueda";
	  }
	  


}
