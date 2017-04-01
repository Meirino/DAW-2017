package es.urjc.code.daw.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.api.CMRestControler.VinetaView;
import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.utils.utils;
import es.urjc.code.daw.vineta.Vineta;

@RequestMapping("/api/busqueda")
@RestController
public class RESTBusquedaController {
	
	@Autowired
	private utils utilidades;
	
	@JsonView(VinetaView.class)
	@RequestMapping(value = "/vinetasTitulo/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<Vineta>> buscarVineta(@PathVariable String nombre){
		if(!this.utilidades.busquedaVineta(nombre).isEmpty()) {
			return new ResponseEntity<>(this.utilidades.busquedaVineta(nombre), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(VinetaView.class)
	@RequestMapping(value = "/vinetasUsuario/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<Vineta>> buscarVinetaPorUsuario(@PathVariable String nombre){
		if(!this.utilidades.busquedaVinetasPorUsuarios(nombre).isEmpty()) {
			return new ResponseEntity<>(this.utilidades.busquedaVinetasPorUsuarios(nombre), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(VinetaView.class)
	@RequestMapping(value = "/vinetasTag/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<Vineta>> buscarVinetaPorTag(@PathVariable String nombre){
		if(!this.utilidades.busquedaVinetasPorTags(nombre).isEmpty()) {
			return new ResponseEntity<>(this.utilidades.busquedaVinetasPorTags(nombre), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(VinetaView.class)
	@RequestMapping(value = "/usuarios/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> buscarUsuario(@PathVariable String nombre){
		if(!this.utilidades.busquedaUsuarios(nombre).isEmpty()) {
			return new ResponseEntity<>(this.utilidades.busquedaUsuarios(nombre), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(VinetaView.class)
	@RequestMapping(value = "/tags/{nombre}", method = RequestMethod.GET)
	public ResponseEntity<List<Tag>> buscarTag(@PathVariable String nombre){
		if(!this.utilidades.busquedaTags(nombre).isEmpty()) {
			return new ResponseEntity<>(this.utilidades.busquedaTags(nombre), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
