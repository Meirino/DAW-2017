package com.practicaDAW2017.CuantoMeme.usuarios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.practicaDAW2017.CuantoMeme.*;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nombre;
	private String email;
	private String avatarUrl;
	private ArrayList<Long> seguidos;
	private ArrayList<Long> seguidores;
	private ArrayList<Long> favoritas;
	private ArrayList<Long> publicadas;
	
	public Usuario() {}
	
	public Usuario(long id, String nombre, String email, String avatarUrl, ArrayList<Long> seguidos, ArrayList<Long> favoritos, ArrayList<Long> publicadas, ArrayList<Long> seguidores) {
		this.setId(id);
		this.setNombre(nombre);
		this.setEmail(email);
		this.setAvatarUrl(avatarUrl);
		this.setSeguidos(seguidos);
		this.setFavoritas(favoritos);
		this.setPublicadas(publicadas);
		this.setSeguidores(seguidores);
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public ArrayList<Long> getSeguidos() {
		return seguidos;
	}
	public void setSeguidos(ArrayList<Long> seguidos) {
		this.seguidos = seguidos;
	}
	public ArrayList<Long> getFavoritas() {
		return favoritas;
	}
	public void setFavoritas(ArrayList<Long> favoritos) {
		this.favoritas = favoritos;
	}
	public ArrayList<Long> getPublicadas() {
		return publicadas;
	}
	public void setPublicadas(ArrayList<Long> publicadas) {
		this.publicadas = publicadas;
	}
	public ArrayList<Long> getSeguidores() {
		return seguidores;
	}
	public void setSeguidores(ArrayList<Long> seguidores) {
		this.seguidores = seguidores;
	}
}
