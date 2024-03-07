package kr.smhrd.renewen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 발전셀 촬영 이미지

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CellShotImgVO {
	
	// 셀 이미지 식별번호 
    private long csNo;
    // 발전셀 식별번호 
    private long cellNo;
    
//    private String cellSerialNum;
    
    // 촬영 이미지 설명 
    private String cellImgDesc;
    // 이미지 업로드 이름 
    private String cellImgName;
    // 이미지 파일이름 
    private String cellImgRealname;
    // 이미지 파일크기 
    private int cellImgSize;
    // 이미지 확장자 
    private String cellImgExt;
    
    // 사용여부 
    private String useYn;
    // 촬영 시간 
    private String createdAt;

    // View 출력 - src 속성 경로
    public String getImgFile() {
    	return cellImgRealname + "." + cellImgExt;
    }
}
