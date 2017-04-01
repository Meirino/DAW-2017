package es.urjc.code.daw.tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.code.daw.tag.*;

@Service
public class TagService {
	@Autowired
	private TagRepository repository;
	
	public List<Tag> findAll(){
		return repository.findAll();
	}
	public void save(Tag tag){
		repository.save(tag);
	}
	public Tag findOne(long id){
		return repository.findOne(id);
	}
	
	public Tag findByNombre(String nombre){
		return repository.findByNombre(nombre);
	}
	
	public void delete (long id){
		repository.delete(id);
	}

}
