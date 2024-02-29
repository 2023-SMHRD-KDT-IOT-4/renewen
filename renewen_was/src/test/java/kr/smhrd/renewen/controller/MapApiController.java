package kr.smhrd.renewen.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@RestController
public class MapApiController {
	
	@Autowired
	RestTemplate restTemplate;

//	@GetMapping("/location")
//	public ResponseEntity<Object> getlocation(@RequestParam String placeAddress) {
//		try {
//			String encodedAddress = URLEncoder.encode(placeAddress, "UTF-8");
//			String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + encodeURI(placeAddress);
//			String kakaoApiKey = "YOUR_KAKAO_API_KEY";
//		
//			HttpHeaders headers = new HttpHeaders();
//			
//			RestTemplate entity = new RestTemplate();
//			ResponseEntity<String> response = restTemplate.getForEntity(url, HttpMethod.GET, entity, String.class);
//			
//			return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
//			
//		} catch (UnsupportedEncodingException e) {
//			return new ResponseEntity<>("Error encoding the address", HttpStatus.INTERNAL_SERVER_ERROR);
//		} 	
//	}

 
//	@Test
//	public ResponseEntity<Object> getLALOInfo(@RequestParam String placeAddress) {
//        try {
//            String encodedAddress = URLEncoder.encode(placeAddress, "UTF-8");
//            String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + encodedAddress;
//            String kakaoApiKey = "YOUR_KAKAO_API_KEY";
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "KakaoAK " + kakaoApiKey);
//
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//
//            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
//        } catch (UnsupportedEncodingException e) {
//            return new ResponseEntity<>("Error encoding the address", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
	
 
