package es.urjc.code.daw.api;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.storage.StorageService;
import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.tag.TagRepository;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserRepository;
import es.urjc.code.daw.vineta.Vineta;
import es.urjc.code.daw.vineta.VinetaRepository;

@RequestMapping("/api/")
@RestController
public class RESTVinetaController {
	
	interface VinetaView extends Vineta.BasicAtt, Vineta.UserAtt, User.BasicAtt, Vineta.TagAtt, Tag.BasicAtt, Vineta.ComentariosAtt, Comentario.BasicAtt, Comentario.UserAtt{}
	
	@Autowired
	private VinetaRepository vinetarepository;
    
    @Autowired
	private TagRepository tagrepository;
    
    @Autowired
    private UserRepository usuarios;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
    public void FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
	
	@JsonView(VinetaView.class)
	@RequestMapping(value = "vinetaspage/", method= RequestMethod.GET)
	public ResponseEntity<List<Vineta>> getvinetaspage(Pageable page ) {
		if(!this.vinetarepository.findAll().isEmpty()) {
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
	            //
	            e.printStackTrace();
	        }
			
			//Finally, this is not returning nothing
			return new ResponseEntity<>(this.vinetarepository.findAll(page).getContent(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@JsonView(Vineta.BasicAtt.class)
	@RequestMapping("vinetas/{id}")
	public ResponseEntity<Vineta> getvineta(@PathVariable int id){
		if(this.vinetarepository.findOne((long) id) != null) {
			return new ResponseEntity<>(this.vinetarepository.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(Vineta.BasicAtt.class)
	@RequestMapping(value = "vinetas", method = RequestMethod.POST)
	public ResponseEntity<Vineta> postVineta(@RequestParam("titulo") String titulo, @RequestParam("desc") String desc, @RequestParam("file") MultipartFile file, @RequestParam("tags") String tag, HttpServletRequest request){
		
		//Quito los espacios del string
    	tag = tag.trim();
    	
    	//Creo un tag provisional y un long
    	Tag tagTemp = new Tag(tag);
    	long indice = 0;
    	
    	//Creo un array de tags con todos los tags
    	ArrayList<Tag> tags = new ArrayList<Tag>();
    	tags = (ArrayList<Tag>) this.tagrepository.findAll();
    	
    	//Creo un booleano para ver si lo he encontrado
    	boolean found = false;
    	
    	//Comparo todos lo tags para ver si existe alguno con el mismo nombre. Si existe alguno con el mismo nombre, copio su id y pongo found a true
    	for(int i = 0; i < tags.size(); i++) {
    		if(tags.get(i).getNombre().equals(tag)) {
    			indice = tags.get(i).getId();
    			found = true;
    		}
    	}
    	
    	Vineta viñeta = new Vineta(titulo, desc, "/imgs/"+file.getOriginalFilename());
    	Principal p = request.getUserPrincipal();
    	User user = usuarios.findByUsername(p.getName());
    	viñeta.setAutor(user);
    	
    	//Si existe ya el tag, se añade a la viñeta, y sino se crea
    	if(found) {
    		viñeta.setTags(this.tagrepository.findOne(indice));
    	} else {
    		this.tagrepository.save(tagTemp);
    		viñeta.setTags(tagTemp);
    	};
    	
    	this.vinetarepository.save(viñeta);
        storageService.store(file);
        
        return new ResponseEntity<>(viñeta, HttpStatus.CREATED);
	}

}
