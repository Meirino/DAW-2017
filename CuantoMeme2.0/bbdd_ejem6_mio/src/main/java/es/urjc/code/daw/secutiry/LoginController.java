package es.urjc.code.daw.secutiry;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.vineta.Vineta;

/**
 * This class is used to provide REST endpoints to logIn and logOut to the
 * service. These endpoints are used by Angular 2 SPA client application.
 * 
 * NOTE: This class is not intended to be modified by app developer.
 */
@RestController
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	interface UserView extends User.BasicAtt, User.VinetaupAtt, User.ComentarioAtt, Comentario.BasicAtt, Vineta.BasicAtt, User.VinetafavAtt,
	User.VinetadislikeAtt, User.VinetalikeAtt, User.SeguidoresAtt, User.RolesAtt, Vineta.TagAtt{}
	@Autowired
	private UserComponent userComponent;
	@Autowired
	private UserService userservice;
	
	
	@CrossOrigin
	@RequestMapping("/api/logIn")
	@JsonView(UserView.class)
	public ResponseEntity<User> logIn() {
		
		if (!userComponent.isLoggedUser()) {
			log.info("Not user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			long id = userComponent.getLoggedUser().getId();
			User u = this.userservice.findOne(id);
			log.info("Logged as " + u.getUsername());
			return new ResponseEntity<>(u, HttpStatus.OK);
		}
	}

	@RequestMapping("/logOut")
	public ResponseEntity<Boolean> logOut(HttpSession session) {

		if (!userComponent.isLoggedUser()) {
			log.info("No user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			session.invalidate();
			log.info("Logged out");
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}

}