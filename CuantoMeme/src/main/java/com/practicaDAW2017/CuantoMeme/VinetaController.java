package com.practicaDAW2017.CuantoMeme;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VinetaController {
	private List<Vineta> listaVinetas = new ArrayList<Vineta>();
	private List<Comentario> comentarios = new ArrayList<Comentario>(); 
	
	public VinetaController() {
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
		this.listaVinetas.add(new Vineta(15, "Prueba 15", "UsuarioPrueba", "Esto es una viñeta de prueba", "Test", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465", this.comentarios));
	}
	
	@RequestMapping("/")
	public String indice(Model model) {
	
		model.addAttribute("vinetas", this.listaVinetas);

		return "index";
	}
	
	@RequestMapping("/vineta/{id}")
	public String detalles(Model model, @PathVariable int id) {
		Vineta vineta = this.listaVinetas.get(id - 1);
		model.addAttribute("vineta", vineta);
		return "detalles";
	}
	
	@RequestMapping("/tag/{tag}")
	public String listaTags(Model model, @PathVariable String tag) {
		ArrayList<Vineta> listaAux = new ArrayList<Vineta>();
		for (int i=0; i < this.listaVinetas.size(); i++) {
			if(this.listaVinetas.get(i).getTag().equals(tag)) {
				listaAux.add(this.listaVinetas.get(i));
			}
		}
		model.addAttribute("lista", listaAux);
		model.addAttribute(tag);
		
		return "tagIndex";
	}
}
