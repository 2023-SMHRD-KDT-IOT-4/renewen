package kr.smhrd.renewen.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import kr.smhrd.renewen.model.SensingDataVO;

@SpringBootTest
// 서비스 계층의 테스트를 위한 클래스
public class ArduAPIServiceTest {

	private final String apiUrl = "https://apihub.kma.go.kr/api/typ01/url/kma_sfctm2.php?tm=202211300900&stn=0&help=1&authKey=-pjFeFmRQnmYxXhZkXJ5Pg";
	
	@Autowired
	private RestTemplate restTemplate;

	@Test
	@DisplayName("ddd")
	public void ddd() {
		assertNotNull(11);
	}
	
	@Test
	public void getWeatherData() {
		// 기상청 API 호출 및 데이터 수신
		String weatherData = restTemplate.getForObject(apiUrl, String.class);
		System.out.println(weatherData);
	}
}
