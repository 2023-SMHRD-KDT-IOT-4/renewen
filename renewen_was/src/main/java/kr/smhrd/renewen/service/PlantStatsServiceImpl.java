package kr.smhrd.renewen.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.smhrd.renewen.global.util.CommonUtil;
import kr.smhrd.renewen.mapper.PlantMapper;
import kr.smhrd.renewen.mapper.PlantStatsMapper;
import kr.smhrd.renewen.model.CellGeneratedElecVO;import kr.smhrd.renewen.model.GenerateCellVO;
import kr.smhrd.renewen.model.PredictedGenElecVO;

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
			String dateHour = dates[0] + " " + dates[1].substring(0,2) + ":00";
			String inputKey = dateHour.substring(2);
			double watt = vo.getGenElecWatt();
			if(map.containsKey(dateHour)) {
				double oldWatt = map.get(dateHour);
				map.put(inputKey, oldWatt + watt);
			} else {
				map.put(inputKey, watt);
			}
		}
		return map;
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


}
