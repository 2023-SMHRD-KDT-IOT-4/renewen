package kr.smhrd.renewen.model.api;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

// 기상 API 데이터 VO
@Data
@Builder
public class WeatherVO {

	// 관측 식별 번호
	private long wmNo; 
	// 관측소 지점번호 
	private String stnNo;
	
	// key : 기상인자 타입 / value : 기상인자 값
	// PA(기압), WD(풍향), WS(풍속), SI(일사량)
	private Map<String, Double> measure;
	// 사용여부 
    private String useYn;
    // 측정 시간 
	private String createdAt; 
	
}
