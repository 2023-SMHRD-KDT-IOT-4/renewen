package kr.smhrd.renewen.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.GenerateCellVO;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.UserVO;
import kr.smhrd.renewen.service.PlantService;

@Controller
public class PlantController { 

	@Autowired
	PlantService plantService;

	@Autowired
	CommonUtil commonUtil;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/plant/register")
	public String plantRegister() {
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
		System.out.println("발전소 등록 결과 : "+ res);
		
		return "redirect:/plant/list";
	}
	
	@GetMapping("/plant/list")
	public String plantList(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		List<PowerPlantVO> list = plantService.getPlantsByUserId(userId);
		model.addAttribute("list", list);
		session.setAttribute("plantNo", list);
		System.out.println("세션에 저장된 값"+session.getAttribute("plantNo"));
		return "views/plant/plant_list";
	}

	// 기존 리스트에 지도반영
	@GetMapping("/plant/list2")
	public String plantList2(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		List<PowerPlantVO> list = plantService.getPlantsByUserId(userId);
		model.addAttribute("list", list);
		return "views/plant/plant_list2";
	}
	
	

	@GetMapping("/plant/list/json")
	public @ResponseBody List<PowerPlantVO> plantListJson(HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		List<PowerPlantVO> list = plantService.getPlantsByUserId(userId);
		return list;
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
	

	// 발전소 구름형상 페이지
	@GetMapping("/plant/cloudImgs")
	public String cloudImgs(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		
		// 로그인 유저의 발전소 리스트 
		List<PowerPlantVO> plantList = plantService.getPlantsByUserId(userId);
		long plantNo = plantList.get(0).getPlantNo(); // 대표 발전소 식별번호(sortNo 오름차순)
		List<CloudShotImgVO> imgList = plantService.getCloudImgsByPlantNo(plantNo);
        model.addAttribute("plantList", plantList);
		model.addAttribute("imgList", imgList);
		
		return "views/plant/cloud_imgs";
	}
	
	// 발전소 셀 페이지
	@GetMapping("/plant/cellImgs")
	public String cellImgs(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
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
	
	@GetMapping("/plant/dashboard")

	public String dashboardPage() {

		

		return "views/plant/dashboard";

	}
}
