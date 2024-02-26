package kr.smhrd.renewen.service;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import kr.smhrd.renewen.model.CellShotImgVO;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.GenerateCellVO;
import kr.smhrd.renewen.model.api.ShotImg;

@Service
public class ArduAPIService {

	@Autowired
	PlantService plantService;
	
	public void processGenerateCell(JsonArray cellsJsonArray, long plantNo) {
		
		// 발전셀 정보 generate_cell
		Type cellsType = new TypeToken<List<GenerateCellVO>>(){}.getType();
		List<GenerateCellVO> cellList = new Gson().fromJson(cellsJsonArray, cellsType);
		for(GenerateCellVO cell : cellList) {
			String serialNum = cell.getCellSerialNum();
			long result = plantService.getCellNoBySerialNum(serialNum);

			if(result == 0) {
				cell.setPlantNo(plantNo);
				System.out.println(cell);
				plantService.insertGenerateCell(cell);
			} else {
				System.out.println("exists");
			}
		}
		
	}
	
	public void processCellShotImgs(JsonArray cellsJsonArray) {
		
		Type shotImgType = new TypeToken<List<ShotImg>>(){}.getType();
		List<ShotImg> list = new Gson().fromJson(cellsJsonArray, shotImgType);
		System.out.println(list);
		
		for(ShotImg shotImg : list) {
			String cellSerialNum = shotImg.getCellSerialNum();
			long cellNo = plantService.getCellNoBySerialNum(cellSerialNum);
			
			if(cellNo != 0) {
				String base64String = shotImg.getImg();
				byte[] decoded = Base64.decodeBase64(base64String);
				
				CellShotImgVO vo = new CellShotImgVO();
				vo.setCellNo(cellNo);
				vo.setCreatedAt(shotImg.getCreatedAt());
				vo.setCellImgDesc(shotImg.getImgDesc());
				vo.setCellImg(decoded);
				
				plantService.insertCellShotImg(vo);
			} else {
				System.out.println("not exists cellNo");
			}
		}
		
	}
	
	
	public CloudShotImgVO getCloudShotImgVO(long plantNo, ShotImg reqVO) {
		
		CloudShotImgVO cloudShotImgVO = new CloudShotImgVO();
		cloudShotImgVO.setPlantNo(plantNo);
		cloudShotImgVO.setCreatedAt(reqVO.getCreatedAt());
		cloudShotImgVO.setCloudImgDesc(reqVO.getImgDesc());
		
		String base64String = reqVO.getImg();
		byte[] decoded = Base64.decodeBase64(base64String);
		cloudShotImgVO.setCloudImg(decoded);
		
		return cloudShotImgVO;
		
	}
}
