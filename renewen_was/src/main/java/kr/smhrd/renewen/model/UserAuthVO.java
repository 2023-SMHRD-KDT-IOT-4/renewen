package kr.smhrd.renewen.model;

import lombok.Data;

@Data
public class UserAuthVO {

    // 사용자 권한 id 
    private String authId;
    // 사용자 권한 이름 
    private String authNm;
    // 사용여부 
    private String useYn;
}
