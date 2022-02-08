package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsersService {
	
	// ============================ Repositorios ============================

	@Autowired
	private UsuarioRepository userRepository;
		
	/**
	 * Inicia sesión a un usuario
	 * @param user Usuario
	 * @param psk Contraseña
	 * @return
	 */
	public boolean login(String user, String psk) {
		boolean isLoginSuccess = false;
		
		User userLogin = userRepository.findById(user).orElse(null);
		
		if (userLogin != null && userLogin.isPskValid(psk)) {
			isLoginSuccess = true;
		}
		
		return isLoginSuccess;
	}
	/**
	 * Obtiene un usuario por su nombre de usuario
	 * @param username Usuario
	 * @return Usuario
	 */
	public User getByUsername(String username) {
		return userRepository.getById(username);
	}
	/**
	 * Obtiene sus pedidos
	 * @param username Usuario
	 * @return List de pedidos
	 */
	public List<Order> getOrders(String username) {
		User user = userRepository.getById(username);
		return user.getOrders();
	}
}
