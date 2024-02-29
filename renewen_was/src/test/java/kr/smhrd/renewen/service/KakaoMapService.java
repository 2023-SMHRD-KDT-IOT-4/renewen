package kr.smhrd.renewen.service;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.MediaType;

import java.util.Collections;

@SpringBootTest
@RestController
public class KakaoMapService {

    private RestTemplate restTemplate;

    public KakaoMapService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Test
    public String getKakaoSearch(String searchKeyword) {
        final String restAPIKey = "YOUR_REST_API_KEY";
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("query", searchKeyword);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + restAPIKey);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );
        return response.getBody();
    }
}
