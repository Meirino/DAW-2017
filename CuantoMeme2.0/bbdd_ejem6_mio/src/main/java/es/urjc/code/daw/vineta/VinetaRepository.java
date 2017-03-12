package es.urjc.code.daw.vineta;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.urjc.code.daw.user.User;

public interface VinetaRepository extends JpaRepository<Vineta, Long> {
	List<Vineta> findAllByOrderByCreationdateDesc();
	List<Vineta> findByTitulo(String titulo);

}
