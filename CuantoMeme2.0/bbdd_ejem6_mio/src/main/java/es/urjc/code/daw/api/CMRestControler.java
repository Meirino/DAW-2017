package es.urjc.code.daw.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.tag.TagRepository;
import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.comentario.ComentarioRepository;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.vineta.*;

@RestController
public class CMRestControler {
	
	interface VinetaView extends Vineta.BasicAtt, Vineta.UserAtt, User.BasicAtt, Vineta.TagAtt, Tag.BasicAtt, Vineta.ComentariosAtt, Comentario.BasicAtt, Comentario.UserAtt{}
	interface UserView extends User.BasicAtt, User.VinetaAtt, User.ComentarioAtt, Comentario.BasicAtt, Vineta.BasicAtt{}
	interface TagView extends Tag.BasicAtt {}
	
	@Autowired
	private UserRepository userrepository;

	@Autowired
	private ComentarioRepository comentariorepository;
	
	@Autowired
	private VinetaRepository vinetarepository;
	
	@Autowired
	private TagRepository tagrepository;
	
	
	@JsonView(VinetaView.class)
	@RequestMapping(value = "/api/vinetaspage/", method= RequestMethod.GET)
	public List<Vineta> getvinetaspage(Pageable page ){
		//This System.out print all the size of my repository
		System.out.println(this.vinetarepository.findAll().size());
		//This for print the title for my 20 first objects Vineta
		for(Vineta v:this.vinetarepository.findAll(page)){
			System.out.println(v.getTitulo());
		}
		// This print 20
		System.out.println(this.vinetarepository.findAll(page).getSize());
		//This print the page number = 0 
		System.out.println(page.getPageNumber());
		//This print the page size = 20 
		System.out.println(page.getPageSize());
		
		Integer seconds = 2;

        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		//Finally, this is not returning nothing
		return vinetarepository.findAll(page).getContent();
	}
	@JsonView(VinetaView.class)
	@RequestMapping("/api/vinetas2/")
	public List<Vineta> getvinetas2(){
		return this.vinetarepository.findAll();
	}
	@JsonView(VinetaView.class)
	@RequestMapping("/api/vinetas3/")
	public Page<Vineta> getvinetas3(Pageable page){
		return this.vinetarepository.findAll(page);
	}
	@JsonView(Vineta.BasicAtt.class)
	@RequestMapping("/api/vineta/{id}")
	public Vineta getvineta(@PathVariable long id){
		return this.vinetarepository.findOne(id);
	}
	
	@JsonView(User.BasicAtt.class)
	@RequestMapping("/api/usuarios/")
	public List<User> getusuarios(){
		return this.userrepository.findAll();
	}
	
	@JsonView(UserView.class)
	@RequestMapping("/api/usuarios/{id}")
	public User getusuario(@PathVariable int id){
		return this.userrepository.findOne((long) id);
	}
	
	@JsonView(UserView.class)
	@RequestMapping("/api/usuariosByName/{nombre}")
	public User getusuariobyname(@PathVariable String nombre){
		return this.userrepository.findByUsername(nombre);
	}
	
	@JsonView(TagView.class)
	@RequestMapping("/api/tags/{nombre}")
	public Tag getTagsByName(@PathVariable String nombre){
		return this.tagrepository.findByNombre(nombre);
	}
	
	
}
