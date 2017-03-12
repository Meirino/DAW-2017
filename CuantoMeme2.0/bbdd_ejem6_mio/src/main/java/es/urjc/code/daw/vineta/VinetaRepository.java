package es.urjc.code.daw.vineta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VinetaRepository extends JpaRepository<Vineta, Long> {
	List<Vineta> findAllByOrderByCreationdateDesc();
	List<Vineta> findByTitulo(String titulo);

}
