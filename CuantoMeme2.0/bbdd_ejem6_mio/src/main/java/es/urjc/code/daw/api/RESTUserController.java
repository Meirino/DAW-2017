package es.urjc.code.daw.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.api.CMRestControler.UserView;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserRepository;

@RequestMapping("/api/")
@RestController
public class RESTUserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	@JsonView(UserView.class)
	public ResponseEntity<User> registro(@RequestParam("username") String username, @RequestParam("pass") String password, @RequestParam("email") String email) {
		User usuario = new User(username, password, email, "ROLE_USER");
		this.userRepository.save(usuario);
		return new ResponseEntity<>(usuario, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "usuarios/{id}", method = RequestMethod.GET)
	@JsonView(UserView.class)
	public ResponseEntity<User> getUsuarioByID(@PathVariable int id) {
		if(this.userRepository.findOne((long) id) != null) {
			return new ResponseEntity<>(this.userRepository.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "usuarios/{id}", method = RequestMethod.PUT)
	@JsonView(UserView.class)
	public ResponseEntity<User> modificarUsuario(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("email") String email) {
		if(this.userRepository.findOne((long) id) != null) {
			User original = this.userRepository.findOne((long) id);
			original.setUsername(nombre);
			original.setEmail(email);
			this.userRepository.save(original);
			return new ResponseEntity<>(this.userRepository.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
	}
}
