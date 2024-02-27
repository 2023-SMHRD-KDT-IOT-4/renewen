package kr.smhrd.json;

import java.net.*;
import java.io.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import kr.smhrd.renewen.api.WeatherService;

 
@SpringBootTest
@ContextConfiguration(classes = JasonApiCall.class)
// API에 GET 요청을 보내고 응답을 받아오는 역할을 하는 클래스
public class JasonApiCall {
	
	@Autowired
	private RestTemplate restTemplate; // 외부 API에 요청을 보냄

	@Test
	public static void main(String[] args) throws Exception {

		// API URL 생성_URL+인증키
		URL url = new URL("https://apihub.kma.go.kr/api/json?authKey=-pjFeFmRQnmYxXhZkXJ5Pg");

		// HttpURLConnection 객체를 만들어서 API 호출
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// 요청 방식 GET
		con.setRequestMethod("GET");

		// 요청 헤더 설정_Content-Type을 application/json으로 설정
		con.setRequestProperty("Content-Type", "application/json");

		// API의 응답을 읽기 위한 BufferedReader를 생성
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		// BufferedReader 닫기
		in.close();
		
		System.out.println(response.toString());

	}

}
