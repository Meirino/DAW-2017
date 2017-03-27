package es.urjc.code.daw.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.api.CMRestControler.UserView;
import es.urjc.code.daw.storage.StorageService;
import es.urjc.code.daw.user.*;

@RequestMapping("/api/users")
@RestController
public class RESTUserController {
	
	private final StorageService storageService;
	
	@Autowired
    public RESTUserController(StorageService storageService) {
        this.storageService = storageService;
    }
	
	@Autowired
	private UserService userservice;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@JsonView(UserView.class)
	@ResponseStatus(HttpStatus.CREATED)
	public User registro(@RequestParam("username") String username, @RequestParam("pass") String password, @RequestParam("email") String email) {
		User usuario = new User(username, password, email, "ROLE_USER");
		this.userservice.save(usuario);
		return usuario;	
	}
	
	@JsonView(User.BasicAtt.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<User> getusuarios(){
		return this.userservice.findAll();
	}
	
	@JsonView(UserView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getusuario(@PathVariable int id){
		User usuario = userservice.findOne(id);
		if (usuario != null) {
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
