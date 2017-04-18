package es.urjc.code.daw.api;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.comentario.*;
import es.urjc.code.daw.storage.StorageService;
import es.urjc.code.daw.tag.*;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.utils.utils;
import es.urjc.code.daw.vineta.*;
@RequestMapping("/api/vinetas")
@RestController
public class RESTVinetaController {
	
	interface VinetaView extends Vineta.BasicAtt , Vineta.UserAtt, User.BasicAtt, Vineta.TagAtt, Tag.BasicAtt, Vineta.ComentariosAtt, Comentario.BasicAtt, Comentario.UserAtt{}
	
	@Autowired
	private VinetaService vinvetaservice;
	
	@Autowired
	private utils utilservice;
    
    @Autowired
	private TagService tagservice;
    
    @Autowired
    private UserService userservice;
	
	@Autowired
	private StorageService storageService;
	@Autowired
	private UserComponent userComponent;
	@Autowired
    public void FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
	
	@Autowired
	private utils utilidades;
	
	@CrossOrigin
	@JsonView(VinetaView.class)
	@RequestMapping(value = "/", method= RequestMethod.GET)
	public ResponseEntity<List<Vineta>> getvinetaspage(Pageable page, HttpServletRequest request ) {

		if(!this.vinvetaservice.findAll().isEmpty()) {
			//This System.out print all the size of my repository
			System.out.println(this.vinvetaservice.findAll().size());
			//This for print the title for my 20 first objects Vineta
			for(Vineta v:this.vinvetaservice.findAll(page)){
				System.out.println(v.getTitulo());
			}
			System.out.println(userComponent.getLoggedUser());
			// This print 20
			System.out.println(this.vinvetaservice.findAll(page).getSize());
			//This print the page number = 0 
			System.out.println(page.getPageNumber());
			//This print the page size = 20 
			System.out.println(page.getPageSize());
			
			Integer seconds = 2;

	        try {
	            Thread.sleep(seconds*1000);
	    		System.out.println("en vinetas veo a ");
	    		System.out.println(request.getUserPrincipal());
	    		System.out.println(this.userComponent.getLoggedUser());
	        } catch (InterruptedException e) {
	           
	            e.printStackTrace();
	        }
			
			//Finally, this is not returning nothing
			return new ResponseEntity<>(this.vinvetaservice.findAll(page).getContent(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	@CrossOrigin
	@JsonView(VinetaView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Vineta> getvineta(@PathVariable int id){
		if(this.vinvetaservice.findOne((long) id) != null) {
			return new ResponseEntity<>(this.vinvetaservice.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin
	@JsonView(VinetaView.class)
	@RequestMapping(value = "/busq/{texto}", method = RequestMethod.GET)
	public ResponseEntity<List<Vineta>> busqVineta(@PathVariable String texto, @RequestParam(required = false, defaultValue = "titulo", value="filtro") String filtro){
		switch(filtro) {
			case("usuario"):
				if(!this.utilidades.busquedaVinetasPorUsuarios(texto).isEmpty()) {
					return new ResponseEntity<>(this.utilidades.busquedaVinetasPorUsuarios(texto), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			case("tag"):
				if(!this.utilidades.busquedaVinetasPorTags(texto).isEmpty()) {
					return new ResponseEntity<>(this.utilidades.busquedaVinetasPorTags(texto), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			default:
				if(!this.utilidades.busquedaVineta(texto).isEmpty()) {
					return new ResponseEntity<>(this.utilidades.busquedaVineta(texto), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
		}
	}
	
	@CrossOrigin
	@JsonView(Vineta.BasicAtt.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Vineta> deletevineta(@PathVariable int id, HttpServletRequest request) {
		Principal p = request.getUserPrincipal();
    	User user = this.userservice.findByUsername(p.getName());
    	
		if(this.vinvetaservice.findOne((long) id) != null && (this.vinvetaservice.findOne((long) id).getAutor().equals(user) || user.getRoles().contains("ROLE_ADMIN"))) {
			Vineta viñeta = this.vinvetaservice.findOne((long) id);
			this.utilservice.deletesocialvineta(viñeta);
			return new ResponseEntity<>(this.vinvetaservice.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@CrossOrigin
	@JsonView(Vineta.BasicAtt.class)
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Vineta> postVineta(@RequestParam("titulo") String titulo, @RequestParam("desc") String desc, @RequestParam("file") MultipartFile file, @RequestParam("tags") String tag, HttpServletRequest request){
		
		//Quito los espacios del string
    	tag = tag.trim();
    	
    	//Creo un tag provisional y un long
    	Tag tagTemp = new Tag(tag);
    	long indice = 0;
    	
    	//Creo un array de tags con todos los tags
    	ArrayList<Tag> tags = new ArrayList<Tag>();
    	tags = (ArrayList<Tag>) this.tagservice.findAll();
    	
    	//Creo un booleano para ver si lo he encontrado
    	boolean found = false;
    	
    	//Comparo todos lo tags para ver si existe alguno con el mismo nombre. Si existe alguno con el mismo nombre, copio su id y pongo found a true
    	for(int i = 0; i < tags.size(); i++) {
    		if(tags.get(i).getNombre().equals(tag)) {
    			indice = tags.get(i).getId();
    			found = true;
    		}
    	}
    	Vineta viñeta = new Vineta(titulo, desc, "http://localhost:8080/imgs/"+file.getOriginalFilename());
    	Principal p = request.getUserPrincipal();
    	User user = userservice.findByUsername(p.getName());
    	viñeta.setAutor(user);
    	
    	//Si existe ya el tag, se añade a la viñeta, y sino se crea
    	if(found) {
    		viñeta.setTags(this.tagservice.findOne(indice));
    	} else {
    		this.tagservice.save(tagTemp);
    		viñeta.setTags(tagTemp);
    	};
    	
    	this.vinvetaservice.save(viñeta);
        storageService.store(file);
        
        return new ResponseEntity<>(viñeta, HttpStatus.CREATED);
	}

	@CrossOrigin
	@JsonView(Vineta.BasicAtt.class)
	@RequestMapping(value = "/dislike/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Vineta> dislikeVineta(@PathVariable long id, HttpServletRequest request){
		System.out.println("ya he llegado");
		Principal p = request.getUserPrincipal();
        User user = userservice.findByUsername(p.getName());
		System.out.println(p.getName());
        Vineta v = vinvetaservice.findOne(id);
        if (v != null){
        	if (!v.isDislikedBefore(user)){
        		user.getVinetas_odiadas().add(v);
        		System.out.println("not before");
        		v.dislike();
        		this.vinvetaservice.save(v);
        		this.userservice.save(user);}
        	return new ResponseEntity<>(v, HttpStatus.OK);
        }else{
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	
	}
	
	@CrossOrigin
	@JsonView(Vineta.BasicAtt.class)
	@RequestMapping(value = "/like/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Vineta> likeVineta(@PathVariable long id, HttpServletRequest request){
		System.out.println("ya he llegado-----");
		Principal p = request.getUserPrincipal();
		System.out.println(p.getName());
        User user = userservice.findByUsername(p.getName());
        Vineta v = vinvetaservice.findOne(id);
        if (v != null){
        	if (!v.isLikedBefore(user)){
        		System.out.println("not before");
        		user.getVinetas_gustadas().add(v);
        		v.like();
        		this.vinvetaservice.save(v);
        		this.userservice.save(user);}
        	return new ResponseEntity<>(v, HttpStatus.OK);
        }else{
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	
	}
	@CrossOrigin
	@JsonView(Vineta.BasicAtt.class)
	@RequestMapping(value = "/like2/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Vineta> likeVineta2(@PathVariable long id, HttpServletRequest request){
		System.out.println("ya he llegado****");
		User user = userComponent.getLoggedUser();
		System.out.println(user);
        Vineta v = vinvetaservice.findOne(id);
        if (v != null){
        	/*
        	if (!v.isLikedBefore(user)){
        		System.out.println("not before");
        		user.getVinetas_gustadas().add(v);
        		v.like();
        		this.vinvetaservice.save(v);
        		this.userservice.save(user);}*/
        	return new ResponseEntity<>(v, HttpStatus.OK);
        }else{
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	
	}
	
	@CrossOrigin
	@JsonView(Vineta.BasicAtt.class)
	@RequestMapping(value = "/favorite/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Vineta> favorite(@PathVariable long id, HttpServletRequest request){
		System.out.println("ya he llegado");
		Principal p = request.getUserPrincipal();
		System.out.println(p.getName());
        User user = userservice.findByUsername(p.getName());
        Vineta v = vinvetaservice.findOne(id);
        if (v != null){
        	if (!v.isFavoritedBefore(user)){
        		System.out.println("not before");
        		user.getVinetas_favoritas().add(v);
        		this.vinvetaservice.save(v);
        		this.userservice.save(user);}
        	return new ResponseEntity<>(v, HttpStatus.OK);
        }else{
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	
	}
	

}
