package kr.smhrd.renewen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class RenewenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenewenApplication.class, args);
	}

	@PostConstruct
    void started() {
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	
}
