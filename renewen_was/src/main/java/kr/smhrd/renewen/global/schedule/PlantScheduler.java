package kr.smhrd.renewen.global.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kr.smhrd.renewen.service.PlantService;

@Component
public class PlantScheduler {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PlantService plantService;
	
	@Autowired
	RestTemplate restTemplate;

	/*
	 * // fetch sensor data // 5sec 간격
	 * 
	 * @Scheduled(fixedRate = 10000) public void callApi() {
	 * 
	 * String reqUrl = "https://5d553fd936ad770014ccde7a.mockapi.io/smhrd/sensors";
	 * String response = restTemplate.getForObject(reqUrl,String.class);
	 * log.info("dddd {}", response);
	 * 
	 * }// callApi
	 * 
	 */	
}