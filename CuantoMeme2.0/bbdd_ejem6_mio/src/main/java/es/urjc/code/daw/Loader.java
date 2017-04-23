package es.urjc.code.daw;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.tag.*;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.user.UserRepository;
import es.urjc.code.daw.vineta.*;
import es.urjc.code.daw.vineta.VinetaRepository;

@Controller
public class Loader {

	@Autowired
	private TagService tagservice;


	
	@Autowired
	private VinetaService vinetaservice;

	@Autowired
	private UserService userservice;
	
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
		Tag tag = new Tag("Trolldad");
		this.tagservice.save(tag);
		for(int i= 0; i<NUSERS; i++){
			User u = new User("usuario_"+i, "usuario_"+i, "cuantomeme@gmail.com", "ROLE_USER");
			this.users_generated.add(u);
			//this.userrepository.save(u);
		}
		for(int i= 0; i<NVINETAS; i++){
			Vineta v = new Vineta("vineta"+i, "des"+i, "http://runt-of-the-web.com/wordpress/wp-content/uploads/2012/05/funnest-troll-dad-rage-comics-computers.gif");
			v.setTags(tag);
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

			int id_user = (int) (Math.random() * NUSERS);
			User user = this.users_generated.get(id_user);
			v.setAutor(user);
			//user.addVineta(v);
			this.userservice.save(user);
			this.vinetaservice.save(v);

		}
		
	}
}
