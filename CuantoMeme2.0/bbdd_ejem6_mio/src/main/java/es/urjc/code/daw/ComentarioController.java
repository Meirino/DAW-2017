package es.urjc.code.daw;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.comentario.ComentarioService;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserService;
import es.urjc.code.daw.vineta.VinetaService;
import es.urjc.code.daw.utils.*;
@Controller
public class ComentarioController {
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private VinetaService vinetaservice;
	
	@Autowired
	private ComentarioService comentarioservice;
	
	@Autowired
	private utils utilservice; 
	
	@RequestMapping(value = "/crearComentario/vineta/{id}", method = RequestMethod.POST)
	public String crearComentario(Model model, HttpSession sesion,@PathVariable long id, @RequestParam String comentario, HttpServletRequest request ) {
		  String page = this.utilservice.requestCurrentPage(request);  
		  Principal p = request.getUserPrincipal();
	      User user = userservice.findByUsername(p.getName());
	      Comentario c = new Comentario(comentario);
	      c.setAutor_comentario(user);
	      c.setVineta(this.vinetaservice.findOne(id));
	      this.comentarioservice.save(c);
	      return "redirect:"+page;

	}
	@RequestMapping(value = "/eliminarcomentario/{id}")
	public String eliminarComentario(Model model, @PathVariable long id, HttpServletRequest request ) {
		  Principal p = request.getUserPrincipal();
	      User user = userservice.findByUsername(p.getName());
	      Comentario c = comentarioservice.findOne(id);
	      if(c.getAutor_comentario().getId() == user.getId()){
	    	  comentarioservice.delete(id);
	      }
	      String page = this.utilservice.requestCurrentPage(request); 
	      return "redirect:"+page;
	}
}
