package es.urjc.code.daw.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.code.daw.api.CMRestControler.VinetaView;
import es.urjc.code.daw.comentario.Comentario;
import es.urjc.code.daw.tag.Tag;
import es.urjc.code.daw.user.User;
import es.urjc.code.daw.vineta.Vineta;
import es.urjc.code.daw.vineta.VinetaRepository;

@RequestMapping("/api/")
@RestController
public class RESTVinetaController {
	
	interface VinetaView extends Vineta.BasicAtt, Vineta.UserAtt, User.BasicAtt, Vineta.TagAtt, Tag.BasicAtt, Vineta.ComentariosAtt, Comentario.BasicAtt, Comentario.UserAtt{}
	
	@Autowired
	private VinetaRepository vinetarepository;
	
	@JsonView(VinetaView.class)
	@RequestMapping(value = "vinetaspage/", method= RequestMethod.GET)
	public List<Vineta> getvinetaspage(Pageable page ){
		//This System.out print all the size of my repository
		System.out.println(this.vinetarepository.findAll().size());
		//This for print the title for my 20 first objects Vineta
		for(Vineta v:this.vinetarepository.findAll(page)){
			System.out.println(v.getTitulo());
		}
		// This print 20
		System.out.println(this.vinetarepository.findAll(page).getSize());
		//This print the page number = 0 
		System.out.println(page.getPageNumber());
		//This print the page size = 20 
		System.out.println(page.getPageSize());
		
		Integer seconds = 2;

        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		//Finally, this is not returning nothing
		return vinetarepository.findAll(page).getContent();
	}

}
