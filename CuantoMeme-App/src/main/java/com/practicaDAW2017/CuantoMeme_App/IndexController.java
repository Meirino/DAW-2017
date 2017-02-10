package com.practicaDAW2017.CuantoMeme_App;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String greeting(Model model) {

		model.addAttribute("name", "Usuario");

		return "index";
	}

}
