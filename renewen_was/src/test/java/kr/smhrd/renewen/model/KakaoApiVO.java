package kr.smhrd.renewen.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@SpringBootTest
@Data
@Configuration
@ConfigurationProperties(prefix = "kakao")
public class KakaoApiVO {
	
	private String tokeunUri;
	private String userInfo;
	private String grantType;
	private String clientId;
	private String rediretUri;
	
	// 흠...
	private String placeName;
	private String address;

}
