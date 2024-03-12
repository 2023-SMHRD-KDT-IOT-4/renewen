package kr.smhrd.renewen.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.smhrd.renewen.global.util.CommonUtil;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.UserVO;
import kr.smhrd.renewen.model.api.GenerateElec;
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
	 * @return 발전셀 센싱 정보 - 온도, 측정시간, 셀타입, 용량 
	 */
	@GetMapping("/plant/sensing/cell")
	public @ResponseBody List<Map<String, Object>> sensingCell(@RequestParam("plantNo") long plantNo) {
		
		List<Map<String, Object>> result = plantService.getCellsSensing(plantNo);
		System.out.println(result);
		return result;
	}
	
	/**
	 * 해당 발전소의 센싱데이터 조회
	 * @param platNo
	 * @return
	 */
	@GetMapping("/plant/sensing")
	public @ResponseBody Map<String, List<Map<String, Double>>>	plantSensing(@RequestParam("plantNo") long plantNo,
								 @RequestParam(value = "startDate", required = false) String startDate,
								 @RequestParam(value = "endDate", required = false) String endDate) {
		
		// startDate, endDtae 없으면 오늘 날짜로
		String nowDate = util.getCurrentDateTime("yyy-MM-dd");
		startDate = startDate != null ? startDate : nowDate;
		endDate = endDate != null ? endDate : nowDate;
		
		Map<String, List<Map<String, Double>>> resultMap = new HashMap<>();
		Map<String, String> sensorMap = new HashMap<>();
		sensorMap.put("DHT11_TEM", "temperature");
		sensorMap.put("DHT11_HUM", "humidity");
		sensorMap.put("PM2008M", "dust");
		 
		 for (Map.Entry<String, String> entry : sensorMap.entrySet()) {
			 List<Map<String, Double>> sensing // 각 센서 결과
			 	=  statsService.getSensingPerPeriod(plantNo, entry.getKey(),startDate, endDate);
			 resultMap.put(entry.getValue(), sensing);
		 }
		
		return resultMap;
	}
	
	
	/**
	 * @param plantNo 발전소 식별번호
	 * @return 금일 - 누적발전량, 현재 발전량, 예상 발전량
	 */
	@GetMapping("/plant/gen/elec")
	public ResponseEntity<Map<String, Double>> genTotal(@RequestParam("plantNo") long plantNo) {
		
		Map<String, Double> response = new HashMap<>();

		// plantNo 로 LinkKey체크
		double totalWatt = statsService.genTodayTotal(plantNo); // 누적발전량
		double currentWatt = statsService.genTodayCurrent(plantNo); // 현재발전량
		double predictWatt = statsService.genTodayPredict(plantNo); // 예상발전량
		response.put("totalWatt", totalWatt);
		response.put("currentWatt", currentWatt);
		response.put("predictWatt", predictWatt);
		logger.info("today plant gen elec(total,current, predict) {}", response);
		
		return ResponseEntity.ok().body(response);
	}
	
	
	
	
	/**
	 * @param plantNo 발전소 식별번호
	 * @return 금일 시간대별(00~23시) 예상, 실제 발전량
	 */
	@GetMapping("/plant/gen_time/elec")
	public ResponseEntity<Map<String, Map<String, Double>>> genTimeTotal(@RequestParam("plantNo") long plantNo) {
		
		Map<String, Map<String, Double>> response = new HashMap<>();
		Map<String, Double> genReal = statsService.getHourElecPerCell(plantNo); // 시간대별 실제발전량
		
		String checkDate = util.getCurrentDateTime("yyyyMMdd");
		Map<String, Double> genPredict = statsService.getPredictPerHour(plantNo, checkDate); // 시간대별 예상발전량
		response.put("genReal", genReal);
		response.put("genPredict", genPredict);
		logger.info("today plant gen elec(timely) {}", response);
		
		return ResponseEntity.ok().body(response);
	}
	
	/**
	 * 조회 기간 시간대별(00~23시) 예상, 실제 발전량
	 * 타임테이블은 시간날짜 00:00 ~ 종료날짜 23:00
	 * @param plantNo 발전소 식별번호, 
	 * @return yy-MM-dd HH:00 
	 * 금일 시간대별(00~23시) 예상, 실제 발전량
	 */
	@GetMapping("/plant/gen_period/elec")
	public ResponseEntity<Map<String, Map<String, Double>>> genPeriodTotal(
							@RequestParam("plantNo") long plantNo,
							@RequestParam("startDate") String startDate,
							@RequestParam("endDate") String endDate	) {
		Map<String, Map<String, Double>> response = new HashMap<>();
		

		String predictEndDate = util.plusDays(endDate, 1); // 종료날짜에서 + 1일
		Map<String, Double> genPredict // 기간 시간대별 예상발전량
			= statsService.getPredictPerPeriod(plantNo, startDate, predictEndDate);
		startDate = startDate.replaceAll("-", "");
		endDate = endDate.replaceAll("-", "");
		Map<String, Double> genReal // 기간 시간대별 실제발전량
			= statsService.getGenElecPeriod(plantNo, startDate, endDate); 
		
		response.put("genReal", genReal);
		response.put("genPredict", genPredict);
		return ResponseEntity.ok().body(response);
	}
	
	/**
	 * 발전량 조회 결과 엑셀 다운로드
	 * @return
	 */
	@PostMapping("/plant/download/excel")
	public ResponseEntity<byte[]> downloadExcel(@RequestBody String datas, HttpServletResponse response) {
		
		try {
			List<GenerateElec> list = statsService.parseGenElec(datas);
			
				if(list.size() == 0) { // 데이터 없음
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
				}
			
				String startDate = list.get(0).getTimeData().split(" ")[0];
				String endDate = list.get(list.size()-1).getTimeData().split(" ")[0];
				
				// 엑셀파일 생성
				Workbook workbook = new HSSFWorkbook();
				CellStyle centerStyle = workbook.createCellStyle();
				centerStyle.setAlignment(HorizontalAlignment.CENTER);
				Sheet sheet = workbook.createSheet("발전량(" + startDate +"~" + endDate + ")");
		        sheet.setColumnWidth(1, 4000); // 날짜
		        sheet.setColumnWidth(2, 3000); // 실 발전량
		        sheet.setColumnWidth(3, 3000); // 예상 발전량
		        
				int rowNo = 0;
				Row headerRow = sheet.createRow(rowNo++);
		        headerRow.createCell(0).setCellValue("No");
		        headerRow.createCell(1).setCellValue("날짜");
		        headerRow.createCell(2).setCellValue("실제 발전량");
		        headerRow.createCell(3).setCellValue("예상 발전량");
		        headerRow.setHeight((short) 450);
		        
		        for(GenerateElec vo : list) {
		        	Row row = sheet.createRow(rowNo++);
		        	row.createCell(0).setCellValue(vo.getNo());
		        	row.createCell(1).setCellValue(vo.getTimeData());
		        	row.createCell(2).setCellValue(vo.getGenReal());
		        	row.createCell(3).setCellValue(vo.getGenPredict());
		        }
		        
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        workbook.write(outputStream);
		        workbook.close();
		        byte[] excelBytes = outputStream.toByteArray();
		        
		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		        headers.setContentDispositionFormData("attachment", "genElecSearchList.xls");
		        
		        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
		        
			} catch (JsonProcessingException e) {
				logger.error("json parsing error {}", e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			} catch (IOException e) {
				logger.error("IOException {}", e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		
	}
	
	
	
	
	
}
