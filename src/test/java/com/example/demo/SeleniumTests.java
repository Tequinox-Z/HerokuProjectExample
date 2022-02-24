package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class SeleniumTests {
	
	private static WebDriver driver;
	
	@BeforeAll
	static void inici() {
		System.setProperty("webdriver.chrome.driver", "/home/tequinox/Documents/workspace-spring-tool-suite-4-4.13.0.RELEASE/WebVenta/drivers/chromedriver");

		driver = new ChromeDriver();
		
	}
	
	@Test
	void test2() {
		
		driver.get("http://localhost:8090");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(true);
	}

}
