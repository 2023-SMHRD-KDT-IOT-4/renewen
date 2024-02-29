package kr.smhrd.renewen.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import kr.smhrd.renewen.global.util.DateUtil;
import kr.smhrd.renewen.model.CellShotImgVO;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.GenerateCellVO;
import kr.smhrd.renewen.model.api.ShotImg;

@Service
public class ArduAPIService {
	
	@Autowired
	PlantService plantService;
	
	@Value("${upload.path}")
	String uploadPath; 

	@Value("${upload.img}")
	String imgDir; 
	
	/**
	 * 이미지 업로드 처리. 
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
	
	public String uploadImg(String imgOriginName, String imgExt, byte[] decodedImg) throws IOException {
		
		String imgUploadName = DateUtil.getCurrentDateTime() + "_" + imgOriginName; // 20240227_imgName
		String realUploadDir = uploadPath + imgDir;
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
		for(GenerateCellVO cell : cellList) {
			String serialNum = cell.getCellSerialNum();
			long result = plantService.getCellNoBySerialNum(serialNum);

			if(result == 0) {
				cell.setPlantNo(plantNo);
				plantService.insertGenerateCell(cell);
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

}
