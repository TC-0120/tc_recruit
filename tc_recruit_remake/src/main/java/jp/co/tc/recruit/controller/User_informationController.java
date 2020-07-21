package jp.co.tc.recruit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class User_informationController {
	@RequestMapping("/user_information")
	public String sendUserId() {
		return "user_information";
	}
}
