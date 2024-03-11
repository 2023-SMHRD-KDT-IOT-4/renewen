package kr.smhrd.renewen.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
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

import kr.smhrd.renewen.model.CellGeneratedElecVO;
import kr.smhrd.renewen.model.CellShotImgVO;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.GenerateCellVO;
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

		logger.info("img cloud");
		String plantLinkKey = reqVO.getPlantLinkKey();
		// 발전소연동키 조회로 해당 발전소 가져옴
		long plantNo = plantService.getPlantNoByLinkKey(plantLinkKey);
		if (plantNo == 0) {
			return "Not Exists plantLinkKey";
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
		logger.info("img cloud success {}", plantNo);
		return "success";
	}

	// 발전셀들 촬영 이미지 insert
	@PostMapping("/img/cells")
	public String cellImgsInsert(@RequestBody String jsonData) {
		
		logger.info("img cells");
		JsonObject jsonObj = (JsonObject) JsonParser.parseString(jsonData);
		JsonArray cellsJsonArray = jsonObj.get("cells").getAsJsonArray();
		
		// 1) 이미지 업로드 처리. db 저장할 List<VO> 리턴
		List<CellShotImgVO> cellImgList = new ArrayList<>();
		try {
			 cellImgList = apiService.processCellShotImgs(cellsJsonArray);
			 if(cellImgList == null) {
				 return "Not Exists plantLinkKey";
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
		
		logger.info("img cells success");
		return "success";
	}
	
	
	// 발전소 - 센싱데이터 처리
	@PostMapping("/sensing")
	public String plantSensing(@RequestBody String jsonData) {
		
		JsonObject jsonObj = (JsonObject) JsonParser.parseString(jsonData);
		String plantLinkKey = jsonObj.get("plantLinkKey").getAsString();
		JsonArray sensingJsonArray = jsonObj.get("sensing").getAsJsonArray();

		// 발전소연동키 조회로 해당 발전소 가져옴
		long plantNo = plantService.getPlantNoByLinkKey(plantLinkKey);
		logger.info("sensing plantNo {}", plantNo);
		
		if (plantNo == 0) {
			logger.info("sensing - plantLinkKey {}", plantLinkKey);
			return "Not Exists plantLinkKey";
		}

		// sensing 처리
		List<SensingDataVO> list = new Gson().fromJson(sensingJsonArray,
				new TypeToken<List<SensingDataVO>>(){}.getType() );
		
		for (SensingDataVO sd : list) {
			sd.setPlantNo(plantNo);
			plantService.insertSensingData(sd);
		}
		
		logger.info("sensing success");
		return "success";
	}
	
	// 발전셀 - 센싱데이터 처리
	@PostMapping("/cells/sensing")
	public String cellsSensing(@RequestBody String jsonData) {
		
		JsonObject jsonObj = (JsonObject) JsonParser.parseString(jsonData);
		String plantLinkKey = jsonObj.get("plantLinkKey").getAsString();
		JsonArray cellsJsonArray = jsonObj.get("cells").getAsJsonArray();
		
		// 발전소연동키 조회로 해당 발전소 가져옴
		long plantNo = plantService.getPlantNoByLinkKey(plantLinkKey);
		if (plantNo == 0) {
			return "Not Exists plantLinkKey";
		}
		if(cellsJsonArray.size() == 0) {
			return "No Cells Datas";
		}
		logger.info("cells plantNo {}", plantNo);
		
		for (int i = 0; i < cellsJsonArray.size(); i++) {
			// cell 1개의 json data
			JsonObject cellJson = cellsJsonArray.get(i).getAsJsonObject();
			Type cellsType = new TypeToken<GenerateCellVO>(){}.getType();
			GenerateCellVO cell = new Gson().fromJson(cellJson, cellsType);
			
			String serialNum = cell.getCellSerialNum();
			long cellNo = plantService.getCellNoBySerialNum(serialNum);
			
			// 해당 발전셀 최초 등록
			if(cellNo == 0) { 
				cell.setPlantNo(plantNo);
				cell.setUseYn("N");
				plantService.insertGenerateCell(cell);
				continue;
			}

			// 이미 등록된 발전셀 -  센싱 데이터 저장
			JsonObject sensorJson = cellJson.get("sensor").getAsJsonObject();
			Type sensorType = new TypeToken<SensingDataVO>(){}.getType();
			
			SensingDataVO sensor = new Gson().fromJson(sensorJson, sensorType);
			sensor.setPlantNo(plantNo);
			plantService.insertSensingData(sensor);
			
	    } // for cellsJsonArray
		
		return "success";
	}
	
	// 발전셀 - 발전량 처리
	@PostMapping("/cells/elect")
	public String cellsElec(@RequestBody String jsonData) {
		
		JsonObject jsonObj = (JsonObject) JsonParser.parseString(jsonData);
		String plantLinkKey = jsonObj.get("plantLinkKey").getAsString();
		JsonArray cellsJsonArray = jsonObj.get("cells").getAsJsonArray();
		
		// 발전소연동키 조회로 해당 발전소 가져옴
		long plantNo = plantService.getPlantNoByLinkKey(plantLinkKey);
		if (plantNo == 0) {
			return "Not Exists plantLinkKey";
		}
		if(cellsJsonArray.size() == 0) {
			return "No Cells Datas";
		}
		logger.info("cells plantNo {}", plantNo);
		
		for (int i = 0; i < cellsJsonArray.size(); i++) {
			// cell 1개의 json data
			JsonObject cellJson = cellsJsonArray.get(i).getAsJsonObject();
			Type cellsType = new TypeToken<GenerateCellVO>(){}.getType();
			GenerateCellVO cell = new Gson().fromJson(cellJson, cellsType);
			
			String serialNum = cell.getCellSerialNum();
			long cellNo = plantService.getCellNoBySerialNum(serialNum);
			
			// 해당 발전셀 최초 등록
			if(cellNo == 0) { 
				cell.setPlantNo(plantNo);
				cell.setUseYn("N");
				plantService.insertGenerateCell(cell);
				continue;
			}

			// 이미 등록된 발전셀 -  발전량 데이터 저장
			JsonObject elecJson = cellJson.get("elec").getAsJsonObject();
			Type elecType = new TypeToken<CellGeneratedElecVO>(){}.getType();
			
			CellGeneratedElecVO cge = new Gson().fromJson(elecJson, elecType);
			cge.setCellNo(cellNo);
			plantService.insertGeneratedElec(cge);
			
	    } // for cellsJsonArray
		
		return "success";
	}	
	
	@GetMapping("/weather/list")
	public ResponseEntity<Map<String, List>> getWeatherList
							(@RequestParam("stnNo") String stnNo,
							 @RequestParam("type") String type) {
		
		logger.info("weather/list {}", type);
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
