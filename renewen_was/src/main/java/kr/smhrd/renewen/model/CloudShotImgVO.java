package kr.smhrd.renewen.model;

import java.io.UnsupportedEncodingException;

import org.apache.tomcat.util.codec.binary.Base64;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

// 발전소 구름형상 이미지

@Data
public class CloudShotImgVO {
	
	// 구름 이미지 식별번호 
    private long csNo;
    // 발전소 식별번호 
    private long plantNo;
    // 촬영 이미지 설명 
    private String cloudImgDesc;
    
    // 구름 촬영 이미지 
    private byte[] cloudImg;
    
    @Getter(AccessLevel.NONE)
    // encode Base64(jsp상에 출력)
    private String base64String;
    
    // 사용여부 
    private String useYn;
    // 촬영 시간 
    private String createdAt;
    

    public String getBase64String()  {
    	System.out.println("getBase64String");
    	
    	byte[] bytes = this.getCloudImg();
		byte[] encodeBase64 = Base64.encodeBase64(bytes, false);
		String base64Encoded = "";
		try {
			base64Encoded = new String(encodeBase64, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.setBase64String(base64Encoded);
    	
		return base64String;
    	
    }
    
}
