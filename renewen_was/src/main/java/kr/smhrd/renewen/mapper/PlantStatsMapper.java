package kr.smhrd.renewen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.smhrd.renewen.model.CellGeneratedElecVO;

@Mapper
public interface PlantStatsMapper {

	// 발전소 셀 별 누적 발전량
	public List<CellGeneratedElecVO> getTotalElecPerCell(long plantNo);
	// 발전소 셀 별 현재 발전량
	public List<CellGeneratedElecVO> getCurrentElecPerCell(long plantNo);
	
}
