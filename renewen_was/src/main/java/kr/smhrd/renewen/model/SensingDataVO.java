package kr.smhrd.renewen.model;

import lombok.Data;

@Data
public class SensingDataVO {

    // 센싱데이터 식별번호 
    private long sdNo;
    // 발전소 식별번호 
    private long plantNo;
    // 센서id
    private String sensorId;
    // 센서 정보(타입, 설명, 측정단위 등)
    private SensorInfoVO sensorInfo;
    // 센서 시리얼번호 
    private String sensorSerialNum;
    // 센싱 측정 값 
    private double measureValue;
    // 센싱 측정 설명 
    private String measureDesc;
    // 사용여부 
    private String useYn;
    // 측정 시간 
	private String createdAt;
	
	private long cellNo;
	private String cellSerialNum;

}
		