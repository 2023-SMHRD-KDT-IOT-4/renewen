package kr.smhrd.renewen.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import kr.smhrd.renewen.api.WeatherService;

@RestController
@SpringBootTest
// RESTful API를 제공하는 컨트롤러 클래스
public class ArduAPIControllerTest {
	
	@Autowired
	RestTemplate restTemplate;
	
	private final WeatherService weatherService;
	
	@Autowired
	public ArduAPIControllerTest(WeatherService weatherService) {
		this.weatherService = weatherService;
	}
	
	@Test
	@GetMapping("/weather")
	public String getWeather() {
		// 기상청 API 호출
//		String weatherData = weatherService.getWeatherData();
		return "";
	}

}
