package kr.smhrd.renewen.model;

import lombok.Data;

@Data
public class SensorInfoVO {

    // 센서id(모델명) 
    private String sensorId;
    // 센서 타입 코드 
    private String sensorTypeCd;
    // 센서 타입 이름 
    private String sensorTypeNm;
    // 센서 설명 
    private String sensorDesc;
    // 센서 측정단위 
    private String measureUnit;
    // 사용여부 
    private String useYn;
    
}
