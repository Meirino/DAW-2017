package es.urjc.code.daw;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.comentario.ComentarioRepository;
import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.tag.TagRepository;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserRepository;
import es.urjc.code.daw.vineta.Vineta;
import es.urjc.code.daw.vineta.VinetaRepository;
import es.urjc.code.daw.user.*;

@Controller
public class Loader {

	@Autowired
	private UserRepository userrepository;

	@Autowired
	private ComentarioRepository comentariorepository;
	
	@Autowired
	private VinetaRepository vinetarepository;
	
	@Autowired
	private TagRepository tagrepository;
	
	@Autowired
	private UserComponent userComponent;
	
	private List<User> users_generated = new ArrayList<>();
	private List<Vineta> vinetas_generated = new ArrayList<>();
	private List<Tag> tags_generated = new ArrayList<>();
	private List<Comentario> comentarios_generated = new ArrayList<>();
	
	static final int NUSERS = 10;
	static final int NTAGS = 10;
	static final int NVINETAS = 100;
	static final int NCOMENTARIOS = 30;
	@PostConstruct
	public void init(){
		for(int i= 0; i<NUSERS; i++){
			User u = new User("usuario_"+i, "usuario_"+i, "cuantomeme"+i+"@gmail.com", "ROLE_USER");
			this.users_generated.add(u);
			//this.userrepository.save(u);
		}
		for(int i= 0; i<NVINETAS; i++){
			Vineta v = new Vineta("vineta"+i, "des"+i, "http://runt-of-the-web.com/wordpress/wp-content/uploads/2012/05/funnest-troll-dad-rage-comics-computers.gif");
			this.vinetas_generated.add(v);
			//this.vinetarepository.save(v);
		}
		for(int i= 0; i<NTAGS; i++){
			Tag t = new Tag("Lol"+i);
			this.tags_generated.add(t);
			//this.tagrepository.save(t);
		}
		for(int i= 0; i<NCOMENTARIOS; i++){
			Comentario c = new Comentario("El comentario "+i);
			this.comentarios_generated.add(c);
			//this.comentariorepository.save(c);
		}
		for (Vineta v: this.vinetas_generated){
			/*Subida a 1:N
			 * Se guarda el secundario
			 * al primario se le asocia el secndario
			 * se guarda el primario
			 * */
			//Esta te sube todo bien a la bd pero algo falla en la api
			int id_user = (int) (Math.random() * NUSERS);
			User user = this.users_generated.get(id_user);
			v.setAutor(user);
			//user.addVineta(v);
			this.userrepository.save(user);
			this.vinetarepository.save(v);
			/*
			 * Con esto salen las vinetas subidas en la api pero algo va mal y en la tabla vinetas
			 * salen mas entrasd con vinetas duplicadas
			int id_user = (int) (Math.random() * NUSERS);
			User user = this.users_generated.get(id_user);
			v.setAutor(user);
			user.addVineta(v);
			this.userrepository.save(user);*/
		}
		
		/*
		//Metiendo likes de forma aleatoria
		for (Vineta v: this.vinetas_generated.subList(0, 13)){
			Forma de guardar un many to many.
			1. Se fuarda el secundario
			2. Al primario se añade el secundario
			3. Se guarda el primario
			
			this.vinetarepository.save(v);
			int id_user = (int) (Math.random() * NUSERS);
			User user = this.users_generated.get(id_user);
			user.getVinetas_odiadas().add(v);
			this.userrepository.save(user);
			
		}
		for (Vineta v: this.vinetas_generated.subList(13,26)){

			this.vinetarepository.save(v);
			int id_user = (int) (Math.random() * NUSERS);
			User user = this.users_generated.get(id_user);
			user.getVinetas_gustadas().add(v);
			this.userrepository.save(user);
			
		}
		for (Vineta v: this.vinetas_generated.subList(26,40)){

			this.vinetarepository.save(v);
			int id_user = (int) (Math.random() * NUSERS);
			User user = this.users_generated.get(id_user);
			user.getVinetas_favoritas().add(v);
			this.userrepository.save(user);
			
		}*/

	}
}
