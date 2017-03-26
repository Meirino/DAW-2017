package es.urjc.code.daw.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.code.daw.user.UserRepository;;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public User findOne(long id) {
		return repository.findOne(id);
	}
	
	public void save(User  user){
		repository.save(user);
	}
	
	public User findByUsername(String username){
		return repository.findByUsername(username);
	}
	public void delete(long id){
		repository.delete(id);
	}
}
