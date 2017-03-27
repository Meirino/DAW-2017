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
import es.urjc.code.daw.tag.*;

@RequestMapping("/api/tags")
@RestController
public class RESTTagController {
	
	interface TagView extends Tag.BasicAtt {};
	
	@Autowired
	private TagService tagservice;
	
	@JsonView(TagView.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<Tag>> getTags() {
		if(!this.tagservice.findAll().isEmpty()) {
			return new ResponseEntity<>(this.tagservice.findAll(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(TagView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Tag> getTagsByID(@PathVariable int id, HttpServletRequest request){
		Principal p = request.getUserPrincipal();
		System.out.println(p.getName());
		if(this.tagservice.findOne((long) id) != null) {
			return new ResponseEntity<>(this.tagservice.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(TagView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Tag> deleteTagsByID(@PathVariable int id){
		if(this.tagservice.findOne((long) id) != null) {
			this.tagservice.delete( id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
