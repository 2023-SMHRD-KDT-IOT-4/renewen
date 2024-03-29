package kr.smhrd.renewen.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.smhrd.renewen.mapper.PlantMapper;
import kr.smhrd.renewen.model.CellGeneratedElecVO;
import kr.smhrd.renewen.model.CellShotImgVO;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.GenerateCellVO;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.SensingDataVO;

@Service
public class PlantServiceImpl implements PlantService {

	@Autowired
	PlantMapper plantMapper;
	
	@Override
	public int registerPlant(PowerPlantVO vo) {
	
		return plantMapper.registerPlant(vo);			
		
	}

	
	@Override
	public List<PowerPlantVO> getPlantsByUserId(String userId) {
		
		return plantMapper.getPlantsByUserId(userId);
	}

	@Override
	public int deletePlant (PowerPlantVO vo) {
		return plantMapper.deletePlant(vo);
	}
	
	@Override
	public PowerPlantVO getPlantInfo(int plantNo) {
		return plantMapper.getPlantInfo(plantNo);
	
	}
	
	@Override
	public int updatePlant(PowerPlantVO vo) {
		return plantMapper.updatePlant(vo);
	}
	
	
	@Override
	public PowerPlantVO getPlantByPlantNo(long plantNo) {
		return plantMapper.getPlantByPlantNo(plantNo);
	}

	@Override
	public int updateUseYn(long cellNo) {
		return plantMapper.updateUseYn(cellNo);
	}
	
	@Override
	public List<CloudShotImgVO> getCloudImgsByPlantNo(long plantNo) {
		return plantMapper.getCloudImgsByPlantNo(plantNo);
	}
	
	public List<CloudShotImgVO> getCloudImgsByPlantNoAndDate(CloudShotImgVO cloud){
		return plantMapper.getCloudImgsByPlantNoAndDate(cloud);
	}

	@Override
	public long getPlantNoByLinkKey(String plantLinkKey) {
		
		Long result = plantMapper.getPlantNoByLinkKey(plantLinkKey);
		return result != null ? result : 0;
	}

	@Override
	public int insertCloudShotImg(CloudShotImgVO vo) {
		return plantMapper.insertCloudShotImg(vo);
	}
	
	@Override
	public int insertSensingData(SensingDataVO vo) {
		return plantMapper.insertSensingData(vo);
	}

	@Override
	public List<SensingDataVO> getSensingDatasByPlantNo(long plantNo) {
		return plantMapper.getSensingDatasByPlantNo(plantNo);
	}

	@Override
	public SensingDataVO getSensingDataBySdNo(long sdNo) {
		return plantMapper.getSensingDataBySdNo(sdNo);
	}

	@Override
	public int insertGenerateCell(GenerateCellVO vo) {
		return plantMapper.insertGenerateCell(vo);
	}

	@Override
	public long getCellNoBySerialNum(String cellSerialNum) {
		
		Long result = plantMapper.getCellNoBySerialNum(cellSerialNum);
		return result == null ? 0 : result;
	}

	@Override
	public int insertCellShotImg(CellShotImgVO vo) {
		return plantMapper.insertCellShotImg(vo);
	}

	@Override
	public List<GenerateCellVO> getCellsByPlantNo(long plantNo) {
		return plantMapper.getCellsByPlantNo(plantNo);
	}


	@Override
	public List<CellShotImgVO> getCellImgsByCellNo(long cellNo) {
		return plantMapper.getCellImgsByCellNo(cellNo);
	}


	@Override
	public int insertGeneratedElec(CellGeneratedElecVO vo) {
		return plantMapper.insertGeneratedElec(vo);
	}


	@Override
	public List<CellShotImgVO> getCellImgsByCellNoAndDate(CellShotImgVO cell){
		return plantMapper.getCellImgsByCellNoAndDate(cell);
	}
	
	@Override
	public List<GenerateCellVO> getGenerateCell(long selectedCell){
		return plantMapper.getGenerateCell(selectedCell);
	}


	@Override
	public List<PowerPlantVO> getNotGrantPlants() {
		return plantMapper.getNotGrantPlants();
	}


	@Override
	public int grantPlant(PowerPlantVO vo) {
		return plantMapper.grantPlant(vo);
	}


	@Override
	public List<PowerPlantVO> getGrantPlants() {
		return plantMapper.getGrantPlants();
	}


	@Override
	public List<Map<String, Object>> getCellsSensing(long plantNo) {
		return plantMapper.getCellsSensing(plantNo);
	}

}
