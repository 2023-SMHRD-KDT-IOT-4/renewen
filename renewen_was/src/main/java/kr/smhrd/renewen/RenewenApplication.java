package kr.smhrd.renewen;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class RenewenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenewenApplication.class, args);
	}

	@PostConstruct
    void started() {
        // timezone UTC 셋팅
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	
}
