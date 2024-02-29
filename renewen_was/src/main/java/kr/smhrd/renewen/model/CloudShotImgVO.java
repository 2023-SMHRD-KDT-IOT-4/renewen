package kr.smhrd.renewen.model;

import lombok.Builder;
import lombok.Data;

// 발전소 구름형상 이미지

@Data
@Builder
public class CloudShotImgVO {
	
	// 구름 이미지 식별번호 
    private long csNo;
    // 발전소 식별번호 
    private long plantNo;
    
    // 촬영 이미지 설명 
    private String cloudImgDesc;
    // 이미지 업로드 이름 
    private String cloudImgName;
    // 이미지 파일이름 
    private String cloudImgRealname;
    // 이미지 파일크기 
    private int cloudImgSize;
    // 이미지 확장자 
    private String cloudImgExt;
    
    // 사용여부 
    private String useYn;
    // 촬영 시간 
    private String createdAt;
    
}
