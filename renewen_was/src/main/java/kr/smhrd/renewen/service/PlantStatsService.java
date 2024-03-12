package kr.smhrd.renewen.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import kr.smhrd.renewen.model.api.GenerateElec;

public interface PlantStatsService {

	// 발전소 금일 누적 발전량
	public double genTodayTotal(long plantNo);
	// 발전소 금일 현재 발전량
	public double genTodayCurrent(long plantNo);
	// 발전소 금일 예상 발전량
	public double genTodayPredict(long plantNo);
	
	// 발전소 - 시간별 발전량
	public Map<String, Double> getHourElecPerCell(long plantNo);
	// 발전소 기간별 발전량
	public Map<String, Double> getGenElecPeriod(long plantNo, String startDate, String endDate);
	
	// 예상 발전량 시간별
	public Map<String, Double> getPredictPerHour(long plantNo, String checkDate);
	// 예상 발전량 기간별
	public Map<String, Double> getPredictPerPeriod(long plantNo, String startDate, String endDate);
	
	public List<GenerateElec> parseGenElec(String datas) throws JsonMappingException, JsonProcessingException;
	
	// 발전소 특정 센서 
	public List<Map<String, Double>> getSensingPerPeriod(long plantNo, String sensorId, String startDate, String endDate);
	
}
