package kr.smhrd.renewen.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.smhrd.renewen.model.CellGeneratedElecVO;
import kr.smhrd.renewen.model.PredictedGenElecVO;

@Mapper
public interface PlantStatsMapper {

	// 발전소 금일 누적 발전량
	public Double getTodayTotal(long plantNo);
	// 발전소 셀 별 금일 누적 발전량
	public List<CellGeneratedElecVO> getTotalElecPerCell(long plantNo);
	// 발전소 금일 현재 발전량
	public Double getTodayCurrent(long plantNo);
	// 발전소 셀 별 금일 현재 발전량
	public List<CellGeneratedElecVO> getCurrentElecPerCell(long plantNo);
	// 발전소 - 시간별 발전량
	public List<CellGeneratedElecVO> getHourElecPerCell(
			@Param("dateHour") String dateHour,
			@Param("cellNos") List<Long> cellNos);
	// 발전소 - 기간별 발전량
	public List<CellGeneratedElecVO> getGenElecPeriod(
			@Param("startDate") String startDate,
			@Param("endDate") String endDate,
			@Param("cellNos") List<Long> cellNos);
	
	// =============================================================================================

	// 예상 발전량 시간별
	public List<PredictedGenElecVO> getPredictPerHour(
			@Param("plantNo") long plantNo,
			@Param("checkDate") String checkDate);
	
	
	public List<Map<String, Object>> getSensingPerPeriod(
			@Param("sensorId") String sensorId, 
			@Param("plantNo") long plantNo,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);
}
