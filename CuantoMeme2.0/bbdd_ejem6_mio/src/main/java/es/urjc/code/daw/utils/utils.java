package es.urjc.code.daw.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class utils {
	public String requestCurrentPage(HttpServletRequest request){
	    String referrer = request.getHeader("Referer");
	    if(referrer!=null){
	        request.getSession().setAttribute("url_prior_login", referrer);
	    }
	    return referrer;
	}
}
