package kr.smhrd.renewen.service;

import java.util.Map;

public interface PlantStatsService {

	// 발전소 금일 누적 발전량
	public double genTotal(long plantNo);
	// 발전소 금일 현재 발전량
	public double genCurrent(long plantNo);
	// 발전소 - 시간별 발전량
	public Map<String, Double> getHourElecPerCell(long plantNo);

//	public List<CellGeneratedElecVO> getHourElecPerCell(@Param("dateHour") String dateHour,
//			@Param("cellNos") List<Long> cellNos);
	
	// 예상 발전량 시간별
	public Map<String, Double> getPredictPerHour(long plantNo, String checkDate);
}
