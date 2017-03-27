package es.urjc.code.daw.api;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.tag.TagRepository;

@RequestMapping("/api/")
@RestController
public class RESTTagController {
	
	interface TagView extends Tag.BasicAtt {};
	
	@Autowired
	private TagRepository tagrepository;
	
	@JsonView(TagView.class)
	@RequestMapping(value = "tags", method = RequestMethod.GET)
	public ResponseEntity<List<Tag>> getTags() {
		if(!this.tagrepository.findAll().isEmpty()) {
			return new ResponseEntity<>(this.tagrepository.findAll(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(TagView.class)
	@RequestMapping(value = "tags/{id}", method = RequestMethod.GET)
	public ResponseEntity<Tag> getTagsByID(@PathVariable int id, HttpServletRequest request){
		Principal p = request.getUserPrincipal();
		System.out.println(p.getName());
		if(this.tagrepository.findOne((long) id) != null) {
			return new ResponseEntity<>(this.tagrepository.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(TagView.class)
	@RequestMapping(value = "tags/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Tag> deleteTagsByID(@PathVariable int id){
		if(this.tagrepository.findOne((long) id) != null) {
			this.tagrepository.delete(this.tagrepository.findOne((long) id));
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(TagView.class)
	@RequestMapping("tags/{nombre}")
	public ResponseEntity<List<Tag>> getTagsByName(@PathVariable String nombre){
		if(!this.tagrepository.findByNombre(nombre).isEmpty()) {
			return new ResponseEntity<>(this.tagrepository.findByNombre(nombre), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
