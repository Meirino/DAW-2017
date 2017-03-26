package es.urjc.code.daw.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.api.CMRestControler.TagView;
import es.urjc.code.daw.api.CMRestControler.UserView;
import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.tag.TagRepository;
import es.urjc.code.daw.user.User;

@RequestMapping("/api/")
@RestController
public class RESTTagController {
	
	@Autowired
	private TagRepository tagrepository;
	
	@JsonView(TagView.class)
	@RequestMapping(value = "tags", method = RequestMethod.GET)
	public ResponseEntity<List<Tag>> getTags(){
		if(!this.tagrepository.findAll().isEmpty()) {
			return new ResponseEntity<>(this.tagrepository.findAll(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(TagView.class)
	@RequestMapping(value = "tags/{id}", method = RequestMethod.GET)
	public ResponseEntity<Tag> getTagsByID(@PathVariable int id){
		if(this.tagrepository.findOne((long) id) != null) {
			return new ResponseEntity<>(this.tagrepository.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
