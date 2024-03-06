package kr.smhrd.renewen.global.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kr.smhrd.renewen.global.util.CommonUtil;
import kr.smhrd.renewen.model.api.WeatherVO;
import kr.smhrd.renewen.service.APIService;

/**
 * 발전소 스케줄링 1) Rest API 기상인자 - 기상청 API 허브(https://apihub.kma.go.kr/)
 */
@Component
public class PlantScheduler {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final String API_HUB_KEY = "CXKwvqlxTqCysL6pcS6glQ";

	@Autowired
	APIService apiService;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CommonUtil commonUtil;

	/**
	 * 1) Rest API 지점별 기상인자(기압, 풍향, 풍속, 일사량) 요청 
	 * 2) weather_api 테이블에 데이터 저장
	 * 
	 * 지점정보
	 * 156:광주 위도 : 35.17294 경도 : 126.89156
	 * 165:목포 위도 : 34.81732 경도 : 126.38151
	 */
	@Scheduled(fixedRate = 1000 * 1200) // 20분 간격
	public void callApiHub() {
		
		String dateTime = commonUtil.getCurrentDateTime("yyyyMMddHHmmss");
		String todayDate = dateTime.substring(0,8);
		int todayHour = Integer.parseInt(dateTime.substring(8,10));

		int checkHour = -1;
		for(int i = 0; i <= todayHour; i++) {
			String checkDateTime 
				= 	commonUtil.getCurrentDateTime("yyyy-MM-dd") + " " +
					commonUtil.formatNumberWithPadding(i)+":00:00";
			if(!apiService.isInserted(checkDateTime)) {
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
		logger.info("response {} ", response);
		// DB 저장할 vo List
		List<WeatherVO> weatherList = getWeatherList(response, tm);
		
		for(WeatherVO vo : weatherList) {
			logger.info("weather {}", vo);
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
	public List<WeatherVO> getWeatherList(String response, String createdTime) {

		Map<String, Integer> columnTypeMap = new HashMap<>();
		columnTypeMap.put("STN", -1); // 지점코드
		columnTypeMap.put("TA", -1); // 기온
		columnTypeMap.put("SI", -1); // 일사량
		columnTypeMap.put("WS", -1); // 풍속
		columnTypeMap.put("PA", -1); // 기압
		columnTypeMap.put("WD", -1); // 풍향
		columnTypeMap.put("CA", -1); // 전운량
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
				} else {
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