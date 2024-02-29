package kr.smhrd.renewen.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.SensingDataVO;
import kr.smhrd.renewen.model.api.ShotImg;
import kr.smhrd.renewen.service.ArduAPIService;
import kr.smhrd.renewen.service.PlantService;

// 아두이노(ESP32, 라즈베리파이)로 부터 센싱데이터 및 이미지 ==> DB 저장  
@RestController
@RequestMapping("/api/was")
public class ArduAPIController {

	@Autowired
	PlantService plantService;

	@Autowired
	ArduAPIService arduAPIService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	// 구름형상 이미지 업로드 및 db insert
	@PostMapping("/img/cloud")
	public String processCloudImg(@RequestBody ShotImg reqVO) throws IOException {

		String plantLinkKey = reqVO.getPlantLinkKey();
		// 발전소연동키 조회로 해당 발전소 가져옴
		long plantNo = plantService.getPlantNoByLinkKey(plantLinkKey);
		if (plantNo == 0) {
			return "Not Exists";
		}
		
		// 이미지 업로드 처리. db저장할 vo 리턴
		CloudShotImgVO shotImgVO = CloudShotImgVO.builder().build();
				
		try {
			shotImgVO = arduAPIService.processShotImg(plantNo, reqVO);
		} catch (IOException e) {
			log.error("cloudImg upload fail");
			return "fail";
		}
			
		// 해당 발전소 구름형상 이미지 db저장
		int result = plantService.insertCloudShotImg(shotImgVO);
		if(result == 0) {
			log.error("cloudImg db insert fail");
			return "fail";
		}

		return "success";
	}

	// 발전셀들 촬영 이미지 insert
	@PostMapping("/img/cells")
	public String cellImgsInsert(@RequestBody String jsonData) {
		
		JsonObject jsonObj = (JsonObject) JsonParser.parseString(jsonData);
		JsonArray cellsJsonArray = jsonObj.get("cells").getAsJsonArray();
		arduAPIService.processCellShotImgs(cellsJsonArray);
		
		return "suc";
	}
	
	
	// 센싱데이터 및 발전셀
	@PostMapping("/sensing")
	public String sensing(@RequestBody String jsonData) {

		JsonObject jsonObj = (JsonObject) JsonParser.parseString(jsonData);
		String plantLinkKey = jsonObj.get("plantLinkKey").getAsString();
		JsonArray sensingJsonArray = jsonObj.get("sensing").getAsJsonArray();
		JsonArray cellsJsonArray = jsonObj.get("cells").getAsJsonArray();

		// 발전소연동키 조회로 해당 발전소 가져옴
		long plantNo = plantService.getPlantNoByLinkKey(plantLinkKey);
		if (plantNo == 0) {
			return "Not Exists";
		}

		// sensing 처리
		Type sensingListType = new TypeToken<List<SensingDataVO>>(){}.getType();
		List<SensingDataVO> list = new Gson().fromJson(sensingJsonArray, sensingListType);
		
		for (SensingDataVO sd : list) {
			sd.setPlantNo(plantNo);
			plantService.insertSensingData(sd);
		}

		// 발전셀 처리
		arduAPIService.processGenerateCell(cellsJsonArray, plantNo);
		
		return "suc";
	}

}
