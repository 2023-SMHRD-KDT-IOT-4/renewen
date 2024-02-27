package kr.smhrd.apiresttemplateservice;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

 
@SpringBootTest
@ContextConfiguration(classes = WeatherService.class)
public class WeatherService {

	 
	private final String apiUrl = "https://apihub.kma.go.kr/api/typ01/url/kma_sfctm2.php?tm=202211300900&stn=0&help=1&authKey=-pjFeFmRQnmYxXhZkXJ5Pg";
	
	private final RestTemplate restTemplate;

	
	public WeatherService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Test
	public void getWeatherData() {
		// 기상청 API 호출 및 데이터 수신
		String weatherData = restTemplate.getForObject(apiUrl, String.class);
		System.out.println(weatherData);
	}





}
