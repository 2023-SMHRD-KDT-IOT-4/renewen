package kr.smhrd.renewen.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.smhrd.renewen.model.SensingDataVO;

@SpringBootTest
public class PlantMapperTest {

	@Autowired
	PlantMapper plantMapper;
	
	@Test
	@DisplayName("get 센싱데이터 sdNo")
	public void sensingData() {
		assertNotNull(plantMapper);
		SensingDataVO vo = plantMapper.getSensingDataBySdNo(350001);
		System.out.println(vo);
		assertNotNull(vo);
		
	}
	@Test
	@DisplayName("get 센싱데이터 sdNo2")
	public void sensingData2() {
		assertNotNull(plantMapper);
		SensingDataVO vo = plantMapper.getSensingDataBySdNo(350003);
		System.out.println(vo);
		assertNotNull(vo);
		
	}
}
