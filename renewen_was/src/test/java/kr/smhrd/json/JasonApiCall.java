package kr.smhrd.json;

import java.net.*;
import java.io.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import kr.smhrd.renewen.api.WeatherService;

 
@ContextConfiguration(classes = JasonApiCall.class)
// API에 GET 요청을 보내고 응답을 받아오는 역할을 하는 클래스
public class JasonApiCall {
	
	@Autowired // 의존성주입
	private RestTemplate restTemplate; // 외부 API에 HTTP 요청 보내고, 응답 받음

	@Test
	public static void main(String[] args) throws Exception {

		// API URL 생성_URL+인증키
		URL url = new URL("https://apihub.kma.go.kr/api/json?authKey=-pjFeFmRQnmYxXhZkXJ5Pg");

		// HttpURLConnection 객체를 만들어서 API 호출
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// 요청 방식 GET
		con.setRequestMethod("GET");

		// 요청 헤더 설정_Content-Type을 application/json으로 설정해 JSON형식의 응답 요청
		con.setRequestProperty("Content-Type", "application/json");

		// API의 응답을 읽기 위한 BufferedReader를 생성해 응답을 읽어옴
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		// 응답 한 줄씩 읽고 StringBuffer에 추가
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		// 읽기가 끝나면 BufferedReader 닫고
		in.close();
		
		// 응답을 문자열 형태로 출력
		System.out.println(response.toString());

	}

}
