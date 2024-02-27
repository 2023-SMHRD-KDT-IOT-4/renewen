package kr.smhrd.renewen;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
 
@SpringBootTest
@SpringBootApplication
public class RenewenApplicationTest {
	
//	@Autowired
//	RestTemplate restTemplate;

	@Test
	public static void main(String[] args) {
		SpringApplication.run(RenewenApplicationTest.class, args);
	}
}
