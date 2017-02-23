package com.practicaDAW2017.CuantoMeme;

import java.util.*;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VinetaRestController {
	
	@Autowired
	private VinetaRepository repositorioViñetas;
	
	@Autowired
	private ComentarioRepositoy repositorioComentarios;
	
	@PostConstruct
	public void init() {
		this.repositorioComentarios.save(new Comentario(1,"Javier94", "pole"));
		this.repositorioComentarios.save(new Comentario(2,"Lightning80", "Está genial"));
		this.repositorioViñetas.save(new Vineta(1, "Prueba", "UsuarioPrueba", "Esto es una viñeta de prueba", "LOL", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.repositorioViñetas.save(new Vineta(2, "Prueba2", "UsuarioPrueba2", "Esto es una viñeta de prueba", "LOL", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465",this.repositorioComentarios.findAll()));
	}
	
	public VinetaRestController() {}
	
	@RequestMapping(value = "/viñetas", method = RequestMethod.GET)
	public ResponseEntity<List<Vineta>> listaHeroes() {
		return new ResponseEntity<>(this.repositorioViñetas.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/viñetas/{id}", method = RequestMethod.GET)
	public ResponseEntity<Vineta> detalles(@PathVariable int id) {
		if(id <= this.repositorioViñetas.findAll().size()) {
			return new ResponseEntity<>(this.repositorioViñetas.findOne((long) id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/tags/{tag}", method = RequestMethod.GET)
	public ResponseEntity<List<Vineta>> listaTags(@PathVariable String tag) {
		ArrayList<Vineta> listaAux = new ArrayList<Vineta>();
		for (int i=1; i <= this.repositorioViñetas.findAll().size(); i++) {
			if(this.repositorioViñetas.findOne((long) i).getTag().equals(tag)) {
				listaAux.add(this.repositorioViñetas.findOne((long) i));
			}
		}
		if(listaAux.size() > 0) {
			return new ResponseEntity<>(listaAux, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
