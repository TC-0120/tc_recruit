package jp.co.tc.recruit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/dashboard";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/dashboard")
	public String top() {
		return "/dashboard";
	}
}
