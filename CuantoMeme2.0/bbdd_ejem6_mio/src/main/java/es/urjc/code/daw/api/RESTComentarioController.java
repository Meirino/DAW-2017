package es.urjc.code.daw.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.comentario.ComentarioRepository;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.vineta.Vineta;

@RequestMapping("/api/")
@RestController
public class RESTComentarioController {
	
	interface ComentarioView extends Comentario.BasicAtt, Comentario.UserAtt, User.BasicAtt, Comentario.VinetaAtt, Vineta.BasicAtt{}
	
	@Autowired
	private ComentarioRepository comentariorepository;
	
	@JsonView(ComentarioView.class)
	@RequestMapping(value = "comentarios", method = RequestMethod.GET)
	public ResponseEntity<List<Comentario>> getComentarios(){
		if(!this.comentariorepository.findAll().isEmpty()) {
			return new ResponseEntity<>(this.comentariorepository.findAll(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(ComentarioView.class)
	@RequestMapping(value = "comentarios/{id}", method = RequestMethod.GET)
	public ResponseEntity<Comentario> getComentariosByID(@PathVariable int id){
		if(this.comentariorepository.findOne((long) id) != null) {
			return new ResponseEntity<>(this.comentariorepository.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(ComentarioView.class)
	@RequestMapping(value = "comentarios/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Comentario> modificarComentario(@PathVariable int id){
		if(this.comentariorepository.findOne((long) id) != null) {
			return new ResponseEntity<>(this.comentariorepository.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
