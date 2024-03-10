package kr.smhrd.renewen.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import kr.smhrd.renewen.global.util.CommonUtil;
import kr.smhrd.renewen.mapper.APIMapper;
import kr.smhrd.renewen.model.CellGeneratedElecVO;
import kr.smhrd.renewen.model.CellShotImgVO;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.GenerateCellVO;
import kr.smhrd.renewen.model.api.ShotImg;
import kr.smhrd.renewen.model.api.WeatherListVO;

@Service
public class APIService {
	
	@Autowired
	PlantService plantService;
	
	@Autowired
	APIMapper apiMapper;
	
	@Autowired
	CommonUtil commonUtil;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 구름형상 이미지 업로드 처리. 
	 * @param plantNo : 발전소 식별번호
	 * @param reqVO
	 *  발전소연동키, 이미지(base64encoded), 이미지이름, 이미지확장자, 이미지설명, 촬영시간
	 * @return db저장할 vo 리턴
	 * @throws IOException
	 */
	public CloudShotImgVO processShotImg(long plantNo, ShotImg reqVO) throws IOException {
		
		String base64String = reqVO.getImg();
		String imgOriginName = reqVO.getImgName();
		String imgExt = reqVO.getImgExt();
		byte[] decoded = Base64.decodeBase64(base64String);
		
		// 이미지 업로드 처리
		String imgUploadName = uploadImg(imgOriginName, imgExt, decoded);
		
		CloudShotImgVO shotImgVO = 
				CloudShotImgVO.builder()
								.plantNo(plantNo)
								.cloudImgDesc(reqVO.getImgDesc())
								.cloudImgName(imgOriginName)
								.cloudImgRealname(imgUploadName)
								.cloudImgExt(imgExt)
								.cloudImgSize(decoded.length)
								.createdAt(reqVO.getCreatedAt())
								.build();
		return shotImgVO;
	}
	
	public List<CellShotImgVO> processCellShotImgs(JsonArray cellsJsonArray) throws IOException {
		
		Type shotImgType = new TypeToken<List<ShotImg>>(){}.getType();
		List<ShotImg> list = new Gson().fromJson(cellsJsonArray, shotImgType);
		List<CellShotImgVO> cellImgList = new ArrayList<>();
		
		for(ShotImg shotImg : list) {
			String cellSerialNum = shotImg.getCellSerialNum();
			long cellNo = plantService.getCellNoBySerialNum(cellSerialNum);
			
			if(cellNo > 0) {
				String base64String = shotImg.getImg();
				String imgOriginName = shotImg.getImgName();
				String imgExt = shotImg.getImgExt();
				byte[] decoded = Base64.decodeBase64(base64String);
				
				// 이미지 업로드 처리
				String imgUploadName = uploadImg(imgOriginName, imgExt, decoded);
				
				CellShotImgVO shotImgVO = 
						CellShotImgVO.builder()
										.cellNo(cellNo)
										.cellImgDesc(shotImg.getImgDesc())
										.cellImgName(imgOriginName)
										.cellImgRealname(imgUploadName)
										.cellImgExt(imgExt)
										.cellImgSize(decoded.length)
										.createdAt(shotImg.getCreatedAt())
										.build();
				cellImgList.add(shotImgVO);
			} else { // 발전셀 식별번호 없음
				return null;
			}
		}
		
		return cellImgList;
	}
	
	public String uploadImg(String imgOriginName, String imgExt, byte[] decodedImg) throws IOException {
		
		String imgUploadName = commonUtil.getCurrentDateTime("yyyyMMddHHmmss") + "_" + imgOriginName; // 20240227_imgOriginName
		String realUploadDir = commonUtil.getUploadImgPath();
		File uploadFolder = new File(realUploadDir);
		
		// 해당경로 폴더 없으면 생성
		if(!uploadFolder.exists()) {
			uploadFolder.mkdirs();
		}
		
		String fullPath = Paths.get(realUploadDir, imgUploadName + "." + imgExt).toString();
		try (FileOutputStream fos = new FileOutputStream(fullPath)) {
			fos.write(decodedImg);
			fos.close();
        } catch (IOException e) {
			e.printStackTrace();
		} 
		
		return imgUploadName;
	}
	
	public void processGenerateCell(JsonArray cellsJsonArray, long plantNo) {
		
		// 발전셀 정보 generate_cell
		Type cellsType = new TypeToken<List<GenerateCellVO>>(){}.getType();
		List<GenerateCellVO> cellList = new Gson().fromJson(cellsJsonArray, cellsType);
		List<CellGeneratedElecVO> cellGenList = new ArrayList<>();
		
		for(GenerateCellVO cell : cellList) {
			String serialNum = cell.getCellSerialNum();
			long cellNo = plantService.getCellNoBySerialNum(serialNum);
			if(cellNo == 0) { // 발전셀 최초 등록
				cell.setPlantNo(plantNo);
				cell.setUseYn("N");
				plantService.insertGenerateCell(cell);
			} else {
				CellGeneratedElecVO cge = new CellGeneratedElecVO();
				cge.setCellNo(cellNo);
				cge.setGenVoltage(cell.getGenVoltage());
				cge.setGenElecCurrent(cell.getGenElecCurrent());
				cge.setCreatedAt(cell.getCreatedAt());
				cellGenList.add(cge);
			}
		}
		// 발전셀 발전량 저장
		for(CellGeneratedElecVO vo : cellGenList) {
			plantService.insertGeneratedElec(vo);
		}
		
	}
	
	public int insertWeatherFactor(Map<String, Object> map) {
		return apiMapper.insertWeatherFactor(map);
	}
	
	public List<WeatherListVO> getWeatherList(Map<String, String> map) {
		return apiMapper.getWeatherList(map);
	}

	public List<String> isInserted(String dateTime, List<String> types) {
		List<String> result = apiMapper.checkInsert(dateTime, types);
		return result;
	}
}
