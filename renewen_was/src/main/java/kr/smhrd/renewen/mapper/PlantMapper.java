package kr.smhrd.renewen.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import kr.smhrd.renewen.model.CellShotImgVO;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.GenerateCellVO;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.SensingDataVO;

@Mapper
public interface PlantMapper {

	// 발전소
	public int registerPlant(PowerPlantVO vo);
	public List<PowerPlantVO> getPlantsByUserId(String userId);
	public PowerPlantVO getPlantByPlantNo(long plantNo);
	public Long getPlantNoByLinkKey(String plantLinkKey);
	
	public int deletePlant(PowerPlantVO vo);
	
	public PowerPlantVO getPlantInfo(int plantNo);
	
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
	public Long getCellNoBySerialNum(String cellSerialNum);
}
