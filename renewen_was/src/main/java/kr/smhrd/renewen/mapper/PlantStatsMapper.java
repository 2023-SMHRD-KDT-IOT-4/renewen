package kr.smhrd.renewen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.smhrd.renewen.model.CellGeneratedElecVO;

@Mapper
public interface PlantStatsMapper {

	// 발전소 셀 별 금일 누적 발전량
	public List<CellGeneratedElecVO> getTotalElecPerCell(long plantNo);
	// 발전소 셀 별 금일 현재 발전량
	public List<CellGeneratedElecVO> getCurrentElecPerCell(long plantNo);
	// 발전소 - 시간별 발전량
	public List<CellGeneratedElecVO> getHourElecPerCell(@Param("dateHour") String dateHour,
			@Param("cellNos") List<Long> cellNos);
	
}
