package es.urjc.code.daw.vineta;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.urjc.code.daw.user.User;

public interface VinetaRepository extends JpaRepository<Vineta, Long> {
	List<Vineta> findAllByOrderByCreationdateDesc();
    //@Query("select * from user_vinetas_gustadas t where t.users_likes_id = ?1")
    //@Query("select t from Team t where t.name = ?1")
	  //@Query("select u from user_vinetas_gustadas where t.users_likes_id = ?1 and t.vinetas_gustadas_id = ?2;")
	  //List<User> findIfIsLikedByUser(int user_id, int vineta_id);
}
