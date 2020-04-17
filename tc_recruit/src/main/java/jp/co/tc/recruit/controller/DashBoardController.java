package jp.co.tc.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.tc.recruit.repository.SelectionStatusRepository;
import jp.co.tc.recruit.repository.TotalStatusRepository;

@Controller
public class DashBoardController {

	 @Autowired
	 SelectionStatusRepository slcStatusRepo;
	 @Autowired
	 TotalStatusRepository ttlStatusRepo;

	@GetMapping("/dashboard")
	public String getLogin(Model model) {
		model.addAttribute("selection", slcStatusRepo.findAll());
		model.addAttribute("first", ttlStatusRepo.findBySelectionStatusId(4));
		return "/dashboard";
	}


	/*@PostMapping("/dashboard")
	public String postLogin(Model model) {

	    return "redirect:/";
	}*/



}
