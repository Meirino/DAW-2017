package com.practicaDAW2017.CuantoMeme;

import java.util.*;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VinetaController {
	
	@Autowired
	private ComentarioRepositoy repositorioComentarios;
	
	@Autowired
	private VinetaRepository repositorioVinetas;
	
	@PostConstruct
	public void init() {
		this.repositorioComentarios.save(new Comentario(1,"Javier94", "pole"));
		this.repositorioComentarios.save(new Comentario(2,"Lightning80", "Está genial"));
		this.repositorioVinetas.save(new Vineta(1, "Prueba", "UsuarioPrueba", "Esto es una viñeta de prueba", "LOL", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.repositorioVinetas.save(new Vineta(2, "Prueba2", "UsuarioPrueba2", "Esto es una viñeta de prueba", "LOL", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
	}

	public VinetaController() {}
	
	@RequestMapping("/")
	public String indice(Model model) {
	
		model.addAttribute("vinetas", this.repositorioVinetas.findAll());

		return "index";
	}
	
	@RequestMapping("/vineta/{id}")
	public String detalles(Model model, @PathVariable int id) {
		Vineta vineta = this.repositorioVinetas.findOne((long) id);
		model.addAttribute("vineta", vineta);
		return "detalles";
	}
	
	@RequestMapping("/tag/{tag}")
	public String listaTags(Model model, @PathVariable String tag) {
		ArrayList<Vineta> listaAux = new ArrayList<Vineta>();
		for (int i=1; i <= this.repositorioVinetas.findAll().size(); i++) {
			if(this.repositorioVinetas.findOne((long) i).getTag().equals(tag)) {
				listaAux.add(this.repositorioVinetas.findOne((long) i));
			}
		}
		model.addAttribute("lista", listaAux);
		model.addAttribute(tag);
		
		return "tagIndex";
	}
}
