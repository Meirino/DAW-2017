package es.urjc.code.daw;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.code.daw.tag.TagService;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.vineta.*;
import es.urjc.code.daw.utils.*;
@Controller
public class VinetaController {
	@Autowired
	private UserService userservice;
	
	@Autowired
	private VinetaService vinetaservice;
	
	@Autowired
	private TagService tagservice;
	
	@Autowired
	private UserComponent userComponent;
	
	@Autowired
	private utils utilservice;

	@RequestMapping(value = "/vinetas")
	public String vinetas(Model model, HttpServletRequest request) {
		boolean isAdmin = false;
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("mensaje", "¡Bienvenido a CuantoMeme!");
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userservice.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
			isAdmin = request.isUserInRole("ROLE_ADMIN");
		}
		model.addAttribute("admin", isAdmin);
		model.addAttribute("vinetas", this.vinetaservice.findAllByOrderByCreationdateDesc());
		model.addAttribute("tags_mas_usados", this.tagservice.findAll());
		return "index";
	}
	
	@RequestMapping("/vineta/{id}")
	public String detalles(Model model, @PathVariable long id, HttpServletRequest request) {
		boolean isfollowed = false;
		boolean owner = false;
		Vineta v = this.vinetaservice.findOne((long) id);
		if (userComponent.isLoggedUser()){
			User user_tofollow = this.userservice.findOne(id);
			Principal p = request.getUserPrincipal();
		    User current_user = userservice.findByUsername(p.getName());
		    isfollowed = current_user.ifollow(user_tofollow);
		    owner = (v.getAutor().getId() == current_user.getId()) || request.isUserInRole("ROLE_ADMIN");
		}

		model.addAttribute("isfollowed", isfollowed);
		model.addAttribute("admin", owner);
	    model.addAttribute("usuariologged", userComponent.isLoggedUser());
		model.addAttribute("vineta", v);
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		model.addAttribute("tags_mas_usados", this.tagservice.findAll());

		return "detalles";
	}
	
	@RequestMapping(value = "/likevineta/{id}")
	public String likeVineta(Model model, @PathVariable long id, HttpServletRequest request ) {
		  String page = this.utilservice.requestCurrentPage(request);
		  Principal p = request.getUserPrincipal();
	      User user = userservice.findByUsername(p.getName());
	      Vineta v = vinetaservice.findOne(id);      
	      if (!v.isLikedBefore(user)){
		   	 user.getVinetas_gustadas().add(v);
	         v.like();
		     this.vinetaservice.save(v);
		     this.userservice.save(user);}	      
	      return "redirect:"+page;
	}
	@RequestMapping(value = "/hacerfavorita/{id}")
	public String hacerfavorita(Model model, @PathVariable long id, HttpServletRequest request) {
		String page = this.utilservice.requestCurrentPage(request);
		Principal p = request.getUserPrincipal();
	    User user = userservice.findByUsername(p.getName());
	    Vineta v = vinetaservice.findOne(id);
        if (!v.isFavoritedBefore(user)){
			 user.getVinetas_favoritas().add(v);
	         this.vinetaservice.save(v);
	         this.userservice.save(user);}
	    return "redirect:"+page;
	}
	@RequestMapping(value = "/eliminarvineta/{id}")
	public String eliminarVineta(Model model, @PathVariable long id, HttpServletRequest request ) {
		  Principal p = request.getUserPrincipal();
	      User user = userservice.findByUsername(p.getName());
	      Vineta v = vinetaservice.findOne(id);
	      // Este metodo elimina la relacion con aquello usuarios que le dieron like, dislike o favor
	      if((v.getAutor().getId() == user.getId()) || request.isUserInRole("ROLE_ADMIN") ){
		      utilservice.deletesocialvineta(v);
	      }
	      //String page = this.requestCurrentPage(request);  
	      return "redirect:/";
	}
	
	@RequestMapping(value = "/dislikevineta/{id}")
	public String dislikeVineta(Model model, @PathVariable long id, HttpServletRequest request) {
		String page = this.utilservice.requestCurrentPage(request);
		Principal p = request.getUserPrincipal();
        User user = userservice.findByUsername(p.getName());
        Vineta v = vinetaservice.findOne(id);
        if (!v.isDislikedBefore(user)){
			 user.getVinetas_odiadas().add(v);
        	 v.dislike();
	         this.vinetaservice.save(v);
	         this.userservice.save(user);}
		  return "redirect:"+page;
	}
	
}
