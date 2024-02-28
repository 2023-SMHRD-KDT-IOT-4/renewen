package kr.smhrd.renewen.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PowerPlantVO {

    // 발전소 식별번호 
    private long plantNo;
    // 사용자 id 
    private String userId;
    // 발전소 이름 
    private String plantName;
    // 발전소 주소 
    private String plantAddr;
    //발전소 상세 주소
    private String plantAddr2;
    // 사업자등록번호 
    private String brNumber;
    // 발전 셀 갯수 
    //private int generateCellCnt;
    // 발전소 연동키 
    private String plantLinkKey;
    // 승인여부 
    private String grantYn;
    // 사용여부 
    private String useYn;
    // 연동 시간 
    private Timestamp createdAt;
	
}
