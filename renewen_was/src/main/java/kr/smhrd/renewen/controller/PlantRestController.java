package kr.smhrd.renewen.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.smhrd.renewen.global.util.CommonUtil;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.UserVO;
import kr.smhrd.renewen.service.PlantService;
import kr.smhrd.renewen.service.PlantStatsService;

@Controller
public class PlantRestController { 

	@Autowired
	PlantService plantService;
	
	@Autowired
	PlantStatsService statsService;

	@Autowired
	CommonUtil util;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @param session
	 * @return 로그인 유저의 발전소List
	 */
	@GetMapping("/plant/list/json")
	public @ResponseBody List<PowerPlantVO> plantListJson(HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		List<PowerPlantVO> list = plantService.getPlantsByUserId(userId);
		return list;
	}
	
	/**
	 * @param plantNo 발전소 식별번호
	 * @return 금일 누적발전량, 현재 발전량
	 */
	@GetMapping("/plant/gen/elec")
	public ResponseEntity<Map<String, Double>> genTotal(@RequestParam("plantNo") long plantNo) {
		
		Map<String, Double> response = new HashMap<>();

		double totalWatt = statsService.genTotal(plantNo); // 누적발전량
		double currentWatt = statsService.genCurrent(plantNo); // 현재발전량
		response.put("totalWatt", totalWatt);
		response.put("currentWatt", currentWatt);
		logger.info("plant gen elec {}", response);
		
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/plant/gen_time/elec")
	public ResponseEntity<Map<String, Map<String, Double>>> genTimeTotal(@RequestParam("plantNo") long plantNo) {
		
		Map<String, Map<String, Double>> response = new HashMap<>();
		Map<String, Double> genReal = statsService.getHourElecPerCell(plantNo); // 시간대별 실제발전량
		
		String checkDate = util.getCurrentDateTime("yyyyMMdd");
		Map<String, Double> genPredict = statsService.getPredictPerHour(plantNo, checkDate); // 시간대별 예상발전량
		response.put("genReal", genReal);
		response.put("genPredict", genPredict);
		
		return ResponseEntity.ok().body(response);
	}
	
}
