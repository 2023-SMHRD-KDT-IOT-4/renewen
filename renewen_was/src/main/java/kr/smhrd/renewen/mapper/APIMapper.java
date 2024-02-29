package kr.smhrd.renewen.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface APIMapper {

	// 기상청 API 허브_기상인자 저장
	public int insertWeatherFactor(Map<String, Object> map);
}
