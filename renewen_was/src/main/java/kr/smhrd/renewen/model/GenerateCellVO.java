package kr.smhrd.renewen.model;

import java.util.List;

import lombok.Data;

@Data
public class GenerateCellVO {

    // 발전셀 식별번호 
    private long cellNo;
    // 발전소 식별번호 
    private long plantNo;
    // 발전셀 타입 
    private String cellType;
    // 발전셀 시리얼번호 
    private String cellSerialNum;
    // 발전셀 용량 
    private double cellVolume;
    // 셀 크기 단위 
    private String cellSizeUnit;
    // 셀 크기(가로) 
    private double cellWidth;
    // 셀 크기(세로) 
    private double cellHeight;
    // 셀 크기(높이) 
    private double cellDepth;
    // 사용여부 
    private String useYn;
    // 연동시간 
    private String createdAt;
    
    private List<CellShotImgVO> cellImgList;
    
    // 발전 전압(V) 
    private int genVoltage;
    // 발전 전류(A) 
    private double genElecCurrent;
}
