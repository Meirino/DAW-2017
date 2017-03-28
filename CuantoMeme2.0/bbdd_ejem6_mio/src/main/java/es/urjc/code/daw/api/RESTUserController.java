package es.urjc.code.daw.api;

import java.security.Principal;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.api.CMRestControler.UserView;
import es.urjc.code.daw.storage.StorageService;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserRepository;

@RequestMapping("/api/users")
@RestController
public class RESTUserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private UserService userservice;

    public void FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@JsonView(UserView.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<User> signUP(@RequestParam("username") String username, @RequestParam("pass") String password, @RequestParam("email") String email) {
		if(this.userservice.findByUsername(username) == null) {
			User usuario = new User(username, password, email, "ROLE_USER");
			this.userservice.save(usuario);
			return new ResponseEntity<>(usuario, HttpStatus.CREATED);
		} else {
			//Si no, no se crea
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
			
	}
	
	@JsonView(User.BasicAtt.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<User> getUsers(){
		return this.userservice.findAll();
	}
	
	@JsonView(UserView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> gerUser(@PathVariable int id){
		User usuario = userservice.findOne(id);
		if (usuario != null) {
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@JsonView(UserView.class)
	public ResponseEntity<User> modifyUSer(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("email") String email, HttpServletRequest request) {
		Principal p = request.getUserPrincipal();
    	User user = this.userRepository.findByUsername(p.getName());
		
		if(this.userservice.findOne((long) id) != null && (this.userservice.findOne((long) id).equals(user) || user.getRoles().contains("ROLE_ADMIN"))) {
			User original = this.userservice.findOne((long) id);
			original.setUsername(nombre);
			original.setEmail(email);
			this.userRepository.save(original);
			return new ResponseEntity<>(this.userRepository.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value = "/avatar", method = RequestMethod.PUT)
    public ResponseEntity<User> handleAvatarUpload(@RequestAttribute("file") MultipartFile avatar, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		MultipartFile file = (MultipartFile) request.getAttribute("file");
    	Principal p = request.getUserPrincipal();
    	User user = userRepository.findByUsername(p.getName());
    	user.setAvatarURL("/imgs/"+file.getOriginalFilename());
    	this.userRepository.save(user);
        storageService.store(file);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> handleAvatarUpload(@PathVariable int id) {
		
		if(this.userservice.findOne((long) id) != null) {
			User usuario = this.userservice.findOne((long) id);
	    	this.userservice.delete(usuario.getId());
	    	return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

}
