package com.ot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.ot.model.Staff;

@Controller
public class IndexController {
	

	@GetMapping("/")
	public String index() {
		return "home";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("staff", new Staff());
		return "login";
	}

	@GetMapping("/account")
	public String account() {
		return "util/account";
	}

	@GetMapping("/error")
	public String handleError() {
		return "error";
	}

	

}
