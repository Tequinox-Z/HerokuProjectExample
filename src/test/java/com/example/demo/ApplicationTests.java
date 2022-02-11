package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.User;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(new User("Salva").getUsername().equals("Salv"));
	}

}
