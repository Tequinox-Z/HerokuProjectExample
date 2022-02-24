package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@Autowired
	private HttpSession session;
	
	// =========== Strings ===========
	
	private String newOrderString = "newOrder";

	
	/**
	 * Muestra el login
	 * @return
	 */
	@GetMapping({"/","login"})
	public String login() {
		String result = "redirect:/menu";
		
		if (session.getAttribute("user") == null) {
			result = "login";
		}
		
		return result;
	}

}
