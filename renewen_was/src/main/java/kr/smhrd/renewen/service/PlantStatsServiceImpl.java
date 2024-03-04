package kr.smhrd.renewen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.smhrd.renewen.mapper.PlantStatsMapper;
import kr.smhrd.renewen.model.CellGeneratedElecVO;

@Service
public class PlantStatsServiceImpl implements PlantStatsService {

	@Autowired
	PlantStatsMapper mapper;
	
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

}
