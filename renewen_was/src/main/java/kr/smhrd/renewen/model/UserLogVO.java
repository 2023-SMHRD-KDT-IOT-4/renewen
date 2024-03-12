package kr.smhrd.renewen.model;

import lombok.Data;

@Data
public class UserLogVO {
	
    // 사용자 로그id 
	private long logId;
    // 사용자 아이디 
    private String userId; //
    // 접속 ip 
    private String connectIp;
    // 접근 메뉴 
    private String accessMenu; //
    // 로그 내용 
    private String logContent; //
    // 로그 종류 : debug, info, warn
    private String logType; //
    // 유저 노출유무 
    private String popupYn; //
    // 로그 시간 
    private String createdAt;	
    
}
