package kr.smhrd.renewen.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.smhrd.renewen.service.KakaoMapService;

@SpringBootTest
@RestController
public class KakaoMapController {
	
	private final KakaoMapService kakaoMapService;
	
	public KakaoMapController(KakaoMapService kakaMapService) {
		this.kakaoMapService = kakaMapService;
	}
	
	@GetMapping("/kakaomap")
	public String getKakaoSearch(@RequestParam("keyword") String keyword) {
		return kakaoMapService.getKakaoSearch(keyword);
	}

}
