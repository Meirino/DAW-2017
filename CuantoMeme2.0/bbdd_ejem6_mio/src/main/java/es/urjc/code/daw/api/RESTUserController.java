package es.urjc.code.daw.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.api.CMRestControler.UserView;
import es.urjc.code.daw.storage.StorageService;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserComponent;
import es.urjc.code.daw.user.UserRepository;

@RequestMapping("/api/")
@RestController
public class RESTUserController {
	
	private final StorageService storageService;
	
	@Autowired
    public RESTUserController(StorageService storageService) {
        this.storageService = storageService;
    }
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	@JsonView(UserView.class)
	public User registro(@RequestParam("username") String username, @RequestParam("pass") String password, @RequestParam("email") String email) {
		User usuario = new User(username, password, email, "ROLE_USER");
		this.userRepository.save(usuario);
		return usuario;
	
	}
}
