package kr.smhrd.renewen.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserVO {
	
    // 사용자 id 
    private String userId;
    // 사용자 비밀번호 
    private String userPw;
    // 사용자 권한 id 
    private String authId;
    // 사용자 권한 이름 
    private String authNm;
    // 사용자 이메일 
    private String userEmail;
    // 사용자 이름 
    private String userName;
    // 사용자 연락처 
    private String userTel;
    // 사용자 회사 
    private String userCompany;
    // 담당 직위 
    private String managePosition;
    // 담당 업무명 
    private String manageTask;
    // 가입 시간 
    private Timestamp joinedAt;
    
}
