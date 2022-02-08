package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UsuarioRepository;
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner initData(ProductRepository productRepositorio, UsuarioRepository userRepositorio) {
		return (args) -> {			
			userRepositorio.save(new User(
										  /* Usuario */ 			"Salva", 
										  /* Contraseña */ 			"Tequinox",
										  /* Nombre */ 				"Salvador", 
										  /* Número de teléfono */ 	"643196361", 
										  /* Dirección */ 			"Calle Matallana", 
										  /* Correo */ 				"txaxati2018@gmail.com"
								));
			
			productRepositorio.save(new Product(
										/* Nombre del producto 					*/ "Prime Z690-A",
										/* Precio */ 							   149.99, 
										/* Nombre de la imagen 					*/ "Prime-Z690-A.png",
										/* Disponibilidad del mismo en unidades */ 9));
			
			// 
			
			productRepositorio.save(new Product("ProArt Z690", 120.17, "ProArt-Z690.png", 0));
			productRepositorio.save(new Product("Z690-PLUS", 179.75, "Z690-PLUS.png", 50));
			productRepositorio.save(new Product("WS-X299 PRO-SE", 49.99, "WS-X299-PRO-SE.png", 10));
			productRepositorio.save(new Product("H570-PRO", 49.99, "H570-PRO.png", 32));

		};
	}

}
