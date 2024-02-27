package kr.smhrd.weathercontroller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import kr.smhrd.apiresttemplateservice.WeatherService;

@RestController
@SpringBootTest
public class WeatherController {
	
	@Autowired
	RestTemplate restTemplate;
	
	private final WeatherService weatherService;
	
	@Autowired
	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}
	
	@Test
	@GetMapping("/weather")
	public String getWeather() {
		// 기상청 API 호출
		String weatherData = weatherService.getWeatherData();
		return weatherData;
	}

}
