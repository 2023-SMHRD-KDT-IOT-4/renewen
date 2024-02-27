package kr.smhrd.application;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
 
@SpringBootTest
@SpringBootApplication
public class Application {
	
//	@Autowired
//	RestTemplate restTemplate;

	@Test
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
