package com.practicaDAW2017.CuantoMeme;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VinetaRestController {
	private List<Vineta> listaVinetas = new ArrayList<Vineta>();
	private List<Comentario> comentarios = new ArrayList<Comentario>(); 
	
	public VinetaRestController() {
		this.listaVinetas.add(new Vineta(1, "Prueba", "UsuarioPrueba", "Esto es una viñeta de prueba", "LOL", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(2, "Prueba 2", "UsuarioPrueba2", "Esto es otra viñeta de prueba", "LOL", "http://theamericanreader.com/wp-content/uploads/2012/10/rage_comics.jpg"));
		this.listaVinetas.add(new Vineta(3, "Prueba 3", "UsuarioPrueba", "Esto es una viñeta de prueba", "LOL", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(4, "Prueba 4", "UsuarioPrueba", "Esto es una viñeta de prueba", "Inglip", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(5, "Prueba 5", "UsuarioPrueba", "Esto es una viñeta de prueba", "Pepe", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(6, "Prueba 6", "UsuarioPrueba", "Esto es una viñeta de prueba", "Inglip", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(7, "Prueba 7", "UsuarioPrueba", "Esto es una viñeta de prueba", "Pepe", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(8, "Prueba 8", "UsuarioPrueba", "Esto es una viñeta de prueba", "LOL", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(9, "Prueba 9", "UsuarioPrueba", "Esto es una viñeta de prueba", "LOL", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(10, "Prueba 10", "UsuarioPrueba", "Esto es una viñeta de prueba", "Pepe", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(11, "Prueba 11", "UsuarioPrueba", "Esto es una viñeta de prueba", "Inglip", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(12, "Prueba 12", "UsuarioPrueba", "Esto es una viñeta de prueba", "Pepe", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(13, "Prueba 13", "UsuarioPrueba", "Esto es una viñeta de prueba", "Inglip", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.listaVinetas.add(new Vineta(14, "Prueba 14", "UsuarioPrueba", "Esto es una viñeta de prueba", "Pepe", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.comentarios.add(new Comentario(1,"Javier94", "pole"));
		this.comentarios.add(new Comentario(2,"Lightning80", "Está genial"));
		this.comentarios.add(new Comentario(3,"Kike90","+1"));
		this.comentarios.add(new Comentario(4,"KaiY", "Está fatal"));
		this.comentarios.add(new Comentario(5,"Char", "No está mal"));
		this.comentarios.add(new Comentario(6,"TakumiTofuboy90", "Me gusta"));
		this.comentarios.add(new Comentario(7,"TakeshiKiddo", "Mis dieces"));
		this.comentarios.add(new Comentario(8,"Alien99", "Mis miles"));
		this.comentarios.add(new Comentario(9,"Kinzo88", "Está muy guapa"));
		this.comentarios.add(new Comentario(10,"Pepe1488","Le doy todos mis internets"));
		this.listaVinetas.add(new Vineta(15, "Prueba 15", "Pepito88", "Esto es una viñeta de prueba", "Test", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465", this.comentarios));
		this.listaVinetas.add(new Vineta(15, "Prueba 16", "Javier94", "Esto es una viñeta de prueba", "Test", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465", this.comentarios));
	}
	
	@RequestMapping(value = "/viñetas", method = RequestMethod.GET)
	public ResponseEntity<List<Vineta>> listaHeroes() {
		return new ResponseEntity<>(this.listaVinetas, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/viñetas/{id}", method = RequestMethod.GET)
	public ResponseEntity<Vineta> detalles(@PathVariable int id) {
		if(id <= this.listaVinetas.size()) {
			return new ResponseEntity<>(this.listaVinetas.get(id - 1), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/tag/{tag}", method = RequestMethod.GET)
	public ResponseEntity<List<Vineta>> listaTags(@PathVariable String tag) {
		ArrayList<Vineta> listaAux = new ArrayList<Vineta>();
		for (int i=0; i < this.listaVinetas.size(); i++) {
			if(this.listaVinetas.get(i).getTag().equals(tag)) {
				listaAux.add(this.listaVinetas.get(i));
			}
		}
		if(listaAux.size() > 0) {
			return new ResponseEntity<>(listaAux, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
