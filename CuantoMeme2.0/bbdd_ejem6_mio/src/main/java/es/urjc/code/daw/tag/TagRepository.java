package es.urjc.code.daw.tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjc.code.daw.vineta.Vineta;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Tag findByNombre(String nombre);
}
