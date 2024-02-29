package kr.smhrd.renewen.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.smhrd.renewen.mapper.APIMapper;

@Service
public class APIServiceImpl implements APIService {

	@Autowired
	APIMapper mapper;
	
	@Override
	public int insertWeatherFactor(Map<String, Object> map) {
		return mapper.insertWeatherFactor(map);
	}

}
