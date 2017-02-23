package com.practicaDAW2017.CuantoMeme.usuarios;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practicaDAW2017.CuantoMeme.Comentario;
import com.practicaDAW2017.CuantoMeme.ComentarioRepositoy;
import com.practicaDAW2017.CuantoMeme.Vineta;
import com.practicaDAW2017.CuantoMeme.VinetaRepository;

@Controller
public class UsuarioController {
	
	@Autowired
	private ComentarioRepositoy repositorioComentarios;
	
	@Autowired
	private VinetaRepository repositorioVinetas;
	
	@Autowired
	private UsuarioRepository repositorioUsuario;
	
	@PostConstruct
	public void init() {
		this.repositorioComentarios.save(new Comentario(1,"Javier94", "pole"));
		this.repositorioComentarios.save(new Comentario(2,"Lightning80", "Está genial"));
		this.repositorioVinetas.save(new Vineta(1, "Prueba", "UsuarioPrueba", "Esto es una viñeta de prueba", "LOL", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.repositorioVinetas.save(new Vineta(2, "Prueba2", "UsuarioPrueba2", "Esto es una viñeta de prueba", "LOL", "http://i0.kym-cdn.com/photos/images/newsfeed/000/125/163/ragek.jpg?1318992465"));
		this.repositorioUsuario.save(new Usuario((long) 1, "Meirino94", "Meirino94@gmail.com", "AvatarUrl", new ArrayList<Long>(Arrays.asList((long) 1)), new ArrayList<Long>(Arrays.asList((long) 1)), new ArrayList<Long>(Arrays.asList((long) 1)), new ArrayList<Long>(Arrays.asList((long) 1))));
	}
	
	@RequestMapping("/perfil/{id}")
	public String perfil(Model model, @PathVariable int id) {
		//ConseguirUsuario
		Usuario usuario = this.repositorioUsuario.findOne((long) id);
		
		//Conseguir a los usuarios seguidos
		ArrayList<Usuario> seguidos = new ArrayList<Usuario>();
		for(int i = 0; i < usuario.getSeguidos().size(); i++) {
			seguidos.add(this.repositorioUsuario.findOne(usuario.getSeguidos().get(i+1)));
		}
		
		//Conseguir las viñetas de seguidores (Reemplazar más adelante)
		ArrayList<Vineta> seguidosVi = new ArrayList<Vineta>();
		for(int i = 0; i < usuario.getSeguidores().size(); i++) {
			seguidosVi.add(this.repositorioVinetas.findOne(usuario.getSeguidores().get(i+1)));
		}
		
		//Conseguir las viñetas favoritas (Reemplazar más adelante)
		ArrayList<Vineta> favoritas = new ArrayList<Vineta>();
		for(int i = 0; i < usuario.getFavoritas().size(); i++) {
			favoritas.add(this.repositorioVinetas.findOne(usuario.getFavoritas().get(i+1)));
		}
		
		//Conseguir las viñetas publicadas (Reemplazar más adelante)
		ArrayList<Vineta> publicadas = new ArrayList<Vineta>();
		for(int i = 0; i < usuario.getPublicadas().size(); i++) {
			publicadas.add(this.repositorioVinetas.findOne(usuario.getPublicadas().get(i+1)));
		}
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("usuariosSeguidos",seguidos);
		model.addAttribute("vinetasSeguidores", seguidosVi);
		model.addAttribute("favoritas", favoritas);
		model.addAttribute("publicadas", publicadas);
		
		return "perfil";
	}

}
