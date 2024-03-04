package kr.smhrd.renewen.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class PowerPlantVO {

    // 발전소 식별번호 
    private long plantNo;
    // 사용자 id 
    private String userId;
    /* 사용자 입력 */
    // 발전소 이름 
    private String plantName;
    // 발전소 주소 
    private String plantAddr;
    //발전소 상세 주소
    private String plantAddr2;
    // 사업자등록번호 
    private String brNumber;
    /*  */
    
    // 발전소 연동키 
    private String plantLinkKey;
    // 승인여부 
    private String grantYn;
    // 사용여부 
    private String useYn;
    // 연동 시간 
    private Timestamp createdAt;
    
    // 관측소 지점번호 
    private String stnNo;
    // 위도 
    private double latitude;
    // 경도 
    private double longitude;
    
    // 겨레 추가
    // '위도, 경도' 형식의 문자열로 좌표를 설정
//    public void setCoordinates(String coordinates) {
//    	String[] parts = coordinates.split(", ");
//    	if (parts.length == 2) {
//    		try {
//    			this.latitude = Double.parseDouble(parts[0]);
//    			this.longitude = Double.parseDouble(parts[1]);
//    		} catch (NumberFormatException e) {
//    			e.printStackTrace();
//    		}
//    	}else {
//    		System.out.println("잘못된 형식의 좌표입니다.");
//    	}
//    }
//    
//    // 위도 값 문자열 형태로 반환
//    public String getLatitudeAsString() {
//    	return String.valueOf(longitude);
//    }
//    // 경도 값 문자열 형태로 반환
//    public String getLongitudeAsString() {
//        return String.valueOf(longitude);
//    }
//     
    
    // 정렬순서 
    private int sortNo;
    
    private List<GenerateCellVO> cellList;
    
    // 발전 셀 갯수 
    //private int generateCellCnt;
}
