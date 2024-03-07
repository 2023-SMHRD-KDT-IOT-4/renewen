package kr.smhrd.renewen.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.smhrd.renewen.global.util.CommonUtil;
import kr.smhrd.renewen.mapper.PlantStatsMapper;
import kr.smhrd.renewen.model.CellGeneratedElecVO;
import kr.smhrd.renewen.model.PredictedGenElecVO;

@Service
public class PlantStatsServiceImpl implements PlantStatsService {

	@Autowired
	PlantStatsMapper mapper;
	
	@Autowired
	CommonUtil util;
	
	@Override
	public double genTotal(long plantNo) {
		double totalWatt = 0;
		List<CellGeneratedElecVO> list = mapper.getTotalElecPerCell(plantNo);
		for(CellGeneratedElecVO vo : list) {
			totalWatt += vo.getGenVoltage() * vo.getGenElecCurrent();
		}
		return totalWatt;
	}

	@Override
	public double genCurrent(long plantNo) {
		double totalWatt = 0;
		List<CellGeneratedElecVO> list = mapper.getCurrentElecPerCell(plantNo);
		for(CellGeneratedElecVO vo : list) {
			totalWatt += vo.getGenVoltage() * vo.getGenElecCurrent();
		}
		return totalWatt;
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
			result.put(util.formatNumberWithPadding(i) + ":00", total);
		}
		return result;
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

}
