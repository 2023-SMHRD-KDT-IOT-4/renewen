package kr.smhrd.renewen.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import jakarta.servlet.http.HttpSession;
import kr.smhrd.renewen.global.util.CommonUtil;
import kr.smhrd.renewen.model.CellShotImgVO;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.GenerateCellVO;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.UserVO;
import kr.smhrd.renewen.service.PlantService;
import kr.smhrd.renewen.service.PlantStatsService;

@Controller
public class PlantController { 

	@Autowired
	PlantService plantService;
	
	@Autowired
	PlantStatsService plantStatsService;

	@Autowired
	CommonUtil commonUtil;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/plant/register")
	public String plantRegister(HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		if(user == null ) {
			return "redirect:/user/login";
		}
		return "views/plant/plant_register";
	}
	
	@PostMapping("/plant/register")
	public String plantRegister(PowerPlantVO plant, HttpSession session) {
		System.out.println(plant);
		UserVO user = (UserVO) session.getAttribute("user");
		
		String userId = user.getUserId();
		plant.setUserId(userId);
		
		// JSP 넘겨준거 우도 경도
		plant.setLatitude(plant.getLatitude());
		
		int res = plantService.registerPlant(plant);	
		if(res < 0) {
			return "redirect:/plant/register";
		}
		
		return "redirect:/plant/list";
	}
	
	@GetMapping("/plant/list")
	public String plantList(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		
		if(user == null ) {
			return "redirect:/user/login";
		}
		
		String userId = user.getUserId();
		List<PowerPlantVO> list = plantService.getPlantsByUserId(userId);
		model.addAttribute("list", list);
		session.setAttribute("plantNo", list);
		//System.out.println("세션에 저장된 값"+session.getAttribute("plantNo"));
		
		
		return "views/plant/plant_list";
	}

	 @GetMapping("/plant/list/delete")
	   public String plantListDelete(@RequestParam("plantNo") int plantNo,PowerPlantVO plant ,Model model,HttpSession session) {
	      System.out.println(plantNo);
	      
	      UserVO user = (UserVO)session.getAttribute("user");
	      String userId = user.getUserId();
	      plant.setUserId(userId);
	      
	      System.out.println("아이디 : "+userId);
	      plant.setPlantNo(plantNo);   
	      
	      int res = plantService.deletePlant(plant);
	      
	      System.out.println("리스트 삭제 결과 : "+res);
	      
	      return "redirect:/plant/list";
	      
	   }

	
	 
	 @GetMapping("/plant/update")
	   public String plantUpdate(@RequestParam("plantNo")int plantNo, HttpSession session,Model model) {
	      System.out.println("선택된 발전소"+plantNo);
	      
	      PowerPlantVO vo = plantService.getPlantInfo(plantNo);
	      model.addAttribute("vo",vo);
	      long getPlantNo = vo.getPlantNo();
	      session.setAttribute("plantNo",getPlantNo );
	      
	      
	      
	      return "views/plant/plant_update";
	   }

	
	 
	@PostMapping("/plant/update")
	public String plantUpdate( PowerPlantVO plant, HttpSession session) {
		long plantNo = (long)session.getAttribute("plantNo");
		plant.setPlantNo(plantNo);
		plant.setPlantAddr(plant.getPlantAddr());
		plant.setPlantAddr2(plant.getPlantAddr2());
		plant.setBrNumber(plant.getBrNumber());
		plant.setLatitude(plant.getLatitude());
		plant.setLongitude(plant.getLongitude());
		
		int res = plantService.updatePlant(plant);
		System.out.println("발전소 정보 수정 결과 : "+res);
	
		return "redirect:/plant/list";	
		
	}
	
	//모달창에 정보 가져오기
	@GetMapping("/plant/modal")
	public @ResponseBody List<GenerateCellVO> getCellInfo(@RequestParam("plantNo")int plantNo, HttpSession session,Model model) {
		System.out.println("발전소 번호 가져오기 : "+plantNo);
		List<GenerateCellVO> cellList = plantService.getCellsByPlantNo(plantNo);
		//session.setAttribute("cellList", cellList);
		
		model.addAttribute("cellList", cellList);
		session.setAttribute("cellList", cellList);
		System.out.println(cellList);
		return cellList;
		
	}
	//generate_cell테이블의 사용여부
	@GetMapping("/updateUseYn")
	@ResponseBody
	public String updateUseYn(HttpSession session, @RequestParam("selectedIndexArray") Integer[] selectedIndexArray) {
	    List<GenerateCellVO> cellList = (List<GenerateCellVO>)session.getAttribute("cellList");
	    List<Long> cellNoValues = new ArrayList<>();

	    if (cellList != null && !cellList.isEmpty()) {
	        for (int selectedIndex : selectedIndexArray) {
	            long cellNoValue = cellList.get(selectedIndex).getCellNo();
	            cellNoValues.add(cellNoValue);
	        }
	    }

	    for(int j = 0; j<cellNoValues.size(); j++) {
	    	plantService.updateUseYn(cellNoValues.get(j));
	    }
	    return "success";
	}

	// 발전소 구름형상 페이지
	@GetMapping("/plant/cloudImgs")
	public String cloudImgs(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		if(user == null) {
			return "redirect:/user/login";
		}
		
		List<PowerPlantVO> plant = (List<PowerPlantVO>)session.getAttribute("plantList");
		if(plant.isEmpty()) {
			return "redirect:/plant/register";
		}
		String userId = user.getUserId();
		
		// 로그인 유저의 발전소 리스트 
		List<PowerPlantVO> plantList = plantService.getPlantsByUserId(userId);
		long plantNo = plantList.get(0).getPlantNo(); // 대표 발전소 식별번호(sortNo 오름차순)
		List<CloudShotImgVO> imgList = plantService.getCloudImgsByPlantNo(plantNo);
        model.addAttribute("plantList", plantList);
		model.addAttribute("imgList", imgList);
		
		return "views/plant/cloud_imgs";
	}
	
	//구름이미지
	@PostMapping("/plant/cloudImgs")
	@ResponseBody
	public List<CloudShotImgVO> cloudImgs(@RequestParam("selectedPlant") int selectedPlant, @RequestParam("selectedDate") String selectedDate,Model model) {
		//System.out.println("선택된 발전소 "+ selectedPlant);
		//System.out.println("선택된 날짜 "+ selectedDate);
		CloudShotImgVO cloud = new CloudShotImgVO();
		cloud.setPlantNo(selectedPlant);
		cloud.setSelectedDate(selectedDate);
	
		List<CloudShotImgVO> cloudImgList = plantService.getCloudImgsByPlantNoAndDate(cloud);
		
		return cloudImgList;
	}
	
	//셀 이미지
	@PostMapping("/plant/cellImgs")
	@ResponseBody
	public Map<String, Object> cellImgs(@RequestParam("selectedCell") int selectedCell, @RequestParam("selectedDate")String selectedDate) {
		//System.out.println("선택된 셀"+selectedCell);
		//System.out.println("선택된 날짜"+selectedDate);
		
		CellShotImgVO cell = new CellShotImgVO();
		
		cell.setCellNo(selectedCell);
		cell.setCreatedAt(selectedDate);
		
		List<CellShotImgVO> cellList = plantService.getCellImgsByCellNoAndDate(cell);
		//System.out.println("cell이미지 리스트:"+cellList);
		
	    List<GenerateCellVO> generateCellList = plantService.getGenerateCell(selectedCell);
		
	    Map<String, Object> result = new HashMap<>();
	    result.put("cellList", cellList);
	    result.put("GenerateCellVO", generateCellList);
		
		
		return result;
		
	}
	
	// 발전소 셀 페이지
	@GetMapping("/plant/cellImgs")
	public String cellImgs(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		if(user == null) {
			return "redirect:/user/login";
		}
		
		String userId = user.getUserId();
		
		// 로그인 유저의 발전소 리스트 (sortNo 오름차순)
		List<PowerPlantVO> plantList = plantService.getPlantsByUserId(userId);
		
		for(PowerPlantVO plantVO : plantList) {
			long plantNo = plantVO.getPlantNo();
			List<GenerateCellVO> cellList = plantService.getCellsByPlantNo(plantNo);
			plantVO.setCellList(cellList);
			
			for(GenerateCellVO cell : cellList) {
				long cellNo = cell.getCellNo();
				cell.setCellImgList(plantService.getCellImgsByCellNo(cellNo));
			}
		}
		// 대표 발전소 식별번호
		model.addAttribute("plantList", plantList);
		
		return "views/plant/cell_imgs";
	}
	
	//cellNo 가져오기
	@GetMapping("/plant/cell")
	@ResponseBody
	public List<GenerateCellVO> getCellNo(@RequestParam("plantNo") int plantNo) {
		//System.out.println("선택된 발전소 번호 : "+plantNo);

		List<GenerateCellVO> cellList =  plantService.getCellsByPlantNo(plantNo);
		
		//System.out.println(cellList);
		
		return cellList;
		
	}

	// 이미지 다운로드
	@GetMapping("/plant/download/{filename:.+}")
	public ResponseEntity<Resource> downloadImg(@PathVariable("filename") String filename) {

		UrlResource resource;
	    try{
	        resource = new UrlResource("file:"+ commonUtil.getFileFullPath(filename));
	    }catch (MalformedURLException e){
	        logger.error("the given File path is not valid");
	        e.getStackTrace();
	        throw new RuntimeException("the given URL path is not valid");
	    }
	    String encodedOriginalFileName = UriUtils.encode(filename, StandardCharsets.UTF_8);
	    String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";
	    logger.info("img download {}", filename);
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
	            .body(resource);
	}
	
	// 발전 현황
	@GetMapping("/plant/dashboard")
	public String dashboardPage(Model model, HttpSession session) {

		UserVO user = (UserVO) session.getAttribute("user");
		
		if(user == null) {
			return "redirect:/user/login";
		}
		String userId = user.getUserId();

		List<PowerPlantVO> plantList = plantService.getPlantsByUserId(userId);
		model.addAttribute("plantList", plantList);
		
		return "views/plant/dashboard";

	}

	// 발전량 조회
	@GetMapping("/plant/stats")
	public String plantStats(Model model, HttpSession session) {

		UserVO user = (UserVO) session.getAttribute("user");
		if(user == null ) {
			return "redirect:/user/login";
		}
		
		String userId = user.getUserId();
		
		List<PowerPlantVO> plantList = plantService.getPlantsByUserId(userId);
		model.addAttribute("plantList", plantList);
		
		return "views/plant/plant_stats";
		
	}
	
	// 발전소 현황
	@GetMapping("/plant/status")
	public String plantStatus(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		if(user == null ) {
			return "redirect:/user/login";
		}
		
		String userId = user.getUserId();
		
		List<PowerPlantVO> plantList = plantService.getPlantsByUserId(userId);
		model.addAttribute("plantList", plantList);
		
		return "views/plant/plant_status";
		
	}
}
