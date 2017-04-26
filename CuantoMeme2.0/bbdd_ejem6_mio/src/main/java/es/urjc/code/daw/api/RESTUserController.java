package es.urjc.code.daw.api;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.api.CMRestControler.VinetaView;
import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.storage.StorageService;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.utils.utils;
import es.urjc.code.daw.vineta.Vineta;

@RequestMapping("/api/usuarios")
@RestController
public class RESTUserController {	
	private final long bytes = 1048576;
	
	interface UserView extends User.BasicAtt, User.RolesAtt {}
	interface UserViewDetails extends User.BasicAtt, User.VinetaupAtt, User.ComentarioAtt, Comentario.BasicAtt, Vineta.BasicAtt, User.VinetafavAtt,
	User.VinetadislikeAtt, User.VinetalikeAtt, User.SeguidoresAtt, User.RolesAtt, Vineta.TagAtt, Vineta.UserAtt{}
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private UserComponent userComponent;
	
	@Autowired
	private utils utilidades;
	
	@JsonView(VinetaView.class)
	@RequestMapping(value = "/busq/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> buscarUsuario(@PathVariable String nombre){
		if(!this.utilidades.busquedaUsuarios(nombre).isEmpty()) {
			return new ResponseEntity<>(this.utilidades.busquedaUsuarios(nombre), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

    public void FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@JsonView(UserView.class)
	public ResponseEntity<User> signUP(@RequestParam("username") String username, @RequestParam("pass") String password, @RequestParam("email") String email) {
		if(this.userservice.findByUsername(username) == null) {
			User usuario = new User(username, password, email, "ROLE_USER");
			this.userservice.save(usuario);
			return new ResponseEntity<>(usuario, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
			
	}
	
	@RequestMapping(value = "/signupJSON", method = RequestMethod.POST)
	@JsonView(UserView.class)
	public ResponseEntity<User> signUpConJSON(@RequestBody User usuario) {
		if(this.userservice.findByUsername(usuario.getUsername()) == null) {
			this.userservice.save(usuario);
			return new ResponseEntity<>(usuario, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
			
	}
	@CrossOrigin
	@JsonView(User.BasicAtt.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers(){
		if(!this.userservice.findAll().isEmpty()) {
			return new ResponseEntity<>(this.userservice.findAll(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@CrossOrigin
	@JsonView(UserView.class)
	@RequestMapping("/logIn")
	public ResponseEntity<User> logIn() {
		if (!userComponent.isLoggedUser()) {
			//System.out.println("no autorizado");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			//User loggedUser = userComponent.getLoggedUser();			
			long id = userComponent.getLoggedUser().getId();
			//System.out.println(loggedUser.getUsername());
			return new ResponseEntity<>(userservice.findOne(id),HttpStatus.OK);
			
		}
	}
	
	@RequestMapping("/logOut")
	public ResponseEntity<Boolean> logOut(HttpSession session) {

		if (!userComponent.isLoggedUser()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			session.invalidate();
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}
	@CrossOrigin
	@JsonView(UserView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable int id){
		User usuario = userservice.findOne(id);
		if (usuario != null) {
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(UserView.class)
	@RequestMapping(value = "/byname/{username}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable String username){
		User usuario = userservice.findByUsername(username);
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
			this.userservice.save(original);
			return new ResponseEntity<>(original, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@JsonView(UserView.class)
	public ResponseEntity<User> deleteUSer(@PathVariable int id) {
		User u = this.userservice.findOne(id);
		if (u == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else{

			for(User u2:u.getFollowers()){
				u2.getFollowing().remove(u);
				userservice.save(u2);
			}
			for(User u2:u.getFollowing()){
				u2.getFollowers().remove(u);
				userservice.save(u2);
			}
			u.setFollowers(null);
			u.setFollowing(null);
			userservice.save(u);
			userservice.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/follow/{id}", method = RequestMethod.PUT)
	@JsonView(UserView.class)
	public ResponseEntity<User> followUSer(@PathVariable long id, HttpServletRequest request) {	
		User user_tofollow = this.userservice.findOne(id);
		Principal p = request.getUserPrincipal();
	    User current_user = userservice.findByUsername(p.getName());
		if (user_tofollow == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else{
		    current_user.addFollowing(user_tofollow);
		    this.userservice.save(current_user);
			return new ResponseEntity<>(current_user, HttpStatus.OK);
			
		}
	}
	
	@RequestMapping(value = "/timeline", method = RequestMethod.GET)
	@JsonView(UserView.class)
	public ResponseEntity<List<Vineta>> getTimeline(HttpServletRequest request) {	
		Principal p = request.getUserPrincipal();
	    User current_user = userservice.findByUsername(p.getName());
		if (!current_user.getFollowing().isEmpty()) {
			
			ArrayList<Vineta> timeline = new ArrayList<Vineta>();
			
			for(User u : current_user.getFollowing()) {
				timeline.addAll(u.getVinetas_subidas());
			}
			
			return new ResponseEntity<>(timeline, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/unfollow/{id}", method = RequestMethod.PUT)
	@JsonView(UserView.class)
	public ResponseEntity<User> unfollowUSer(@PathVariable int id, HttpServletRequest request) {	
		  User user_tounfollow = this.userservice.findOne(id);
		  Principal p = request.getUserPrincipal();
	      User current_user = this.userservice.findByUsername(p.getName());
		if (user_tounfollow == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else{
			current_user.getFollowing().remove(user_tounfollow);
		    this.userservice.save(current_user);
			return new ResponseEntity<>(current_user, HttpStatus.OK);	
		}
	}
	

	
	@RequestMapping(value = "/avatar", method = RequestMethod.PUT)
    public ResponseEntity<User> handleAvatarUpload(@RequestParam("file") MultipartFile avatar, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		System.out.println("he llegado a cambiar avatar");
		if (!userComponent.isLoggedUser()){
		     return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if(avatar.getSize() <= this.bytes) {
			Principal p = request.getUserPrincipal();
	    	User user = userRepository.findByUsername(p.getName());
	    	user.setAvatarURL("http://localhost:8080/imgs/"+avatar.getOriginalFilename());
	    	this.userRepository.save(user);
	        storageService.store(avatar);

	        return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }	

}
