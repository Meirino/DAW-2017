package es.urjc.code.daw.vineta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.code.daw.vineta.VinetaRepository;;;

@Service
public class VinetaService {
	@Autowired
	private VinetaRepository repository;
	
	public void save(Vineta vineta){
		repository.save(vineta);
	}
	
	public List<Vineta> findAll(){
		return repository.findAll();
	}
	
	public Vineta findOne(long id){
		return repository.findOne(id);
	}
	
	public List<Vineta> findAllByOrderByCreationdateDesc(){
		return repository.findAllByOrderByCreationdateDesc();
	}
	public List<Vineta> findByTitulo(String titulo){
		return repository.findByTitulo(titulo);
	}
	
	public void delete(long id){
		repository.delete(id);
	}
}
