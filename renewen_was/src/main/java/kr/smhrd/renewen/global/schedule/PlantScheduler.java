package kr.smhrd.renewen.global.schedule;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kr.smhrd.renewen.global.util.CommonUtil;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.api.WeatherVO;
import kr.smhrd.renewen.service.APIService;
import kr.smhrd.renewen.service.PlantService;

/**
 * 발전소 스케줄링 
 * 1) Rest API 기상인자 - 기상청 API 허브(https://apihub.kma.go.kr/)
 * 2) 모델링 API 발전소 예상발전량 - Flask Server 
 */
@Component
@EnableScheduling
public class PlantScheduler {

	@Autowired
	APIService apiService;
	
	@Autowired
	PlantService plantService;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CommonUtil commonUtil;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${api.hub-key}")
	private String API_HUB_KEY;
	
    @Value("${url.flask}")
    private String FLASK_URL;
	
    // 기온, 일사량, 풍속, 기압, 풍향, 전운량
	private List<String> weatherFactors 
		= Arrays.asList("TEMPERATURE", "SI", "WS", "PA", "WD", "CA");
    
	/**
	 * 유효한 발전소들의 예상 발전량 call
	 * plant_no : [250002, 250004]
	 * st_date : 2024030700 yyyymmddhh    
	 */
	@Scheduled(cron = "0 30 0 * * *") // 매일 00:30분
	public void callFlask() {

		logger.info("callFlask schedule");
		// 1) 유효한 발전소(연동완료) 리스트
		List<PowerPlantVO> plantList = plantService.getGrantPlants();
		if(plantList.isEmpty()) {
			logger.info("callFlask schedule - plantList empty");
			return;  // 유효 발전소 없음 => 예상발전량 요청X
		}
		List<Long> plantNos = new ArrayList<>();
		for(PowerPlantVO vo : plantList) {
			plantNos.add(vo.getPlantNo());
		}
		String stDate = commonUtil.getCurrentDateTime("yyyyMMdd") + "00" ;
	    
	    HttpHeaders headers = new HttpHeaders();// HTTP 요청 헤더 설정
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    Map<String, Object> requestBody = new HashMap<>();// 요청 본문에 전송할 데이터 설정
	    requestBody.put("plant_no", plantNos);
	    requestBody.put("st_date", stDate);
	    // HTTP 요청 엔티티 생성
	    HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
		String reqUrl = FLASK_URL + "predict";
		// 2) Flask 예상 발전량 요청
		String response = restTemplate.postForObject(reqUrl, requestEntity, String.class);
		logger.info("callFlask response {} ", response);
	}
	
	/**
	 * 1) Rest API 지점별 기상인자(기압, 풍향, 풍속, 일사량) 요청 
	 * 2) weather_api 테이블에 데이터 저장
	 * 
	 * 지점정보
	 * 156:광주 위도 : 35.17294 경도 : 126.89156
	 * 165:목포 위도 : 34.81732 경도 : 126.38151
	 */
	@Scheduled(fixedRate = 60000 * 13) // (1min) 13분 간격
	public void callApiHub() {
		String dateTime = commonUtil.getCurrentDateTime("yyyyMMddHHmmss");
		String todayDate = dateTime.substring(0,8);
		int todayHour = Integer.parseInt(dateTime.substring(8,10));

		List<String> checkFactors = null;
		int checkHour = -1;
		for(int i = 0; i <= todayHour; i++) {
			String checkDateTime 
				= 	commonUtil.getCurrentDateTime("yyyy-MM-dd") + " " +
					commonUtil.formatNumberWithPadding(i)+":00:00";
			List<String> insertedFactors = apiService.isInserted(checkDateTime, weatherFactors);
			checkFactors = new ArrayList<>(weatherFactors);
			checkFactors.removeAll(insertedFactors);
			// 수집할 기상인자 존재
			if(checkFactors.size() > 0) {
				checkHour = i;
				break;
			}
		}
		
		String tm = dateTime.substring(0, 12);
		if(checkHour >= 0 && checkHour <= todayHour) {
			tm = todayDate + commonUtil.formatNumberWithPadding(checkHour)+"00";
		}
		int min = Integer.parseInt(tm.substring(11, 12));
		if(min > 0) {
			return;
		}
		
		// 조회 지점(지역) 리스트 현재는 156,165만. 추후 발전소 테이블에서 유효발전소 조회로
		List<String> stnList = Arrays.asList("156");
		String stnQueryString = String.join(":", stnList); // 요소 사이에 쉼표로 결합 ("156:165")

		String baseUrl = "https://apihub.kma.go.kr/api/typ01/url/kma_sfctm2.php?authKey=" + API_HUB_KEY;
		String reqUrl = baseUrl + "&stn=" + stnQueryString + "&tm=" + tm; // 요청시간 추가 ex) 202403040000
		logger.info("reqUrl {} ", reqUrl);
		
		// Rest Get 요청
		String response = restTemplate.getForObject(reqUrl, String.class);
		// DB 저장할 vo List
		List<WeatherVO> weatherList = getWeatherList(response, tm, checkFactors);
		
		for(WeatherVO vo : weatherList) {
			logger.info("weather {}", vo.getMeasure());
			Map<String, Double> measureMap =vo.getMeasure();
			Map<String, Object> parameterMap = new HashMap<>(); // insert
			parameterMap.put("stnNo", vo.getStnNo());
			parameterMap.put("createdAt", tm + "00");
			
			for (String key : measureMap.keySet()) {
			    Double value = measureMap.get(key);
			    parameterMap.put("weatherType", key);
			    parameterMap.put("weatherValue", value);
			    apiService.insertWeatherFactor(parameterMap);
			}
		}
		
	}

	// DB 저장할 기상정보 List<VO>로 정제
	public List<WeatherVO> getWeatherList(String response, String createdTime, List<String> checkFactors) {

		Map<String, Integer> columnTypeMap = new HashMap<>();
		columnTypeMap.put("STN", -1); // 지점코드

		for(String factor : weatherFactors) {
			if("TEMPERATURE".equals(factor)) {
				columnTypeMap.put("TA", -1); // TEMPERATURE(db저장) 수집은 TA
				continue;
			}
			columnTypeMap.put(factor, -1);
		}
		List<WeatherVO> weatherList = new ArrayList<>();

		String[] lines = response.split("\\r?\\n");
		String columnLine = "";
		List<String> responseList = new ArrayList<>();

		for (String line : lines) {
			if (line.startsWith("# YYMMDDHHMI")) {// 컬럼 라인 추출
				columnLine = line;
			} else if (!line.startsWith("#")) { // 지점별 기상측정 결과 추출
				responseList.add(line);
			}
		}
		
		// 유효 기상인자 인덱스
		String[] columnArr = columnLine.split("\\s+");
		for (int i = 0; i < columnArr.length; i++) {
			String type = columnArr[i];
			if (columnTypeMap.containsKey(type)) {
				columnTypeMap.put(type, i - 1);
			}
		}
		Map<Integer, String> reversedMap = new HashMap<>();
		for (Map.Entry<String, Integer> entry : columnTypeMap.entrySet()) {
			reversedMap.put(entry.getValue(), entry.getKey());
		}

		for (String res : responseList) { // 기상 응답값 리스트들
			String[] responseArr = res.split("\\s+");
			String[] stn = { "" };
			Map<String, Double> measureMap = new HashMap<>();

			reversedMap.forEach((key, value) -> {
				if ("STN".equals(value)) {
					stn[0] = responseArr[key];
				} else if("TA".equals(value)) { // 기상(TA)일 경우 키값 => TEMPERATURE
					measureMap.put("TEMPERATURE", Double.parseDouble(responseArr[key]));
				}
				else {
					measureMap.put(value, Double.parseDouble(responseArr[key]));
				}
			});

			WeatherVO vo = WeatherVO.builder()
										.stnNo(stn[0])
										.measure(measureMap)
										.createdAt(createdTime)
										.build();
			weatherList.add(vo);
					
		} 

		return weatherList;
	}


}