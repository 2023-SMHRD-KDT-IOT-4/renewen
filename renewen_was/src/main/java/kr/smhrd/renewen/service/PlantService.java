package kr.smhrd.renewen.service;

import java.util.List;

import kr.smhrd.renewen.model.CellShotImgVO;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.GenerateCellVO;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.SensingDataVO;

public interface PlantService {

	// 발전소
	public int registerPlant(PowerPlantVO vo);
	public List<PowerPlantVO> getPlantsByUserId(String userId);
	public PowerPlantVO getPlantByPlantNo(long plantNo);
	public long getPlantNoByLinkKey(String plantLinkKey);
	
	public int deletePlant(PowerPlantVO vo);
	
	public PowerPlantVO getPlantInfo(int plantNo);
	public int updatePlant(PowerPlantVO vo);
	
	// =================================================================	
	
	// 센싱데이터 
	public int insertSensingData(SensingDataVO vo);
	public List<SensingDataVO> getSensingDatasByPlantNo(long plantNo);
	public SensingDataVO getSensingDataBySdNo(long sdNo);
	
	// =================================================================
	
	// 구름형상 이미지
	public List<CloudShotImgVO> getCloudImgsByPlantNo(long plantNo);
	public int insertCloudShotImg(CloudShotImgVO vo);
	
	// =================================================================
	
	// 발전셀
	public int insertGenerateCell(GenerateCellVO vo);
	public int insertCellShotImg(CellShotImgVO vo);
	public long getCellNoBySerialNum(String cellSerialNum);
	
}
