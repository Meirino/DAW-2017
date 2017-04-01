package es.urjc.code.daw.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserService;
import es.urjc.code.daw.vineta.*;

@Service
public class utils {
	@Autowired
	private UserService userservice;
	@Autowired
	private VinetaService vinetaservice;
	
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
	

}
