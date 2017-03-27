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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.comentario.*;
import es.urjc.code.daw.user.*;
import es.urjc.code.daw.vineta.*;

@RequestMapping("/api/comentarios")
@RestController
public class RESTComentarioController {
	
	interface ComentarioView extends Comentario.BasicAtt, Comentario.UserAtt, User.BasicAtt, Comentario.VinetaAtt, Vineta.BasicAtt{}
	
	@Autowired
	private ComentarioService comentarioservice;
	
	@Autowired
	private UserService userRepository;
	
	@Autowired
	private VinetaService vinetarepository;
	
	@JsonView(ComentarioView.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<Comentario>> getComentarios(){
		if(!this.comentarioservice.findAll().isEmpty()) {
			return new ResponseEntity<>(this.comentarioservice.findAll(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(ComentarioView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Comentario> getComentariosByID(@PathVariable int id){
		if(this.comentarioservice.findOne((long) id) != null) {
			return new ResponseEntity<>(this.comentarioservice.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(ComentarioView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Comentario> modificarComentario(@PathVariable int id, @RequestParam("texto") String texto, HttpServletRequest request) {
		Principal p = request.getUserPrincipal();
    	User user = this.userRepository.findByUsername(p.getName());
    	
		if(this.comentarioservice.findOne((long) id) != null && (this.comentarioservice.findOne((long) id).getAutor_comentario().equals(user) || user.getRoles().contains("ROLE_ADMIN"))) {
			Comentario original = this.comentarioservice.findOne((long) id);
			original.setComentario(texto);
			this.comentarioservice.save(original);
			return new ResponseEntity<>(original, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
	}
	
	@JsonView(ComentarioView.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Comentario> eliminarComentario(@PathVariable int id, HttpServletRequest request){
		Principal p = request.getUserPrincipal();
    	User user = this.userRepository.findByUsername(p.getName());
		
		if(this.comentarioservice.findOne((long) id) != null && (this.comentarioservice.findOne((long) id).getAutor_comentario().equals(user) || user.getRoles().contains("ROLE_ADMIN"))) {
			Comentario original = this.comentarioservice.findOne((long) id);
			this.comentarioservice.delete((long) id);
			return new ResponseEntity<>(original, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	/*
	@JsonView(ComentarioView.class)
	@RequestMapping(value = "comentariosByUser/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Comentario>> comentariosDeUsuario(@PathVariable int id){
		if(this.userRepository.findOne((long) id) != null) {
			return new ResponseEntity<>(this.userRepository.findOne((long) id).getComentarios(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(ComentarioView.class)
	@RequestMapping(value = "comentariosByVineta/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Comentario>> comentariosDeVineta(@PathVariable int id){
		if(this.vinetarepository.findOne((long) id) != null) {
			return new ResponseEntity<>(this.vinetarepository.findOne((long) id).getComentarios(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	*/
}
