package kr.smhrd.renewen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.SensingDataVO;
import kr.smhrd.renewen.model.UserVO;
import kr.smhrd.renewen.service.PlantService;

@Controller
public class PlantController { 

	@Autowired
	PlantService plantService;
	
	@Autowired
	RestTemplate restTemplate;
	
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
		
		int res = plantService.updatePlant(plant);
		System.out.println("발전소 정보 수정 결과 : "+res);
	
		return "redirect:/plant/list";	
		
	}
	
	
	
	
	@GetMapping("/plant/cloudImgs")
	public String imgList(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		List<PowerPlantVO> plantList = plantService.getPlantsByUserId(userId);
		
		if(plantList.isEmpty()) {
			return "views/plant/cloudImgs"; 
		}
		
		long plantNo = plantList.get(0).getPlantNo();
		List<CloudShotImgVO> list = plantService.getCloudImgsByPlantNo(plantNo);
		model.addAttribute("cloudImgs", list);
		
		return "views/plant/cloudImgs";
	}

}
