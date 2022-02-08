package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.OrdersService;
import com.example.demo.service.UsersService;

@Controller
public class LoginController {
	
	@Autowired
	private UsersService servicioUsuarios;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private HttpSession session;
	
	// =========== Strings ===========
	
	private String newOrderString = "newOrder";

	
	/**
	 * Muestra el login
	 * @return
	 */
	@GetMapping({"/","/login"})
	public String login() {
		String result = "redirect:/menu";
		
		if (session.getAttribute("user") == null) {
			result = "login";
		}
		
		return result;
	}
	/**
	 * Inicia sesión (envio)
	 * @param user
	 * @param psk
	 * @param model
	 * @return
	 */
	@PostMapping({"/login"})
	public String login(@RequestParam("user") String user, @RequestParam("psk") String psk, Model model) {
		String result = null;
		
		if (servicioUsuarios.login(user, psk)) {
			session.setAttribute("user", user);
			result = "redirect:/menu";
		}
		else {
			model.addAttribute("err", true);
			result = "login";
		}
				
		return result;
	}
	/**
	 * Cierra sesión
	 * @return
	 */
	@GetMapping("/login/exit")
	public String exit() {
		if (session.getAttribute(newOrderString) != null) {
			if (ordersService.existsOrder((String) session.getAttribute("user"), (int) session.getAttribute(newOrderString))) {
				ordersService.remove((String) session.getAttribute("user"), ordersService.getOrderById((String) session.getAttribute("user"), (int) session.getAttribute(newOrderString)));
			}
			session.removeAttribute(newOrderString);
		}
		session.removeAttribute("user");
		return "redirect:/login";
	}
}
