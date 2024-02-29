package kr.smhrd.renewen.service;

import java.util.Map;

public interface APIService {

	// 기상청 API 허브_기상인자 저장
		public int insertWeatherFactor(Map<String, Object> map);
}
