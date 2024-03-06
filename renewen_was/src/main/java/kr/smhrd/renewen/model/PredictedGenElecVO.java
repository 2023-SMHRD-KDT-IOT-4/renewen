package kr.smhrd.renewen.model;

import lombok.Data;

@Data
public class PredictedGenElecVO {

    // 예측 식별번호 
    private long predictNo;
    // 발전소 식별번호 
    private long plantNo;
    // 관측소 지점번호 
    private String stnNo;
    // 예측 발전량(W) 
    private double genElec;
    // 사용여부 
    private String useYn;
    // 예측 시간 
    private String createdAt;
}
