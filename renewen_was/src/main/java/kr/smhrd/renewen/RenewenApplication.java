package kr.smhrd.renewen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RenewenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenewenApplication.class, args);
	}

}
