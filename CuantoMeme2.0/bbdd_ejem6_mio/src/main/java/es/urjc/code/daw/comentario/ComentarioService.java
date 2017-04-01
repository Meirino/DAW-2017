package es.urjc.code.daw.comentario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.code.daw.comentario.*;

@Service
public class ComentarioService {
	@Autowired
	private ComentarioRepository repository;
	
	public List<Comentario> findAll(){
		return repository.findAll();
	}
	public void save(Comentario c){
		repository.save(c);
	}
	
	public void delete(long id){
		repository.delete(id);
	}
	
	public Comentario findOne (long id){
		return repository.findOne(id);
	}
}

