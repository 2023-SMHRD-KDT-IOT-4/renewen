package kr.smhrd.renewen.service;

import java.util.List;

import kr.smhrd.renewen.model.CellGeneratedElecVO;
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
	
	public int updateUseYn(long cellNo);
	
	// =================================================================	
	
	// 센싱데이터 
	public int insertSensingData(SensingDataVO vo);
	public List<SensingDataVO> getSensingDatasByPlantNo(long plantNo);
	public SensingDataVO getSensingDataBySdNo(long sdNo);
	
	// =================================================================
	
	// 구름형상 이미지
	public List<CloudShotImgVO> getCloudImgsByPlantNo(long plantNo);
	
	public int insertCloudShotImg(CloudShotImgVO vo);
	
	public List<CloudShotImgVO> getCloudImgsByPlantNoAndDate(CloudShotImgVO cloud);
	
	// =================================================================
	
	public List<GenerateCellVO> getGenerateCell(long selectedCell);
	
	// 발전셀

	// 아두이노 연동 - 발전셀 정보 저장
	public int insertGenerateCell(GenerateCellVO vo);
	// cellNo 조회(셀 시리얼번호로)
	public long getCellNoBySerialNum(String cellSerialNum);
	// 해당 발전소의 발전셀 전체 - plantNo조회
	public List<GenerateCellVO> getCellsByPlantNo(long plantNo);
	
	// 발전셀 촬영 이미지 저장
	public int insertCellShotImg(CellShotImgVO vo);
	// 해당 발전셀의 셀 촬영이미지 리스트
	public List<CellShotImgVO> getCellImgsByCellNo(long cellNo);
	
	// 발전셀 발전량 저장 
	public int insertGeneratedElec(CellGeneratedElecVO vo);
	
	public List<CellShotImgVO> getCellImgsByCellNoAndDate(CellShotImgVO cell);
	
}
