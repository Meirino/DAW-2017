package es.urjc.code.daw.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.tag.TagService;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserService;
import es.urjc.code.daw.vineta.*;

@Service
public class utils {
	@Autowired
	private UserService userservice;
	
	@Autowired
	private VinetaService vinetaservice;
	
	@Autowired
	private TagService tagservice;
	
	public String requestCurrentPage(HttpServletRequest request){
	    String referrer = request.getHeader("Referer");
	    if(referrer!=null){
	        request.getSession().setAttribute("url_prior_login", referrer);
	    }
	    return referrer;
	}
	
	public void deletesocialvineta(Vineta v){
		for(User u:v.getUsers_likes()){
	    	  u.getVinetas_gustadas().remove(v);
	    	  userservice.save(u);
	      }
	      for(User u:v.getUsers_dislikes()){
	    	  u.getVinetas_odiadas().remove(v);
	    	  userservice.save(u);
	      }
	      for(User u:v.getUsers_fav()){
	    	  u.getVinetas_favoritas().remove(v);
	    	  userservice.save(u);
	      }
	      this.vinetaservice.save(v);
	      this.vinetaservice.delete(v.getId());
	}
	
	public List<Vineta> busquedaVineta(String nombre) {
		
		List<Vineta> resultado = new ArrayList<Vineta>();
		List<Vineta> viñetas = new ArrayList<Vineta>();
		viñetas = this.vinetaservice.findAll();
		for(Vineta v : viñetas) {
			if(v.getTitulo().contains(nombre)) {
				resultado.add(v);
			}
		}
		return resultado;
	}
	
	public List<Vineta> busquedaVinetasPorUsuarios(String nombre) {
		
		List<Vineta> resultado = new ArrayList<Vineta>();
		List<Vineta> viñetas = new ArrayList<Vineta>();
		viñetas = this.vinetaservice.findAll();
		for(Vineta v : viñetas) {
			if(v.getAutor().getUsername().contains(nombre)) {
				resultado.add(v);
			}
		}
		return resultado;
	}
	
	public List<Vineta> busquedaVinetasPorTags(String nombre) {
		
		List<Vineta> resultado = new ArrayList<Vineta>();
		List<Tag> tags = new ArrayList<Tag>();
		tags = this.tagservice.findAll();
		for(Tag t : tags) {
			if(t.getNombre().contains(nombre)) {
				resultado.addAll(t.getVinetas());
			}
		}
		return resultado;
	}

}
