package kr.smhrd.apiresttemplateservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import kr.smhrd.json.JasonApiCall;

@RestController
@SpringBootTest
@ContextConfiguration(classes = ApiService.class)
public class ApiService {
	 
	private final RestTemplate restTemplate;
	
	public ApiService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Test
	public void fetchDataFromApi() {
		String apiUrl = "https://apihub.kma.go.kr/api/typ01/url/kma_sfctm2.php?tm=202211300900&stn=0&help=1&authKey=-pjFeFmRQnmYxXhZkXJ5Pg";
		
		 ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

		 if (responseEntity.getStatusCode().is2xxSuccessful()) {
	            String responseBody = responseEntity.getBody();
	            // responseBody를 파싱하고 데이터 추출
	            System.out.println("API 응답: " + responseBody);
	        } else {
	            System.err.println("API 호출 실패: " + responseEntity.getStatusCode());
	        }
	    }
	 

}
