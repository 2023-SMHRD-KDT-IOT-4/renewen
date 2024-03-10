package kr.smhrd.renewen.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.smhrd.renewen.model.api.WeatherListVO;

@Mapper
public interface APIMapper {

	// 기상청 API 허브_기상인자 저장
	public int insertWeatherFactor(Map<String, Object> map);
	
	public List<WeatherListVO> getWeatherList(Map<String, String> map);
	
	public List<String> checkInsert(
			@Param("dateTime") String dateTime,
			@Param("types") List<String> types);
}
