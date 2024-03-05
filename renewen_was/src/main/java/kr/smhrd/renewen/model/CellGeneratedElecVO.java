package kr.smhrd.renewen.model;

import lombok.Data;

@Data
public class CellGeneratedElecVO {

    // 셀 발전량 식별번호 
    private long cellGenNo;
    // 발전셀 식별번호 
    private long cellNo;
    // 발전 전압(V) 
    private int genVoltage;
    // 발전 전류(A) 
    private double genElecCurrent;
    // 사용여부 
    private String useYn;
    // 측정 시간 
	private String createdAt; 
	
	private double genElecWatt;
	
	private double genElecWattText;
}
