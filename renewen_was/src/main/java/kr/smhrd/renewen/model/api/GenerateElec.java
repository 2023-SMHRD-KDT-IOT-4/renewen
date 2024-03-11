package kr.smhrd.renewen.model.api;

import lombok.Data;

/**
 * 엑셀 다운로드
 *  
 */
@Data
public class GenerateElec {

	private int no;
	private String timeData;
	private double genReal;
	private double genPredict;
}
