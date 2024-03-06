package kr.smhrd.renewen.model.api;

import lombok.Data;

@Data
public class WeatherListVO {
	
	private String stnNo;
	private String weatherType;
	private double weatherValue;
	private String createdAt;
}
