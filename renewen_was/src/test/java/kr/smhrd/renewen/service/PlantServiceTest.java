package kr.smhrd.renewen.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.smhrd.renewen.model.SensingDataVO;

@SpringBootTest
public class PlantServiceTest {

	@Autowired
	PlantService service;

	@Test
	@DisplayName("ddd")
	public void ddd() {
		assertNotNull(service);
		List<SensingDataVO> list2 = service.getSensingDatasByPlantNo(250001);
		System.out.println(list2);
	}
}
