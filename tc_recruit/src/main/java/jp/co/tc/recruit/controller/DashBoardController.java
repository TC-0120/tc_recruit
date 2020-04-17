package jp.co.tc.recruit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.tc.recruit.repository.SelectionStatusRepository;

@Controller
public class DashBoardController {
	 @Autowired
	 HttpServletRequest request;

	 @Autowired
	 SelectionStatusRepository selRepo;

	@GetMapping("/dashboard")
	public String getLogin(Model model) {

		HttpSession session = request.getSession();
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authentication = securityContext.getAuthentication();

		model.addAttribute("authentication", authentication);
		model.addAttribute("selection", selRepo.findAll());


		return "/dashboard";
	}


	/*@PostMapping("/dashboard")
	public String postLogin(Model model) {

	    return "redirect:/";
	}*/



}
