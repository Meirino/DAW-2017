package es.urjc.code.daw;

import java.security.Principal;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.code.daw.comentario.*;
import es.urjc.code.daw.tag.*;
import es.urjc.code.daw.vineta.*;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.utils.utils;

@Controller
public class UserController {

	@Autowired
	private UserService userservice;

	@Autowired
	private ComentarioService comentarioservice;
	
	@Autowired
	private VinetaService vinetaservice;
	
	@Autowired
	private TagService tagservice;
	
	@Autowired
	private UserComponent userComponent;

	@Autowired
	private utils utilservice;
	
	@PostConstruct
	public void init(){
		User usuario3 = new User("pepe", "pepito", "cuantomeme3@gmail.com", "ROLE_USER");
		User usuario4 = new User("jose", "josito", "cuantomeme4@gmail.com", "ROLE_USER");
		User admin = new User("admin", "admin", "admin@gmail.com","ROLE_USER", "ROLE_ADMIN");

		Vineta v3 = new Vineta("vineta3", "des3", "http://i2.kym-cdn.com/photos/images/facebook/000/125/918/RMUBQ.png");
		Vineta v4 = new Vineta("vineta4", "des4", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465");
		
		v3.setAutor(usuario3);
		v4.setAutor(usuario4);
		
		this.userservice.save(usuario3);
		this.userservice.save(usuario4);
		this.userservice.save(admin);
		this.vinetaservice.save(v3);
		this.vinetaservice.save(v4);
		
		Tag t4 = new Tag("Trolldad");
		Tag t5 = new Tag("Inglip");
		Tag t6 = new Tag("Yaoming");
		
		this.tagservice.save(t4);
		this.tagservice.save(t5);
		this.tagservice.save(t6);
		
		v3.setTags(t4);
		v3.setTags(t5);
		v4.setTags(t4);
		v4.setTags(t6);
		
		this.vinetaservice.save(v3);
		this.vinetaservice.save(v4);
		
		Comentario c2 = new Comentario("pole");
		c2.setAutor_comentario(usuario3);
		c2.setVineta(v3);
		this.comentarioservice.save(c2);
	}
	
	/*--------------------------Autenticacion--------------------------*/

	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request) {	
		this.utilservice.requestCurrentPage(request);
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
		this.userservice.save(usuario);
		return "redirect:/";		
	}

	/*------------------------Usuario----------------------------------*/

	@RequestMapping(value = "/home")
	public String home(Model model, HttpServletRequest request) {
		//Sistema de ""Recomendación""
		//Creo un número aleatorio entre 0 y el número de viñetas que existen
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(this.vinetaservice.findAll().size())+1;

		Principal p = request.getUserPrincipal();
    	User user = this.userservice.findByUsername(p.getName());
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		//model.addAttribute("usuario_logged", user);    
		model.addAttribute("usuario", user);
		model.addAttribute("owner",true);
		model.addAttribute("tags_mas_usados", this.tagservice.findAll());
		model.addAttribute("recomendados", this.vinetaservice.findOne((long) randomInt));
		model.addAttribute("seguidos",user.getFollowing());
		model.addAttribute("seguidores", user.getFollowers());
		return "perfil";
	}
	@RequestMapping(value = "/misfavoritos")
	public String misfavoritos(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "Tus viñetas mas favoritas!");
		Principal p = request.getUserPrincipal();
    	User usuario = this.userservice.findByUsername(p.getName());
		model.addAttribute("usuario", usuario);
		model.addAttribute("vinetas", usuario.getVinetas_favoritas() );
		model.addAttribute("tags_mas_usados", this.tagservice.findAll());
		model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
		System.out.println("hola 1");
		return "misvinetas-social";

	}
	
	@RequestMapping(value = "/mislikes")
	public String mislikes(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "Las vinetas que mas te gustan");
		Principal p = request.getUserPrincipal();
    	User usuario = this.userservice.findByUsername(p.getName());
		model.addAttribute("usuario", usuario);
		model.addAttribute("vinetas", usuario.getVinetas_gustadas());
		model.addAttribute("tags_mas_usados", this.tagservice.findAll());
		model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
		return "misvinetas-social";
	}
	
	@RequestMapping(value = "/misdislikes")
	public String misdislikes(Model model, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "Las vinetas que mas odias");
		Principal p = request.getUserPrincipal();
    	User usuario = this.userservice.findByUsername(p.getName());
		model.addAttribute("usuario", usuario);
		model.addAttribute("vinetas", usuario.getVinetas_odiadas());
		model.addAttribute("tags_mas_usados", this.tagservice.findAll());
		model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
		return "misvinetas-social";
	}
	

	@RequestMapping(value = "/perfil/{id}")
	public String perfil(Model model, @PathVariable long id, HttpServletRequest request) {
		  boolean isfollowed = false;
			if (userComponent.isLoggedUser()){
				User user_tofollow = this.userservice.findOne(id);
				Principal p = request.getUserPrincipal();
			    User current_user = this.userservice.findByUsername(p.getName());
			    isfollowed = current_user.ifollow(user_tofollow);
			}
		  User usuario = this.userservice.findOne(id);
		  System.out.println(usuario.getUsername());
		  model.addAttribute("owner",false);//Si es admin, le  tratamos como si fuera dueño del perfil
		  model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
		  model.addAttribute("usuario", usuario);
		  model.addAttribute("anonymous", !userComponent.isLoggedUser());
		  model.addAttribute("tags_mas_usados", this.tagservice.findAll());
		  model.addAttribute("isfollowed", isfollowed);
		  model.addAttribute("seguidos",usuario.getFollowing());
			model.addAttribute("seguidores", usuario.getFollowers());
		return "perfil";
	}
	@RequestMapping(value = "/seguirperfil/{id}")
	public String seguirPerfil(Model model, @PathVariable long id, HttpServletRequest request) {		  
		  User user_tofollow = this.userservice.findOne(id);
		  Principal p = request.getUserPrincipal();
	      User current_user = userservice.findByUsername(p.getName());
	      current_user.addFollowing(user_tofollow);
	      this.userservice.save(current_user);
		  model.addAttribute("owner",false);
		  model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
	      model.addAttribute("usuario", current_user);
		  model.addAttribute("anonymous", !userComponent.isLoggedUser());
		  model.addAttribute("tags_mas_usados", this.tagservice.findAll());
		  model.addAttribute("isfollowed", current_user.ifollow(user_tofollow));
	      String page = this.utilservice.requestCurrentPage(request); 
	      return "redirect:"+page;
	}
	@RequestMapping(value = "/dejarseguirperfil/{id}")
	public String dejarseguirPerfil(Model model, @PathVariable long id, HttpServletRequest request) {
		  User user_tounfollow = this.userservice.findOne(id);
		  Principal p = request.getUserPrincipal();
	      User current_user = this.userservice.findByUsername(p.getName());
	      current_user.getFollowing().remove(user_tounfollow);
	      this.userservice.save(current_user);
		  model.addAttribute("owner",false);
		  model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
	      model.addAttribute("usuario", current_user);
		  model.addAttribute("anonymous", !userComponent.isLoggedUser());
		  model.addAttribute("tags_mas_usados", this.tagservice.findAll());
		  model.addAttribute("isfollowed", current_user.ifollow(user_tounfollow));
	      String page = this.utilservice.requestCurrentPage(request); 
	      return "redirect:"+page;
	}
	
	@RequestMapping(value = "/eliminarperfil/{id}", method = RequestMethod.POST)
	public String eliminarPerfil(Model model, @PathVariable long id, HttpServletRequest request) {
		boolean isAdmin = request.isUserInRole("ROLE_ADMIN");
		User u = this.userservice.findOne(id);
		if (isAdmin && u != null){
			for(User u2:u.getFollowers()){
				u2.getFollowing().remove(u);
				userservice.save(u2);
			}
			for(User u2:u.getFollowing()){
				u2.getFollowers().remove(u);
				userservice.save(u2);
			}
			u.setFollowers(null);
			u.setFollowing(null);
			userservice.save(u);
			userservice.delete(id);
		}
		
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "¡Bienvenido a CuantoMeme!");
		model.addAttribute("admin", isAdmin);
		model.addAttribute("vinetas", this.vinetaservice.findAllByOrderByCreationdateDesc());
		model.addAttribute("tags_mas_usados", this.tagservice.findAll());
		return "redirect:/";
	}
	
	

	/*----------------------------Global-------------------------*/
	@RequestMapping(value = "/")
	public String viñetas(Model model, HttpServletRequest request) {
		boolean isAdmin = false;
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "¡Bienvenido a CuantoMeme!");
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = this.userservice.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
			isAdmin = request.isUserInRole("ROLE_ADMIN");
		}
		model.addAttribute("admin", isAdmin);
		model.addAttribute("vinetas", this.vinetaservice.findAllByOrderByCreationdateDesc());
		model.addAttribute("tags_mas_usados", this.tagservice.findAll());
		return "index";
	}
	
	  @RequestMapping(value = "/busqueda")
	  public String search(@RequestParam("nombre") String texto, @RequestParam("modo") String modo, Model model, HttpServletRequest request) {
	   model.addAttribute("mensaje", "Resultado para tu busqueda: "+modo+" igual a "+texto);
	   if(modo.equals("titulo")) {
	    model.addAttribute("vinetas",this.vinetaservice.findByTitulo(texto));
	   }
	   if(modo.equals("autor")) {
		   User usuario = this.userservice.findByUsername(texto);
		   if (usuario == null){
			   model.addAttribute("vinetas", null);
		   }else{
			    model.addAttribute("vinetas",usuario.getVinetas_subidas());

		   }
	   }
	   if(modo.equals("tag")) {

		   Tag tag = this.tagservice.findByNombre(texto);

		   if (tag == null){
			   model.addAttribute("vinetas", null);
		   }else{
			    model.addAttribute("vinetas",tag.getVinetas());
		   }
	   }
	   model.addAttribute("admin",request.isUserInRole("ROLE_ADMIN"));
	   model.addAttribute("tags_mas_usados", this.tagservice.findAll());

	   return "resultadobusqueda";
	  }
	  


}
