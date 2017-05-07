package es.urjc.code.daw;

import es.urjc.code.daw.storage.StorageFileNotFoundException;
import es.urjc.code.daw.storage.StorageService;
import es.urjc.code.daw.tag.*;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.user.UserRepository;
import es.urjc.code.daw.vineta.Vineta;
import es.urjc.code.daw.vineta.VinetaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.List;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import es.urjc.code.daw.storage.*;
@Controller
public class FileUploadController {

    private final StorageService storageService;
    
    @Autowired
	private VinetaRepository vinetarepository;
    
    @Autowired
	private TagRepository tagrepository;
    
    @Autowired
    private UserRepository usuarios;
    @Autowired
	private S3Wrapper s3Wrapper;
    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
    

    /*@RequestMapping(value = "/subida", method = RequestMethod.GET)
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build().toString()).collect(Collectors.toList()));

        return "uploadForm";
    }*/

    @RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @RequestMapping(value = "/subida", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("titulo") String titulo, @RequestParam("desc") String desc ,@RequestParam("file") MultipartFile[] file, @RequestParam("tags") String tag, HttpServletRequest request,RedirectAttributes redirectAttributes) {
    	
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
    	s3Wrapper.upload(file);
    	Vineta viñeta = new Vineta(titulo, desc, "https://s3-eu-west-1.amazonaws.com/bucketdawfase5/"+file[0].getOriginalFilename());
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
        //storageService.store(file);
        //redirectAttributes.addFlashAttribute("message",
         //       "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/home";
    }
    
    @RequestMapping(value = "/subidaAvatar", method = RequestMethod.POST)
    public String handleAvatarUpload(@RequestParam("file") MultipartFile[] file, RedirectAttributes redirectAttributes, HttpServletRequest request) {

    	Principal p = request.getUserPrincipal();
    	User user = usuarios.findByUsername(p.getName());
    	user.setAvatarURL("https://s3-eu-west-1.amazonaws.com/bucketdawfase5/"+file[0].getOriginalFilename());
    	this.usuarios.save(user);
        //storageService.store(file);

        return "redirect:/home";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
