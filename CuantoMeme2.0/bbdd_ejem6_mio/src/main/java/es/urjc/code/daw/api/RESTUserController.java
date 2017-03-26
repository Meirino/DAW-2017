package es.urjc.code.daw.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.urjc.code.daw.storage.StorageService;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserComponent;
import es.urjc.code.daw.user.UserRepository;

@RequestMapping("/api/")
public class RESTUserController {
	
	private final StorageService storageService;
	
	@Autowired
    public RESTUserController(StorageService storageService) {
        this.storageService = storageService;
    }
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public User registro(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
		User usuario = new User(username, password, email, "ROLE_USER");
		this.userRepository.save(usuario);
		return usuario;
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public MultipartFile subirImagen(@RequestBody MultipartFile file) {
		storageService.store(file);
		return file;
	}
	
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test(@RequestBody MultipartFile file) {
		return "test";
	}
	
}
