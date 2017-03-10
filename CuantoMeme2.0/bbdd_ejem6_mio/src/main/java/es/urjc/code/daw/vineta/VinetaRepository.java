package es.urjc.code.daw.vineta;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VinetaRepository extends JpaRepository<Vineta, Long> {
	Page<Vineta> findAllByOrderByCreationdateDesc(Pageable page);
}
