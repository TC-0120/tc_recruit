package jp.co.tc.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tc.recruit.form.UserForm;
import jp.co.tc.recruit.service.UserService;

@Controller
@RequestMapping("/password")
public class PasswordController {

	@Autowired
	UserService usrSvc;

	@GetMapping()
	public String getPasswordSet(Model model) {
		return "/password_setting";
	}

	@PostMapping("regist")
	public String PasswordRegist(@ModelAttribute("passwordRegist") UserForm userForm, Model model) {
		String password = userForm.getPassword().get(0);
		String passwordConfirm = userForm.getPassword().get(1);
		String username = userForm.getUsername().get(0);
		String message = "入力内容が異なります";

		if(password.equals(passwordConfirm)) {
			usrSvc.passwordRegist(password, username);
		} else {
			model.addAttribute("message", message);
			return "/password_setting";
		}

		return "/login";
	}
}
