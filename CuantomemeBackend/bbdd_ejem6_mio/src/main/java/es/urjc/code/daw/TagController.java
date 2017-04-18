package es.urjc.code.daw;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.code.daw.tag.TagService;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserComponent;
import es.urjc.code.daw.user.UserService;

@Controller
public class TagController {
	@Autowired
	private TagService tagservice;
	
	@Autowired
	private UserComponent userComponent;
	
	@Autowired
	private UserService userservice;
	@RequestMapping(value = "/tag/{nombre}")
	public String detalles(Model model, @PathVariable String nombre, HttpServletRequest request) {
		model.addAttribute("anonymous", !userComponent.isLoggedUser());
		if (userComponent.isLoggedUser()){
			Principal p = request.getUserPrincipal();
	    	User usuario = userservice.findByUsername(p.getName());
			model.addAttribute("usuario", usuario);
		}
		model.addAttribute("tag",this.tagservice.findByNombre(nombre));
		model.addAttribute("lista", this.tagservice.findByNombre(nombre).getVinetas());
		model.addAttribute("tags_mas_usados", this.tagservice.findAll());

		return "tagIndex";
	}
}
