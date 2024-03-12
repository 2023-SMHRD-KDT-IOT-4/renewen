package kr.smhrd.renewen.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.smhrd.renewen.global.util.CommonUtil;
import kr.smhrd.renewen.mapper.PlantMapper;
import kr.smhrd.renewen.mapper.PlantStatsMapper;
import kr.smhrd.renewen.model.CellGeneratedElecVO;
import kr.smhrd.renewen.model.GenerateCellVO;
import kr.smhrd.renewen.model.PredictedGenElecVO;
import kr.smhrd.renewen.model.api.GenerateElec;

@Service
public class PlantStatsServiceImpl implements PlantStatsService {

	@Autowired
	PlantStatsMapper mapper;
	
	@Autowired
	PlantMapper plantMapper;
	
	@Autowired
	CommonUtil util;
	
	// 금일 누적 발전량
	@Override
	public double genTodayTotal(long plantNo) {
		Double totalWatt = mapper.getTodayTotal(plantNo);
		double result = totalWatt != null ? Math.floor(totalWatt * 100) / 100 : 0.0; 
		return result;
	}

	// 금일 현재 발전량
	@Override
	public double genTodayCurrent(long plantNo) {
		Double totalWatt = mapper.getTodayCurrent(plantNo);
		double result = totalWatt != null ? Math.floor(totalWatt * 100) / 100 : 0.0; 
		return result;
	}
	
	@Override
	public double genTodayPredict(long plantNo) {
		String today = util.getCurrentDateTime("yyyyMMdd");
		List<PredictedGenElecVO> list = mapper.getPredictPerHour(plantNo, today);
		double total = 0; 
		for(PredictedGenElecVO vo : list) {
			total += vo.getGenElec();
		}
		double result = Math.floor(total * 100) / 100; // 소수점 2자리 버림
		return result;
	}

	@Override
	public Map<String, Double> getHourElecPerCell(long plantNo) {

		Map<String, Double> result = new HashMap<>();
		List<CellGeneratedElecVO> list = mapper.getTotalElecPerCell(plantNo);
		
		for(int i = 0; i < 24; i++) {
			double total = 0;
			for(CellGeneratedElecVO vo : list) {
				String createdAt = vo.getCreatedAt();
				String time = createdAt.split(" ")[1].substring(0, 2);
				
				if(i == Integer.parseInt(time)) {
					total += vo.getGenVoltage() * vo.getGenElecCurrent();
				}
				
			}
			double totalVal = Math.floor(total * 100) / 100;
			result.put(util.formatNumberWithPadding(i) + ":00", totalVal);
		}
		return result;
	}

	// 발전소 기간별 발전량
	@Override
	public Map<String, Double> getGenElecPeriod(long plantNo, String startDate, String endDate) {
		
		List<GenerateCellVO> cellList = plantMapper.getCellsByPlantNo(plantNo);
		List<Long> cellNos = cellList.stream().map(GenerateCellVO::getCellNo).collect(Collectors.toList());
		List<CellGeneratedElecVO> genList = mapper.getGenElecPeriod(startDate, endDate, cellNos);

		Map<String, Double> result = getElecPeriodResult(genList);
		return result;
	}
	
	
	public Map<String, Double> getElecPeriodResult(List<CellGeneratedElecVO> list) {
		Map<String, Double> map = new HashMap<>();
		
		for(CellGeneratedElecVO vo : list) {
			String createdAt = vo.getCreatedAt(); // 2024-03-06 19:19:32
			String[] dates = createdAt.split(" ");
			String dateHour = dates[0] + " " + dates[1].substring(0,2);
			String inputKey = dateHour.substring(2); // 24-03-08 15
			double watt = vo.getGenElecWatt();
			if(map.containsKey(inputKey)) {
				double oldWatt = map.get(inputKey);
				map.put(inputKey, oldWatt + watt);
			} else {
				map.put(inputKey, watt);
			}
		}
		
		Map<String, Double> resultMap = new HashMap<>();
		for (Map.Entry<String, Double> entry : map.entrySet()) {
		    String hour = entry.getKey();
		    double value = entry.getValue();
		    String hourMin = hour + ":00";
		    resultMap.put(hourMin, value);
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Double> getPredictPerHour(long plantNo, String checkDate) {
		Map<String, Double> result = new HashMap<>();
		List<PredictedGenElecVO> list = mapper.getPredictPerHour(plantNo, checkDate);
		
		for(int i = 0; i < 24; i++) {
			for(PredictedGenElecVO vo : list) {
				String createdAt = vo.getCreatedAt();
				String time = createdAt.split(" ")[1].substring(0, 2);
				if(i == Integer.parseInt(time)) {
					result.put(util.formatNumberWithPadding(i) + ":00", vo.getGenElec());
					break;
				}
				result.put(util.formatNumberWithPadding(i) + ":00", 0.0);
			}
		}
		
		return  result;
	}
	
	public Map<String, Double> getPredictTimeMap(long plantNo, String checkDate, Map<String, Double> timeMap) {
		Map<String, Double> result = new HashMap<>();
		List<PredictedGenElecVO> list = mapper.getPredictPerHour(plantNo, checkDate);
		
		for(int i = 0; i < 24; i++) {
			for(PredictedGenElecVO vo : list) {
				String createdAt = vo.getCreatedAt();
				String time = createdAt.split(" ")[1].substring(0, 2);
				if(i == Integer.parseInt(time)) {
					result.put(util.formatNumberWithPadding(i) + ":00", vo.getGenElec());
					break;
				}
				result.put(util.formatNumberWithPadding(i) + ":00", 0.0);
			}
		}

		return  result;
	}

	// 기간 시간대별 예상발전량
	@Override
	public Map<String, Double> getPredictPerPeriod(long plantNo, String startDate, String endDate) {
		Map<String, Double> responseMap = new HashMap<>();
		
		startDate = startDate.replaceAll("-", "");
		endDate = endDate.replaceAll("-", "");
		List<String> dateList = util.dateList(startDate, endDate);
		Map<String, Double> timeMap =  makeTimeTable(dateList);
		
		for(String checkDate : dateList) {
			checkDate = checkDate.replaceAll("-", "");
			List<PredictedGenElecVO> resultList = mapper.getPredictPerHour(plantNo, checkDate);
			responseMap.putAll(getDateTimeListPredict(resultList));
		}
		
		// resultMap에 예상 발전량 없는 date time 키 값 추가
		for(String timeDate : timeMap.keySet()) {
			if(!responseMap.containsKey(timeDate)) {
				responseMap.put(timeDate, 0.0);
			}
		}
		

		return responseMap;
	}// 
	
	
	// 20240306
	public Map<String, Double> makeTimeTable(List<String> dateList) {
		
		Map<String, Double> result = new HashMap<>();
		
		for(String date : dateList) {
			String year = date.substring(0, 4).substring(2);
			String month = date.substring(4, 6);
			String day = date.substring(6);
			date = year + "-" + month + "-" + day;
			for(int i = 0; i < 24; i++) {
				String hour = i < 10 ? "0" + i : String.valueOf(i);
				hour += ":00";
				result.put(date + " " +  hour, 0.0 );
			}
		}
		
		return result; // yy-MM-dd HH:00 
	}
	
	public Map<String, Double> getDateTimeListPredict(List<PredictedGenElecVO> list) {
		Map<String, Double> resultMap = new HashMap<>();
		
		for(int i = 0; i < 24; i++) {
			for(PredictedGenElecVO vo : list) {
				String createdAt = vo.getCreatedAt();
				String date = createdAt.split(" ")[0].substring(2);
				String time = createdAt.split(" ")[1].substring(0, 2);
				if(i == Integer.parseInt(time)) {
					resultMap.put(date + " " + util.formatNumberWithPadding(i) + ":00", vo.getGenElec());
					break;
				}
				resultMap.put(date + " " + util.formatNumberWithPadding(i) + ":00", 0.0);
			}
		}
		
		return resultMap;
	}

	@Override
	public List<GenerateElec> parseGenElec(String datas) throws  JsonProcessingException {
		
	    ObjectMapper objectMapper = new ObjectMapper();
	    List<GenerateElec> generateElecList = new ArrayList<>();
	
        JsonNode rootNode = objectMapper.readTree(datas);
        JsonNode timeDataNode = rootNode.get("timeData");
        JsonNode genRealNode = rootNode.get("genReal");
        JsonNode genPredictNode = rootNode.get("genPredict");

        for (int i = 0; i < timeDataNode.size(); i++) {
            String timeData = timeDataNode.get(i).asText();
            double genReal = genRealNode.get(i).get("value").asDouble();
            double genPredict = genPredictNode.get(i).get("value").asDouble();

            GenerateElec generateElec = new GenerateElec();
            generateElec.setNo(i+1);
            generateElec.setTimeData(timeData);
            generateElec.setGenReal(genReal);
            generateElec.setGenPredict(genPredict);

            generateElecList.add(generateElec);
        }
	        
	    return generateElecList;
	}

	@Override
	public List<Map<String, Double>> getSensingPerPeriod(long plantNo, String sensorId, String startDate, String endDate) {
		
        List<String> dateTimes = util.dateTimeList(startDate, endDate, "-"); // yyy-MM-dd HH:00
        List<Map<String, Double>> result = new ArrayList<>();
		
		List<Map<String, Object>> sensings = mapper.getSensingPerPeriod(sensorId, plantNo, startDate, endDate);
		
		for(String dateTime : dateTimes) {
			Double value = 0.0;
			
			for(Map<String, Object> sensing : sensings) {
				String createdAt = (String) sensing.get("hour_group");
				BigDecimal measureVal = (BigDecimal) sensing.get("measure_value");
				
				if(dateTime.equals(createdAt)) {
					value = measureVal != null ? measureVal.doubleValue() : 0.0;
					break;
				}
			}
			Map<String, Double> map = new HashMap<>();
			map.put(dateTime, value);
			result.add(map);
		}
		
		return result;
	}


}
