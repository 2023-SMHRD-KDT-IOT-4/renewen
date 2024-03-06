package kr.smhrd.renewen.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import kr.smhrd.renewen.model.CellShotImgVO;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.SensingDataVO;
import kr.smhrd.renewen.model.api.ShotImg;
import kr.smhrd.renewen.model.api.WeatherListVO;
import kr.smhrd.renewen.service.APIService;
import kr.smhrd.renewen.service.PlantService;

/**
 * 1) 아두이노(ESP32, 라즈베리파이)로 부터 센싱데이터 및 이미지 ==> DB 저장  
 * 2) 기상 데이터 조회
 */
@RestController
@RequestMapping("/api/was")
public class APIController {

	@Autowired
	PlantService plantService;

	@Autowired
	APIService apiService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	// 구름형상 이미지 업로드 및 db insert
	@PostMapping("/img/cloud")
	public String processCloudImg(@RequestBody ShotImg reqVO) throws IOException {

		String plantLinkKey = reqVO.getPlantLinkKey();
		// 발전소연동키 조회로 해당 발전소 가져옴
		long plantNo = plantService.getPlantNoByLinkKey(plantLinkKey);
		if (plantNo == 0) {
			return "Not Exists";
		}
		
		// 1) 이미지 업로드 처리. db 저장할 vo 리턴
		CloudShotImgVO shotImgVO = CloudShotImgVO.builder().build();
		try {
			shotImgVO = apiService.processShotImg(plantNo, reqVO);
		} catch (IOException e) {
			logger.error("cloudImg upload fail");
			return "fail";
		}
			
		// 2) 구름형상 이미지 db저장
		int result = plantService.insertCloudShotImg(shotImgVO);
		if(result == 0) {
			logger.error("cloudImg db insert fail {}", shotImgVO);
			return "fail";
		}

		return "success";
	}

	// 발전셀들 촬영 이미지 insert
	@PostMapping("/img/cells")
	public String cellImgsInsert(@RequestBody String jsonData) {
		
		JsonObject jsonObj = (JsonObject) JsonParser.parseString(jsonData);
		JsonArray cellsJsonArray = jsonObj.get("cells").getAsJsonArray();
		
		// 1) 이미지 업로드 처리. db 저장할 List<VO> 리턴
		List<CellShotImgVO> cellImgList = new ArrayList<>();
		try {
			 cellImgList = apiService.processCellShotImgs(cellsJsonArray);
			 if(cellImgList == null) {
				 return "Not Exists";
			 }
				 
		} catch (IOException e) {
			logger.error("cloudImg upload fail");
			return "fail";
		}
		
		// 2) 이미지 db 저장
		for(CellShotImgVO vo : cellImgList) {
			int result = plantService.insertCellShotImg(vo);
			if(result == 0) {
				logger.error("cellImg db insert fail {}", vo);
			}
			
		}
		
		return "suc";
	}
	
	
	// 센싱데이터 및 발전셀
	@PostMapping("/sensing")
	public String sensing(@RequestBody String jsonData) {
		logger.info("sensing {}", jsonData);
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
		logger.info("SensingDataVO List {}", list);
		
		for (SensingDataVO sd : list) {
			sd.setPlantNo(plantNo);
			plantService.insertSensingData(sd);
		}

		// 발전셀 처리
		logger.info("cellsJsonArray {}", cellsJsonArray);
		apiService.processGenerateCell(cellsJsonArray, plantNo);
		
		return "suc";
	}
	
	@GetMapping("/weather/list")
	public ResponseEntity<Map<String, List>> getWeatherList
							(@RequestParam("stnNo") String stnNo,
							 @RequestParam("type") String type) {
		Map<String, List> response = new HashMap<>();
		String[] types = type.split(",");
		
		for(String tp: types) {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("stnNo", stnNo);
			paramMap.put("type", tp);
			List<WeatherListVO> result =  apiService.getWeatherList(paramMap);
			response.put(tp, result);
		}
		
		return ResponseEntity.ok().body(response);
	}

}
